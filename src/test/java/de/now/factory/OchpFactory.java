package de.now.factory;

import de.now.enums.ochp.*;
import eu.ochp._1_4.ConnectorFormat;
import eu.ochp._1_4.ConnectorStandard;
import eu.ochp._1_4.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

public class OchpFactory {

    private OchpFactory() {
    }

    public static AuthMethodType createAuthMethodType(final AuthMethod authMethod) {
        final AuthMethodType authMethodType = new AuthMethodType();
        authMethodType.setAuthMethodType(authMethod.getValue());
        return authMethodType;
    }

    public static BillingItemType createBillingItemType(final String billingItemTypeValue) {
        final BillingItemType billingItemType = new BillingItemType();
        billingItemType.setBillingItemType(billingItemTypeValue);
        return billingItemType;
    }

    public static ChargePointStatusType createChargePointStatusType(ChargePointStatus chargePointStatusEnum) {
        final ChargePointStatusType chargePointStatusType = new ChargePointStatusType();
        chargePointStatusType.setChargePointStatusType(chargePointStatusEnum.getValue());
        return chargePointStatusType;
    }

    public static ChargePointScheduleType createChargePointScheduleType(
            final DateTimeType startDateTime,
            final DateTimeType endDateTime,
            final ChargePointStatusType status
    ) {
        final ChargePointScheduleType chargePointScheduleType = new ChargePointScheduleType();
        chargePointScheduleType.setStartDate(startDateTime);
        chargePointScheduleType.setEndDate(endDateTime);
        chargePointScheduleType.setStatus(status);
        return chargePointScheduleType;
    }

    public static ConnectorFormat createConnectorFormat(final de.now.enums.ochp.ConnectorFormat connectorFormatEnum) {
        final ConnectorFormat connectorFormat = new ConnectorFormat();
        connectorFormat.setConnectorFormat(connectorFormatEnum.getValue());
        return connectorFormat;
    }

    public static ConnectorFormat createConnectorFormat(final String connectorFormatValue) {
        final ConnectorFormat connectorFormat = new ConnectorFormat();
        connectorFormat.setConnectorFormat(connectorFormatValue);
        return connectorFormat;
    }

    public static ConnectorStandard createConnectorStandard(final de.now.enums.ochp.ConnectorStandard connectorStandardEnum) {
        final ConnectorStandard connectorStandard = new ConnectorStandard();
        connectorStandard.setConnectorStandard(connectorStandardEnum.getValue());
        return connectorStandard;
    }

    public static ConnectorStandard createConnectorStandard(final String connectorStandardValue) {
        final ConnectorStandard connectorStandard = new ConnectorStandard();
        connectorStandard.setConnectorStandard(connectorStandardValue);
        return connectorStandard;
    }

    public static DateTimeType createDateTimeType(final String dateTime) {
        final DateTimeType dateTimeType = new DateTimeType();
        dateTimeType.setDateTime(dateTime);
        return dateTimeType;
    }

    public static ExceptionalPeriodType createExceptionalPeriodType(final DateTimeType periodBegin, final DateTimeType periodEnd) {
        final ExceptionalPeriodType exceptionalPeriodType = new ExceptionalPeriodType();
        exceptionalPeriodType.setPeriodBegin(periodBegin);
        exceptionalPeriodType.setPeriodEnd(periodEnd);
        return exceptionalPeriodType;
    }

    public static ExceptionalPeriodType createExceptionalPeriodType(final String periodBegin, final String periodEnd) {
        return createExceptionalPeriodType(createDateTimeType(periodBegin), createDateTimeType(periodEnd));
    }

    public static EvseImageUrlType createEvseImageUrlType(final String uri, final ImageClass imageClass, final String type) {
        final EvseImageUrlType evseImageUrlType = new EvseImageUrlType();
        evseImageUrlType.setUri(uri);
        evseImageUrlType.setClazz(imageClass.getValue());
        evseImageUrlType.setType(type);
        return evseImageUrlType;
    }

    public static EvseStatusType createEvseStatusType(
            final String evseId,
            final EvseStatusMajor evseStatusMajor,
            final EvseStatusMinor evseStatusMinor,
            final XMLGregorianCalendar ttl
    ) {
        final EvseStatusType evseStatusType = new EvseStatusType();
        evseStatusType.setEvseId(evseId);
        evseStatusType.setMajor(evseStatusMajor.getValue());
        if (evseStatusMinor != null) {
            evseStatusType.setMinor(evseStatusMinor.getValue());
        }
        evseStatusType.setTtl(ttl);
        return evseStatusType;
    }

    public static HoursType createHoursType(
            final boolean closedCharging,
            final Boolean twentyfourseven,
            final List<RegularHoursType> regularHours
    ) {
        final HoursType hoursType = new HoursType();
        hoursType.setClosedCharging(closedCharging);
        hoursType.setTwentyfourseven(twentyfourseven);
        hoursType.getRegularHours().addAll(regularHours);
        return hoursType;
    }

    public static IndividualTariffType createIndividualTariffType(
            final String currency,
            final float itemPrice,
            final BillingItem billingItem,
            final float stepSize
    ) {
        final IndividualTariffType individualTariffType = new IndividualTariffType();
        individualTariffType.setCurrency(currency);
        // Recipients optional
        individualTariffType.getTariffElement().add(createTariffElementType(itemPrice, billingItem, stepSize));
        return individualTariffType;
    }

    public static PriceComponentType createPriceComponentType(
            final float itemPrice,
            final BillingItem billingItem,
            final float stepSize
    ) {
        final PriceComponentType priceComponentType = new PriceComponentType();
        priceComponentType.setItemPrice(itemPrice);
        priceComponentType.setBillingItem(createBillingItemType(billingItem.getValue()));
        priceComponentType.setStepSize(stepSize);
        return priceComponentType;
    }

    public static RegularHoursType createRegularHoursType(
            final int weekday,
            final String periodBegin,
            final String periodEnd
    ) {
        final RegularHoursType regularHoursType = new RegularHoursType();
        regularHoursType.setWeekday(weekday);
        regularHoursType.setPeriodBegin(periodBegin);
        regularHoursType.setPeriodEnd(periodEnd);
        return regularHoursType;
    }

    public static RelatedResourceType createRelatedResourceType(final RelatedResourceClass clazz, final String uri) {
        final RelatedResourceType relatedResourceType = new RelatedResourceType();
        relatedResourceType.setClazz(clazz.getValue());
        relatedResourceType.setUri(uri);
        return relatedResourceType;
    }

    public static TariffElementType createTariffElementType(
            final float itemPrice,
            final BillingItem billingItem,
            final float stepSize
    ) {
        final TariffElementType tariffElementType = new TariffElementType();
        // using empty tariffRestriction
        tariffElementType.setTariffRestriction(createTariffRestrictionType());
        tariffElementType.setPriceComponent(createPriceComponentType(itemPrice, billingItem, stepSize));
        return tariffElementType;
    }

    public static TariffInfo createTariffInfo(
            final String tariffId,
            final String currency,
            final float itemPrice,
            final BillingItem billingItem,
            final float stepSize
    ) {
        final TariffInfo tariffInfo = new TariffInfo();
        tariffInfo.setTariffId(tariffId);
        tariffInfo.getIndividualTariff().add(createIndividualTariffType(currency, itemPrice, billingItem, stepSize));
        return tariffInfo;
    }

    public static TariffRestrictionType createTariffRestrictionType() {
        // all fields are optional
        return new TariffRestrictionType();
    }
}
