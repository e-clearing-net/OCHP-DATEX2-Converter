package de.now.enums.ochp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WeekDayTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of(1, WeekDay.MONDAY),
                Arguments.of(2, WeekDay.TUESDAY),
                Arguments.of(3, WeekDay.WEDNESDAY),
                Arguments.of(4, WeekDay.THURSDAY),
                Arguments.of(5, WeekDay.FRIDAY),
                Arguments.of(6, WeekDay.SATURDAY),
                Arguments.of(7, WeekDay.SUNDAY)
        );
    }

    @ParameterizedTest(name = "Value {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapWeekDay(final int value, final WeekDay expected) {
        // when
        final WeekDay weekDay = WeekDay.fromValue(value);

        // then
        assertThat(weekDay).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapBadWeekDay() {
        // given
        final int badValue = 1_000_000;
        // when
        final WeekDay weekDay = WeekDay.fromValue(badValue);
        // then
        assertThat(weekDay).isNull();
    }
}
