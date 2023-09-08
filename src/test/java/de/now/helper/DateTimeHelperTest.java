package de.now.helper;

import eu.ochp._1_4.DateTimeType;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DateTimeHelperTest {

    @Test
    void convertToXMLGregorianCalendar() {
        // given
        final OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneOffset.UTC);
        // when
        final XMLGregorianCalendar xmlGregorianCalendar = DateTimeHelper.convertToXMLGregorianCalendar(offsetDateTime);
        // then
        assertThat(xmlGregorianCalendar).isNotNull();
        assertThat(xmlGregorianCalendar.getYear()).isEqualTo(offsetDateTime.getYear());
        assertThat(xmlGregorianCalendar.getMonth()).isEqualTo(offsetDateTime.getMonthValue());
        assertThat(xmlGregorianCalendar.getDay()).isEqualTo(offsetDateTime.getDayOfMonth());
        assertThat(xmlGregorianCalendar.getHour()).isEqualTo(offsetDateTime.getHour());
        assertThat(xmlGregorianCalendar.getMinute()).isEqualTo(offsetDateTime.getMinute());
        assertThat(xmlGregorianCalendar.getSecond()).isEqualTo(offsetDateTime.getSecond());
    }

    @Test
    void formatToIsoUTC() {
        // given
        final LocalDateTime localDateTime = LocalDateTime.of(2022, 2, 10, 8, 37);
        final OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, ZoneOffset.UTC);
        // when
        final String isoDateTimeString = DateTimeHelper.formatToIsoUTC(offsetDateTime);
        // then
        assertThat(isoDateTimeString).isNotNull().isEqualTo("2022-02-10T08:37:00Z");
    }

    @Test
    void datatypeFactoryIsSet() {
        assertThat(DateTimeHelper.DATATYPE_FACTORY).isNotNull();
    }

    @Test
    void timeZoneIsUTC() {
        assertThat(DateTimeHelper.TIME_ZONE_UTC).isNotNull();
        assertThat(DateTimeHelper.TIME_ZONE_UTC.getID()).isEqualTo("UTC");
    }

    @Test
    void formatterIsZuluZone() {
        assertThat(DateTimeHelper.ISO_UTC_FORMATTER).isNotNull();
        assertThat(DateTimeHelper.ISO_UTC_FORMATTER.getZone().getId()).isEqualTo("Z");
    }

    @Test
    void nowAtUtcAsXMLGregorianCalendar() {
        // given
        // when
        final XMLGregorianCalendar nowAtUtcAsXMLGregorianCalendar = DateTimeHelper.getNowAtUtcAsXMLGregorianCalendar();
        // then
        assertThat(nowAtUtcAsXMLGregorianCalendar).isNotNull();

        final int fieldUndefined = DatatypeConstants.FIELD_UNDEFINED;
        assertThat(nowAtUtcAsXMLGregorianCalendar.getYear()).isNotEqualTo(fieldUndefined);
        assertThat(nowAtUtcAsXMLGregorianCalendar.getMonth()).isNotEqualTo(fieldUndefined);
        assertThat(nowAtUtcAsXMLGregorianCalendar.getDay()).isNotEqualTo(fieldUndefined);
        assertThat(nowAtUtcAsXMLGregorianCalendar.getHour()).isNotEqualTo(fieldUndefined);
        assertThat(nowAtUtcAsXMLGregorianCalendar.getMinute()).isNotEqualTo(fieldUndefined);
        assertThat(nowAtUtcAsXMLGregorianCalendar.getSecond()).isNotEqualTo(fieldUndefined);
    }

    @Test
    void nowAtUtcAsEpochSecond() {
        // given
        final long now = OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond();
        // when
        final long nowAtUtcAsEpochSecond = DateTimeHelper.getNowAtUtcAsEpochSeconds();
        // then
        final long differenceSeconds = nowAtUtcAsEpochSecond - now;
        assertThat(differenceSeconds).isLessThan(5L);
    }

    @Test
    void parseToEpochSeconds() {
        // given
        final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        final String dateTime = DateTimeHelper.formatToIsoUTC(now);
        final DateTimeType dateTimeType = new DateTimeType();
        dateTimeType.setDateTime(dateTime);
        // when
        final long epochSeconds = DateTimeHelper.parseToEpochSeconds(dateTimeType);
        // then
        assertThat(epochSeconds).isEqualTo(now.toEpochSecond());
    }

    @Test
    void parseToXMLGregorianCalendar() {
        // given
        final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        final String dateTime = DateTimeHelper.formatToIsoUTC(now);
        final DateTimeType dateTimeType = new DateTimeType();
        dateTimeType.setDateTime(dateTime);
        // when
        final XMLGregorianCalendar xmlGregorianCalendar = DateTimeHelper.parseToXMLGregorianCalendar(dateTimeType);
        // then
        assertThat(xmlGregorianCalendar).isNotNull();
        assertThat(xmlGregorianCalendar.getYear()).isEqualTo(now.getYear());
        assertThat(xmlGregorianCalendar.getMonth()).isEqualTo(now.getMonthValue());
        assertThat(xmlGregorianCalendar.getDay()).isEqualTo(now.getDayOfMonth());
        assertThat(xmlGregorianCalendar.getHour()).isEqualTo(now.getHour());
        assertThat(xmlGregorianCalendar.getMinute()).isEqualTo(now.getMinute());
        assertThat(xmlGregorianCalendar.getSecond()).isEqualTo(now.getSecond());
    }

    @Test
    void parseTimeToXMLGregorianCalendar() {
        // given
        final String time = "18:59";
        // when
        final XMLGregorianCalendar xmlGregorianCalendar = DateTimeHelper.parseTimeToXMLGregorianCalendar(time);
        // then
        assertThat(xmlGregorianCalendar).isNotNull();
        assertThat(xmlGregorianCalendar.getHour()).isEqualTo(18);
        assertThat(xmlGregorianCalendar.getMinute()).isEqualTo(59);
    }
}
