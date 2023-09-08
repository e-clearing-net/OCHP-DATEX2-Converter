package de.now.mapper.ochp;

import de.now.enums.ochp.WeekDay;
import eu.datex2.schema._3.common.DayEnum2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DayMapperTest {

    private static Stream<Arguments> getIntTestValues() {
        return Stream.of(
                Arguments.of(1, DayEnum2.MONDAY),
                Arguments.of(2, DayEnum2.TUESDAY),
                Arguments.of(3, DayEnum2.WEDNESDAY),
                Arguments.of(4, DayEnum2.THURSDAY),
                Arguments.of(5, DayEnum2.FRIDAY),
                Arguments.of(6, DayEnum2.SATURDAY),
                Arguments.of(7, DayEnum2.SUNDAY)
        );
    }

    @ParameterizedTest(name = "Weekday {0}, Expected {1}")
    @MethodSource("getIntTestValues")
    void mapWeekDayInt(final int weekday, DayEnum2 expected) {
        // when
        final DayEnum2 dayEnum2 = DayMapper.INSTANCE.toDayEnum2(weekday);
        // then
        assertThat(dayEnum2).isNotNull().isEqualTo(expected);
    }

    private static Stream<Arguments> getDayEnum2TestValues() {
        return Stream.of(
                Arguments.of(WeekDay.MONDAY, DayEnum2.MONDAY),
                Arguments.of(WeekDay.TUESDAY, DayEnum2.TUESDAY),
                Arguments.of(WeekDay.WEDNESDAY, DayEnum2.WEDNESDAY),
                Arguments.of(WeekDay.THURSDAY, DayEnum2.THURSDAY),
                Arguments.of(WeekDay.FRIDAY, DayEnum2.FRIDAY),
                Arguments.of(WeekDay.SATURDAY, DayEnum2.SATURDAY),
                Arguments.of(WeekDay.SUNDAY, DayEnum2.SUNDAY)
        );
    }

    @ParameterizedTest(name = "WeekDay {0}, Expected {1}")
    @MethodSource("getDayEnum2TestValues")
    void mapWeekDayEnum(final WeekDay weekday, DayEnum2 expected) {
        // when
        final DayEnum2 dayEnum2 = DayMapper.INSTANCE.toDayEnum2(weekday);
        // then
        assertThat(dayEnum2).isNotNull().isEqualTo(expected);
    }
}
