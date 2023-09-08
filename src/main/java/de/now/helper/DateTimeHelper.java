package de.now.helper;

import eu.ochp._1_4.DateTimeType;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DateTimeHelper {

    private DateTimeHelper() {
    }

    // visible for testing
    protected static final DatatypeFactory DATATYPE_FACTORY = DatatypeFactory.newDefaultInstance();

    protected static final TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone("UTC");

    protected static final DateTimeFormatter ISO_UTC_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC);

    public static XMLGregorianCalendar convertToXMLGregorianCalendar(final OffsetDateTime offsetDateTime) {
        return getXmlGregorianCalendar(offsetDateTime);
    }

    public static String formatToIsoUTC(final OffsetDateTime offsetDateTime) {
        return ISO_UTC_FORMATTER.format(offsetDateTime);
    }

    public static XMLGregorianCalendar getNowAtUtcAsXMLGregorianCalendar() {
        return getXmlGregorianCalendar(OffsetDateTime.now(ZoneOffset.UTC));
    }

    public static long getNowAtUtcAsEpochSeconds() {
        return OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond();
    }

    public static long parseToEpochSeconds(final DateTimeType dateTimeType) {
        return OffsetDateTime.parse(dateTimeType.getDateTime(), ISO_UTC_FORMATTER).toEpochSecond();
    }

    public static XMLGregorianCalendar parseToXMLGregorianCalendar(final DateTimeType dateTimeType) {
        return DATATYPE_FACTORY.newXMLGregorianCalendar(dateTimeType.getDateTime());
    }

    public static XMLGregorianCalendar parseTimeToXMLGregorianCalendar(final String time) {
        final LocalTime localTime = LocalTime.parse(time);
        final XMLGregorianCalendar xmlGregorianCalendar = DATATYPE_FACTORY.newXMLGregorianCalendar();
        xmlGregorianCalendar.setTime(localTime.getHour(), localTime.getMinute(), 0);
        return xmlGregorianCalendar;
    }

    private static XMLGregorianCalendar getXmlGregorianCalendar(OffsetDateTime offsetDateTime) {
        final XMLGregorianCalendar xmlGregorianCalendar = DATATYPE_FACTORY.newXMLGregorianCalendar();
        xmlGregorianCalendar.setYear(offsetDateTime.getYear());
        xmlGregorianCalendar.setMonth(offsetDateTime.getMonthValue());
        xmlGregorianCalendar.setDay(offsetDateTime.getDayOfMonth());
        xmlGregorianCalendar.setTime(offsetDateTime.getHour(), offsetDateTime.getMinute(), offsetDateTime.getSecond());
        xmlGregorianCalendar.setTimezone(offsetDateTime.getOffset().getTotalSeconds() / 60); // UTC
        return xmlGregorianCalendar;
    }
}
