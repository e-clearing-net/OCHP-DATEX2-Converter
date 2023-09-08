package de.now.enums.ochp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EvseStatusMinorTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of("available", EvseStatusMinor.AVAILABLE),
                Arguments.of("blocked", EvseStatusMinor.BLOCKED),
                Arguments.of("charging", EvseStatusMinor.CHARGING),
                Arguments.of("outoforder", EvseStatusMinor.OUT_OF_ORDER),
                Arguments.of("reserved", EvseStatusMinor.RESERVED)
        );
    }

    @ParameterizedTest(name = "Value {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapEvseStatusMinor(final String value, final EvseStatusMinor expected) {
        // when
        final EvseStatusMinor evseStatusMinor = EvseStatusMinor.fromValue(value);

        // then
        assertThat(evseStatusMinor).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapBadEvseStatusMinor() {
        // given
        final String badValue = "BadValue";
        // when
        final EvseStatusMinor evseStatusMinor = EvseStatusMinor.fromValue(badValue);
        // then
        assertThat(evseStatusMinor).isNull();
    }
}
