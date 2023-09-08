package de.now.mapper.ochp;

import de.now.enums.ochp.ChargePointStatus;
import de.now.enums.ochp.RelatedResourceClass;
import de.now.helper.DateTimeHelper;
import de.now.helper.ochp.status.ClosedStatusSchedule;
import de.now.helper.ochp.status.HelperStatus;
import de.now.helper.ochp.status.OverwriteStatusSchedule;
import eu.datex2.schema._3.common.*;
import eu.datex2.schema._3.facilities.ClosureInformation;
import eu.datex2.schema._3.facilities.OperatingHours;
import eu.datex2.schema._3.facilities.OperatingHoursSpecification;
import eu.ochp._1_4.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Mapper(uses = {DayMapper.class, NameTimestampVersionMapper.class})
public interface OpeningTimesMapper {

    OpeningTimesMapper INSTANCE = Mappers.getMapper(OpeningTimesMapper.class);


    @Mapping(source = ".", target = "closureInformation")
    @Mapping(source = ".", target = "lastUpdated", qualifiedByName = "toTimestamp")
    @Mapping(source = ".", target = "overallPeriod")
    @Mapping(source = ".", target = "urlLinkAddress", qualifiedByName = "toOpeningTimesUrl")
    @Mapping(source = "openingTimes.twentyfourseven", target = "operatingAllYear")
    @Mapping(source = "evseId", target = "id", qualifiedByName = "cleanUpAsterisk")
    @Mapping(target = "version", expression = "java(nameTimestampVersionMapper.toVersion())")
    OperatingHoursSpecification toOperatingHoursSpecification(final ChargePointInfo chargePointInfo);

    default ClosureInformation toClosureInformation(final ChargePointInfo chargePointInfo) {
        final ChargePointStatusType status = chargePointInfo.getStatus();
        final String chargePointStatusType = status != null ? status.getChargePointStatusType() : null;
        final ChargePointStatus chargePointStatus = ChargePointStatus.fromValue(chargePointStatusType);
        final List<ChargePointScheduleType> statusSchedule = chargePointInfo.getStatusSchedule();

        return toClosureInformation(chargePointStatus, statusSchedule);
    }

    default List<Period> toExceptionPeriod(
            final List<ExceptionalPeriodType> exceptionalPeriodTypes
    ) {
        if (exceptionalPeriodTypes.isEmpty()) {
            return Collections.emptyList();
        }

        final List<Period> periods = new ArrayList<>(exceptionalPeriodTypes.size());
        for (ExceptionalPeriodType exceptionalPeriodType : exceptionalPeriodTypes) {
            final Period period = new Period();
            period.setStartOfPeriod(toXMLGregorianCalendar(exceptionalPeriodType.getPeriodBegin()));
            period.setEndOfPeriod(toXMLGregorianCalendar(exceptionalPeriodType.getPeriodEnd()));

            periods.add(period);
        }

        return periods;
    }

    @Named("toOpeningTimesUrl")
    default String toOpeningTimesUrl(final ChargePointInfo chargePointInfo) {
        final List<RelatedResourceType> relatedResource = chargePointInfo.getRelatedResource();

        if (relatedResource.isEmpty()) {
            return null;
        }

        // https://github.com/e-clearing-net/OCHP/blob/OCHP-1.4.1/OCHP.md#relatedresourceclass-enum -"openingTimes" no such value in WSDL
        // using stationInfo
        final String clazzToMatch = RelatedResourceClass.STATION_INFO.getValue();
        for (RelatedResourceType relatedResourceType : relatedResource) {
            if (clazzToMatch.equals(relatedResourceType.getClazz())) {
                return relatedResourceType.getUri();
            }
        }

        return null;
    }

    @ObjectFactory
    default OperatingHours toOperatingHours(final ChargePointInfo chargePointInfo) {
        return toOperatingHoursSpecification(chargePointInfo);
    }

    default OverallPeriod toOverallPeriod(final ChargePointInfo chargePointInfo) {

        final OverallPeriod overallPeriod = new OverallPeriod();

        final XMLGregorianCalendar timestamp = Objects.requireNonNullElse(
                toXMLGregorianCalendar(chargePointInfo.getTimestamp()),
                DateTimeHelper.getNowAtUtcAsXMLGregorianCalendar()
        );

        overallPeriod.setOverallStartTime(timestamp);

        final HoursType openingTimes = chargePointInfo.getOpeningTimes();
        if (openingTimes != null) {
            // Regular Hours and Exceptional Openings
            final List<Period> validPeriod = toValidPeriods(openingTimes.getRegularHours());
            overallPeriod.getValidPeriod().addAll(validPeriod);

            final List<Period> exceptionalOpenings = toExceptionPeriod(openingTimes.getExceptionalOpenings());
            overallPeriod.getValidPeriod().addAll(exceptionalOpenings);

            // Exceptional Closings
            final List<Period> exceptionalClosings = toExceptionPeriod(openingTimes.getExceptionalClosings());
            overallPeriod.getExceptionPeriod().addAll(exceptionalClosings);
        }

        return overallPeriod;
    }

    default List<Period> toValidPeriods(
            final List<RegularHoursType> regularHoursTypes
    ) {
        if (regularHoursTypes.isEmpty()) {
            return Collections.emptyList();
        }

        final List<Period> periods = new ArrayList<>(regularHoursTypes.size());

        for (RegularHoursType regularHoursType : regularHoursTypes) {
            final Period period = new Period();

            final TimePeriodOfDay timePeriodOfDay = new TimePeriodOfDay();
            timePeriodOfDay.setStartTimeOfPeriod(
                    DateTimeHelper.parseTimeToXMLGregorianCalendar(regularHoursType.getPeriodBegin())
            );
            timePeriodOfDay.setEndTimeOfPeriod(
                    DateTimeHelper.parseTimeToXMLGregorianCalendar(regularHoursType.getPeriodEnd())
            );

            period.getRecurringTimePeriodOfDay().add(timePeriodOfDay);

            final DayEnum dayEnum = new DayEnum();
            dayEnum.setValue(DayMapper.INSTANCE.toDayEnum2(regularHoursType.getWeekday()));

            final DayWeekMonth dayWeekMonth = new DayWeekMonth();
            dayWeekMonth.getApplicableDay().add(dayEnum);

            period.getRecurringDayWeekMonthPeriod().add(dayWeekMonth);

            periods.add(period);
        }

        return periods;
    }

    default XMLGregorianCalendar toXMLGregorianCalendar(final DateTimeType dateTimeType) {
        if (dateTimeType == null) {
            return null;
        }

        return DateTimeHelper.parseToXMLGregorianCalendar(dateTimeType);
    }

    private ClosureInformation toClosureInformation(final ChargePointStatus chargePointStatus, final List<ChargePointScheduleType> statusSchedule) {

        if (chargePointStatus == null && statusSchedule.isEmpty()) {
            return null;
        }

        final OverwriteStatusSchedule overwriteStatusSchedule = new OverwriteStatusSchedule();
        // Future status - look for Inoperative, Closed in statusSchedule list.
        final ClosedStatusSchedule futureInoperativeStatus = new ClosedStatusSchedule(ChargePointStatus.INOPERATIVE);
        final ClosedStatusSchedule futureClosedStatus = new ClosedStatusSchedule(ChargePointStatus.CLOSED);

        for (ChargePointScheduleType chargePointScheduleType : statusSchedule) {
            final long scheduleStartDate = DateTimeHelper.parseToEpochSeconds(chargePointScheduleType.getStartDate());

            overwriteStatusSchedule.accept(scheduleStartDate, chargePointScheduleType);
            futureInoperativeStatus.accept(scheduleStartDate, chargePointScheduleType);
            futureClosedStatus.accept(scheduleStartDate, chargePointScheduleType);
        }

        final ChargePointScheduleType futureClosed = HelperStatus.selectClosestInFutureStatus(futureClosedStatus, futureInoperativeStatus);

        return toClosureInformation(chargePointStatus, overwriteStatusSchedule, futureClosed);
    }

    private ClosureInformation toClosureInformation(
            final ChargePointStatus status,
            final OverwriteStatusSchedule overwriteStatusSchedule,
            final ChargePointScheduleType futureClosed
    ) {
        final boolean overwriteStatus = overwriteStatusSchedule.hasValue();
        final ChargePointScheduleType overwriteStatusScheduleValue = overwriteStatusSchedule.getValue();
        final ChargePointStatus tempStatus = (overwriteStatus
                ? ChargePointStatus.fromValue(overwriteStatusScheduleValue.getStatus().getChargePointStatusType())
                : status
        );

        final boolean isCurrentlyClosed;
        final ClosureInformation closureInformation = new ClosureInformation();

        if (ChargePointStatus.CLOSED.equals(tempStatus)) {
            isCurrentlyClosed = true;
            closureInformation.setPermananentlyClosed(Boolean.TRUE);
            if (overwriteStatus) {
                closureInformation.setClosedFrom(toXMLGregorianCalendar(overwriteStatusScheduleValue.getStartDate()));
            }
        } else if (ChargePointStatus.INOPERATIVE.equals(tempStatus)) {
            isCurrentlyClosed = true;
            closureInformation.setTemporarilyClosed(Boolean.TRUE);
            if (overwriteStatus) {
                closureInformation.setClosedFrom(toXMLGregorianCalendar(overwriteStatusScheduleValue.getStartDate()));
                closureInformation.setTemporarilyClosedUntil(toXMLGregorianCalendar(overwriteStatusScheduleValue.getEndDate()));
            }
        } else {
            isCurrentlyClosed = false;
        }

        final ChargePointStatus futureStatus = ChargePointStatus.fromValue(
                futureClosed != null ? futureClosed.getStatus().getChargePointStatusType() : null
        );

        if (!isCurrentlyClosed) {
            // if in future may be closed - pass only the dates
            if (ChargePointStatus.CLOSED.equals(futureStatus)) {
                closureInformation.setClosedFrom(toXMLGregorianCalendar(futureClosed.getStartDate()));
            } else if (ChargePointStatus.INOPERATIVE.equals(futureStatus)) {
                closureInformation.setClosedFrom(toXMLGregorianCalendar(futureClosed.getStartDate()));
                closureInformation.setTemporarilyClosedUntil(toXMLGregorianCalendar(futureClosed.getEndDate()));
            }
        }

        return closureInformation;
    }
}
