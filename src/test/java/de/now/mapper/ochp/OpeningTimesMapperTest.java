package de.now.mapper.ochp;

import de.now.enums.ochp.ChargePointStatus;
import de.now.enums.ochp.RelatedResourceClass;
import de.now.factory.OchpFactory;
import de.now.helper.DateTimeHelper;
import eu.datex2.schema._3.common.*;
import eu.datex2.schema._3.facilities.ClosureInformation;
import eu.datex2.schema._3.facilities.OperatingHours;
import eu.datex2.schema._3.facilities.OperatingHoursSpecification;
import eu.ochp._1_4.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class OpeningTimesMapperTest {

    @Test
    void mapOperatingHoursWithLastUpdateTimestamp() {
        // given
        final OffsetDateTime timestamp = OffsetDateTime.now(ZoneOffset.UTC);

        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setEvseId("DE*LND*E1");
        chargePointInfo.setTimestamp(OchpFactory.createDateTimeType(DateTimeHelper.formatToIsoUTC(timestamp)));

        // when
        final OperatingHours operatingHours = OpeningTimesMapper.INSTANCE.toOperatingHours(chargePointInfo);

        // then
        assertThat(operatingHours).isNotNull();
        assertThat(operatingHours.getClass()).isEqualTo(OperatingHoursSpecification.class);

        final OperatingHoursSpecification operatingHoursSpecification = (OperatingHoursSpecification) operatingHours;
        final XMLGregorianCalendar lastUpdated = operatingHoursSpecification.getLastUpdated();
        assertThat(lastUpdated).isNotNull().isEqualTo(DateTimeHelper.convertToXMLGregorianCalendar(timestamp));
    }

    @Test
    void mapOperatingHoursSpecification() {
        // given
        final OffsetDateTime timestamp = OffsetDateTime.now(ZoneOffset.UTC);

        final OffsetDateTime offsetDateTimeStart = OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(2);
        final DateTimeType startDateTime = OchpFactory.createDateTimeType(DateTimeHelper.formatToIsoUTC(offsetDateTimeStart));

        final OffsetDateTime offsetDateTimeEnd = OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(3);
        final DateTimeType endDateTime = OchpFactory.createDateTimeType(DateTimeHelper.formatToIsoUTC(offsetDateTimeEnd));

        final ChargePointScheduleType statusSchedule = OchpFactory.createChargePointScheduleType(
                startDateTime, endDateTime, OchpFactory.createChargePointStatusType(ChargePointStatus.INOPERATIVE)
        );

        final HoursType openingTimes = OchpFactory.createHoursType(true, Boolean.TRUE, List.of());

        final String uri = "https://github.com/e-clearing-net/OCHP";
        final RelatedResourceType relatedResourceType = OchpFactory.createRelatedResourceType(RelatedResourceClass.STATION_INFO, uri);

        final String inEvseId = "DE*BMW*E12";

        final DateTimeType inDateTimeType = OchpFactory.createDateTimeType(LocalDate.now(ZoneOffset.UTC).toString());

        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setEvseId(inEvseId);
        chargePointInfo.setTimestamp(inDateTimeType);
        chargePointInfo.setStatus(OchpFactory.createChargePointStatusType(ChargePointStatus.OPERATIVE));
        chargePointInfo.getStatusSchedule().add(statusSchedule);
        chargePointInfo.setOpeningTimes(openingTimes);
        chargePointInfo.getRelatedResource().add(relatedResourceType);

        // when
        final OperatingHoursSpecification operatingHoursSpecification = OpeningTimesMapper.INSTANCE.toOperatingHoursSpecification(chargePointInfo);

        // then
        assertThat(operatingHoursSpecification).isNotNull();

        final String id = operatingHoursSpecification.getId();
        assertThat(id).isNotNull().isEqualTo(NameTimestampVersionMapper.INSTANCE.cleanUpAsterisk(inEvseId));

        final String version = operatingHoursSpecification.getVersion();
        assertThat(version).isNotNull().isEqualTo(inDateTimeType.getDateTime());

        final XMLGregorianCalendar lastUpdated = operatingHoursSpecification.getLastUpdated();
        assertThat(lastUpdated.toString()).isNotNull().isEqualTo(LocalDate.now(ZoneOffset.UTC).toString());

        final Boolean operatingAllYear = operatingHoursSpecification.isOperatingAllYear();
        assertThat(operatingAllYear).isNotNull().isEqualTo(Boolean.TRUE);

        final String urlLinkAddress = operatingHoursSpecification.getUrlLinkAddress();
        assertThat(urlLinkAddress).isNotNull().isEqualTo(uri);

        final ClosureInformation closureInformation = operatingHoursSpecification.getClosureInformation();
        assertThat(closureInformation).isNotNull();

        final Boolean permananentlyClosed = closureInformation.isPermananentlyClosed();
        assertThat(permananentlyClosed).isNull();

        final Boolean temporarilyClosed = closureInformation.isTemporarilyClosed();
        assertThat(temporarilyClosed).isNull();

        // In future may be closed - only dates passed - currently OPERATIVE
        final XMLGregorianCalendar closedFrom = closureInformation.getClosedFrom();
        assertThat(closedFrom).isNotNull().isEqualTo(DateTimeHelper.convertToXMLGregorianCalendar(offsetDateTimeStart));

        final XMLGregorianCalendar temporarilyClosedUntil = closureInformation.getTemporarilyClosedUntil();
        assertThat(temporarilyClosedUntil).isNotNull().isEqualTo(DateTimeHelper.convertToXMLGregorianCalendar(offsetDateTimeEnd));
    }

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of(ChargePointStatus.INOPERATIVE, true),
                Arguments.of(ChargePointStatus.CLOSED, false)
        );
    }

    @ParameterizedTest(name = "ChargePointStatus {0}, temporaryClosed {1}")
    @MethodSource("getTestValues")
    void mapClosureInformationOverwritesStatus(final ChargePointStatus overwriteStatus, final boolean temporaryClosed) {
        // given
        final OffsetDateTime offsetDateTimeStart = OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(2); // in the past!
        final DateTimeType startDateTime = OchpFactory.createDateTimeType(DateTimeHelper.formatToIsoUTC(offsetDateTimeStart));

        final OffsetDateTime offsetDateTimeEnd = OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(3);
        final DateTimeType endDateTime = OchpFactory.createDateTimeType(DateTimeHelper.formatToIsoUTC(offsetDateTimeEnd));

        final ChargePointScheduleType statusSchedule = OchpFactory.createChargePointScheduleType(
                startDateTime, endDateTime, OchpFactory.createChargePointStatusType(overwriteStatus)
        );

        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setStatus(OchpFactory.createChargePointStatusType(ChargePointStatus.OPERATIVE));
        chargePointInfo.getStatusSchedule().add(statusSchedule);

        // when
        final ClosureInformation closureInformation = OpeningTimesMapper.INSTANCE.toClosureInformation(chargePointInfo);

        // then
        assertThat(closureInformation).isNotNull();

        if (!temporaryClosed) {
            final Boolean permananentlyClosed = closureInformation.isPermananentlyClosed();
            assertThat(permananentlyClosed).isNotNull().isEqualTo(Boolean.TRUE);

            final XMLGregorianCalendar closedFrom = closureInformation.getClosedFrom();
            assertThat(closedFrom).isNotNull().isEqualTo(DateTimeHelper.convertToXMLGregorianCalendar(offsetDateTimeStart));

            final XMLGregorianCalendar temporarilyClosedUntil = closureInformation.getTemporarilyClosedUntil();
            assertThat(temporarilyClosedUntil).isNull();
            return;
        }

        final Boolean temporarilyClosed = closureInformation.isTemporarilyClosed();
        assertThat(temporarilyClosed).isNotNull().isEqualTo(Boolean.TRUE);

        final XMLGregorianCalendar closedFrom = closureInformation.getClosedFrom();
        assertThat(closedFrom).isNotNull().isEqualTo(DateTimeHelper.convertToXMLGregorianCalendar(offsetDateTimeStart));

        final XMLGregorianCalendar temporarilyClosedUntil = closureInformation.getTemporarilyClosedUntil();
        assertThat(temporarilyClosedUntil).isNotNull().isEqualTo(DateTimeHelper.convertToXMLGregorianCalendar(offsetDateTimeEnd));
    }

    @Test
    void mapOverallPeriod() {
        // given
        final String startTime = "07:00";
        final String endTime = "20:00";

        final RegularHoursType regularHoursType1 = OchpFactory.createRegularHoursType(1, startTime, endTime);
        final RegularHoursType regularHoursType2 = OchpFactory.createRegularHoursType(2, startTime, endTime);
        final RegularHoursType regularHoursType3 = OchpFactory.createRegularHoursType(3, startTime, endTime);

        final List<RegularHoursType> regularHours = List.of(regularHoursType1, regularHoursType2, regularHoursType3);

        final HoursType openingTimes = OchpFactory.createHoursType(true, Boolean.FALSE, regularHours);

        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setOpeningTimes(openingTimes);

        // when
        final OverallPeriod overallPeriod = OpeningTimesMapper.INSTANCE.toOverallPeriod(chargePointInfo);

        // then
        assertThat(overallPeriod).isNotNull();

        final XMLGregorianCalendar overallStartTime = overallPeriod.getOverallStartTime();
        assertThat(overallStartTime).isNotNull();

        final XMLGregorianCalendar overallEndTime = overallPeriod.getOverallEndTime();
        assertThat(overallEndTime).isNull();

        final List<Period> validPeriod = overallPeriod.getValidPeriod();
        assertThat(validPeriod).isNotNull().hasSize(regularHours.size());

        final Period period1 = validPeriod.get(0);
        assertThat(period1).isNotNull();

        final Period period2 = validPeriod.get(1);
        assertThat(period2).isNotNull();

        final Period period3 = validPeriod.get(2);
        assertThat(period3).isNotNull();
    }

    @Test
    void mapOverallPeriodExceptional() {
        // given
        final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        final OffsetDateTime periodBegin1 = now.plusDays(10L);
        final OffsetDateTime periodEnd1 = now.plusDays(15L);

        final ExceptionalPeriodType exceptionalPeriodType1 = OchpFactory.createExceptionalPeriodType(
                DateTimeHelper.formatToIsoUTC(periodBegin1),
                DateTimeHelper.formatToIsoUTC(periodEnd1)
        );

        final OffsetDateTime periodBegin2 = now.plusDays(20L);
        final OffsetDateTime periodEnd2 = now.plusDays(25L);

        final ExceptionalPeriodType exceptionalPeriodType2 = OchpFactory.createExceptionalPeriodType(
                DateTimeHelper.formatToIsoUTC(periodBegin2),
                DateTimeHelper.formatToIsoUTC(periodEnd2)
        );

        final HoursType openingTimes = OchpFactory.createHoursType(true, Boolean.TRUE, List.of());
        openingTimes.getExceptionalOpenings().add(exceptionalPeriodType1);
        openingTimes.getExceptionalClosings().add(exceptionalPeriodType2);

        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setOpeningTimes(openingTimes);

        // when
        final OverallPeriod overallPeriod = OpeningTimesMapper.INSTANCE.toOverallPeriod(chargePointInfo);

        // then
        assertThat(overallPeriod).isNotNull();

        final List<Period> validPeriods = overallPeriod.getValidPeriod();
        assertThat(validPeriods).isNotNull().hasSize(1);

        final Period validPeriod = validPeriods.get(0);
        assertThat(validPeriod).isNotNull();

        final List<Period> exceptionPeriods = overallPeriod.getExceptionPeriod();
        assertThat(exceptionPeriods).isNotNull().hasSize(1);

        final Period exceptionalPeriod = exceptionPeriods.get(0);
        assertThat(exceptionalPeriod).isNotNull();
    }

    @Test
    void mapValidPeriods() {
        // given
        final String startTime = "07:00";
        final String endTime = "20:00";

        final RegularHoursType regularHoursType1 = OchpFactory.createRegularHoursType(1, startTime, endTime);
        final RegularHoursType regularHoursType2 = OchpFactory.createRegularHoursType(2, startTime, endTime);
        final RegularHoursType regularHoursType3 = OchpFactory.createRegularHoursType(3, startTime, endTime);

        final List<RegularHoursType> regularHours = List.of(regularHoursType1, regularHoursType2, regularHoursType3);

        // when
        final List<Period> periods = OpeningTimesMapper.INSTANCE.toValidPeriods(regularHours);

        // then
        assertThat(periods).isNotNull().hasSize(regularHours.size());

        final Period period1 = periods.get(0);
        assertThat(period1).isNotNull();

        final List<DayWeekMonth> recurringDayWeekMonthPeriod = period1.getRecurringDayWeekMonthPeriod();
        assertThat(recurringDayWeekMonthPeriod).isNotNull().hasSize(1);

        final DayWeekMonth dayWeekMonth = recurringDayWeekMonthPeriod.get(0);
        assertThat(dayWeekMonth).isNotNull();

        final List<DayEnum> applicableDay = dayWeekMonth.getApplicableDay();
        assertThat(applicableDay).isNotNull().hasSize(1);

        final DayEnum dayEnum = applicableDay.get(0);
        assertThat(dayEnum).isNotNull();

        final DayEnum2 dayEnumValue = dayEnum.getValue();
        assertThat(dayEnumValue).isNotNull().isEqualTo(DayEnum2.MONDAY);

        final List<TimePeriodOfDay> recurringTimePeriodOfDay = period1.getRecurringTimePeriodOfDay();
        assertThat(recurringTimePeriodOfDay).isNotNull().hasSize(1);

        final TimePeriodOfDay timePeriodOfDay = recurringTimePeriodOfDay.get(0);
        assertThat(timePeriodOfDay).isNotNull();

        final XMLGregorianCalendar startTimeOfPeriod = timePeriodOfDay.getStartTimeOfPeriod();
        assertThat(startTimeOfPeriod).isNotNull().isEqualTo(DateTimeHelper.parseTimeToXMLGregorianCalendar(startTime));

        final XMLGregorianCalendar endTimeOfPeriod = timePeriodOfDay.getEndTimeOfPeriod();
        assertThat(endTimeOfPeriod).isNotNull().isEqualTo(DateTimeHelper.parseTimeToXMLGregorianCalendar(endTime));
    }

    @Test
    void mapExceptionPeriod() {
        // given
        final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        final OffsetDateTime periodBegin1 = now.plusDays(10L);
        final OffsetDateTime periodEnd1 = now.plusDays(15L);

        final ExceptionalPeriodType exceptionalPeriodType1 = OchpFactory.createExceptionalPeriodType(
                DateTimeHelper.formatToIsoUTC(periodBegin1),
                DateTimeHelper.formatToIsoUTC(periodEnd1)
        );

        final OffsetDateTime periodBegin2 = now.plusDays(20L);
        final OffsetDateTime periodEnd2 = now.plusDays(25L);

        final ExceptionalPeriodType exceptionalPeriodType2 = OchpFactory.createExceptionalPeriodType(
                DateTimeHelper.formatToIsoUTC(periodBegin2),
                DateTimeHelper.formatToIsoUTC(periodEnd2)
        );

        final List<ExceptionalPeriodType> exceptionalPeriodTypes = List.of(exceptionalPeriodType1, exceptionalPeriodType2);
        // when
        final List<Period> periods = OpeningTimesMapper.INSTANCE.toExceptionPeriod(exceptionalPeriodTypes);
        // then
        assertThat(periods).isNotNull().hasSize(exceptionalPeriodTypes.size());

        final Period period1 = periods.get(0);
        assertThat(period1).isNotNull();

        final XMLGregorianCalendar startOfPeriod = period1.getStartOfPeriod();
        assertThat(startOfPeriod).isNotNull().isEqualTo(DateTimeHelper.convertToXMLGregorianCalendar(periodBegin1));

        final XMLGregorianCalendar endOfPeriod = period1.getEndOfPeriod();
        assertThat(endOfPeriod).isNotNull().isEqualTo(DateTimeHelper.convertToXMLGregorianCalendar(periodEnd1));
    }


}