package de.now.mapper.ochp;

import de.now.enums.ochp.WeekDay;
import eu.datex2.schema._3.common.DayEnum2;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DayMapper {

    DayMapper INSTANCE = Mappers.getMapper(DayMapper.class);

    DayEnum2 toDayEnum2(final WeekDay weekDay);

    default DayEnum2 toDayEnum2(final int weekDay) {
        return toDayEnum2(WeekDay.fromValue(weekDay));
    }
}
