package de.now.enums.ochp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GeneralLocationTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of("on-street", GeneralLocation.ON_STREET),
                Arguments.of("parking-garage", GeneralLocation.PARKING_GARAGE),
                Arguments.of("underground-garage", GeneralLocation.UNDERGROUND_GARAGE),
                Arguments.of("parking-lot", GeneralLocation.PARKING_LOT),
                Arguments.of("other", GeneralLocation.OTHER),
                Arguments.of("unknown", GeneralLocation.UNKNOWN),
                Arguments.of("private", GeneralLocation.PRIVATE)
        );
    }

    @ParameterizedTest(name = "Value {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapGeneralLocation(final String value, final GeneralLocation expected) {
        // when
        final GeneralLocation generalLocation = GeneralLocation.fromValue(value);
        // then
        assertThat(generalLocation).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapBadGeneralLocation() {
        // given
        final String badValue = "BadValue";
        // when
        final GeneralLocation generalLocation = GeneralLocation.fromValue(badValue);
        // then
        assertThat(generalLocation).isNull();
    }
}
