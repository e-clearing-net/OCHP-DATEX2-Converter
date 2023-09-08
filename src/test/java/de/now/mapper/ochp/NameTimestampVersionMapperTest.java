package de.now.mapper.ochp;

import de.now.factory.OchpFactory;
import de.now.helper.DateTimeHelper;
import eu.datex2.schema._3.common.MultilingualString;
import eu.datex2.schema._3.common.MultilingualStringValue;
import eu.ochp._1_4.ChargePointInfo;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NameTimestampVersionMapperTest {

    @Test
    void mapVersionNow() {
        // given
        final String localDateNow = LocalDate.now(ZoneOffset.UTC).toString();
        // when
        final String version = NameTimestampVersionMapper.INSTANCE.toVersion();
        // then
        assertThat(version).isNotNull().isEqualTo(localDateNow);
    }

    @Test
    void mapTimestamp() {
        // given
        final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        final String inTimestamp = DateTimeHelper.formatToIsoUTC(now);

        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setTimestamp(OchpFactory.createDateTimeType(inTimestamp));
        // when
        final XMLGregorianCalendar xmlGregorianCalendar = NameTimestampVersionMapper.INSTANCE.toTimestamp(chargePointInfo);
        // then
        assertThat(xmlGregorianCalendar).isNotNull().isEqualTo(DateTimeHelper.convertToXMLGregorianCalendar(now));
    }

    @Test
    void mapTimestampNow() {
        // given
        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        // when
        final XMLGregorianCalendar xmlGregorianCalendar = NameTimestampVersionMapper.INSTANCE.toTimestamp(chargePointInfo);
        // then
        assertThat(xmlGregorianCalendar).isNotNull();
    }

    @Test
    void mapOperatorName() {
        // given
        final String inEvseId = "DE*LND*E1234";
        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setEvseId(inEvseId);
        // when
        final MultilingualString multilingualString = NameTimestampVersionMapper.INSTANCE.toOperatorName(chargePointInfo);
        // then
        assertThat(multilingualString).isNotNull();

        final List<MultilingualStringValue> values = multilingualString.getValues().getValue();
        assertThat(values).hasSize(1);

        final MultilingualStringValue multilingualStringValue = values.get(0);
        assertThat(multilingualStringValue).isNotNull();

        final String value = multilingualStringValue.getValue();
        assertThat(value).isNotNull().isEqualTo("DELND");

        final String lang = multilingualStringValue.getLang();
        assertThat(lang).isNotNull().isEqualTo("en");
    }

    @Test
    void mapLocationName() {
        // given
        final String inLocationName = "Location Name Test";
        final String inLocationNameLang = "ENG";
        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setLocationName(inLocationName);
        chargePointInfo.setLocationNameLang(inLocationNameLang);
        // when
        final MultilingualString multilingualString = NameTimestampVersionMapper.INSTANCE.toLocationName(chargePointInfo);
        // then
        assertThat(multilingualString).isNotNull();

        final List<MultilingualStringValue> values = multilingualString.getValues().getValue();
        assertThat(values).hasSize(1);

        final MultilingualStringValue multilingualStringValue = values.get(0);
        assertThat(multilingualStringValue).isNotNull();

        final String value = multilingualStringValue.getValue();
        assertThat(value).isNotNull().isEqualTo(inLocationName);

        final String lang = multilingualStringValue.getLang();
        assertThat(lang).isNotNull().isEqualTo("en");
    }
}
