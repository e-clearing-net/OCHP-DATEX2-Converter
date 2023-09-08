package de.now.enums.ochp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EvseStatusMajorTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of("available", EvseStatusMajor.AVAILABLE),
                Arguments.of("not-available", EvseStatusMajor.NOT_AVAILABLE),
                Arguments.of("unknown", EvseStatusMajor.UNKNOWN)
        );
    }

    @ParameterizedTest(name = "Value {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapEvseStatusMajor(final String value, final EvseStatusMajor expected) {
        // when
        final EvseStatusMajor evseStatusMajor = EvseStatusMajor.fromValue(value);

        // then
        assertThat(evseStatusMajor).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapBadEvseStatusMajor() {
        // given
        final String badValue = "BadValue";
        // when
        final EvseStatusMajor evseStatusMajor = EvseStatusMajor.fromValue(badValue);
        // then
        assertThat(evseStatusMajor).isNull();
    }
}
