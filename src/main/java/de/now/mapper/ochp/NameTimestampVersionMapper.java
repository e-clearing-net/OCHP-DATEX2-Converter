package de.now.mapper.ochp;

import de.now.datex2.factory.Datex2Factory;
import de.now.enums.ochp.LanguageCode;
import de.now.helper.DateTimeHelper;
import eu.datex2.schema._3.common.MultilingualString;
import eu.ochp._1_4.ChargePointInfo;
import eu.ochp._1_4.DateTimeType;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Mapper
public interface NameTimestampVersionMapper {

    NameTimestampVersionMapper INSTANCE = Mappers.getMapper(NameTimestampVersionMapper.class);

    @Named("toLocationName")
    default MultilingualString toLocationName(final ChargePointInfo chargePointInfo) {
        return Datex2Factory.createMultilingualString(chargePointInfo.getLocationName(), LanguageCode.convertLanguageCode3to2(chargePointInfo.getLocationNameLang()));
    }

    @Named("toOperatorName")
    default MultilingualString toOperatorName(final ChargePointInfo chargePointInfo) {
        // Using (CountryCode + Party/OperatorID) as name and hardcoded English language
        return Datex2Factory.createMultilingualString(toOperatorName5chars(chargePointInfo), "en");
    }

    @Named("toOperatorName5chars")
    default String toOperatorName5chars(final ChargePointInfo chargePointInfo) {
        return cleanUpAsterisk(chargePointInfo.getEvseId()).substring(0, 5);
    }

    @Named("toTimestamp")
    default XMLGregorianCalendar toTimestamp(final ChargePointInfo chargePointInfo) {
        final DateTimeType timestamp = chargePointInfo.getTimestamp();
        if (timestamp != null) {
            return DateTimeHelper.parseToXMLGregorianCalendar(timestamp);
        }

        return DateTimeHelper.getNowAtUtcAsXMLGregorianCalendar();
    }

    @Named("toTimestampNow")
    default XMLGregorianCalendar toTimestampNow() {
        return DateTimeHelper.getNowAtUtcAsXMLGregorianCalendar();
    }

    @Named("toVersion")
    default String toVersion() {
        return LocalDate.now(ZoneOffset.UTC).toString();
    }

    @Named("cleanUpAsterisk")
    default String cleanUpAsterisk(final String value) {
        return value.replace("*", "");
    }
}
