package de.now.enums.ochp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ChargePointStatusTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of("Unknown", ChargePointStatus.UNKNOWN),
                Arguments.of("Operative", ChargePointStatus.OPERATIVE),
                Arguments.of("Inoperative", ChargePointStatus.INOPERATIVE),
                Arguments.of("Planned", ChargePointStatus.PLANNED),
                Arguments.of("Closed", ChargePointStatus.CLOSED)
        );
    }

    @ParameterizedTest(name = "Value {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapChargePointStatus(final String value, final ChargePointStatus expected) {
        // when
        final ChargePointStatus chargePointStatus = ChargePointStatus.fromValue(value);

        // then
        assertThat(chargePointStatus).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapBadChargePointStatus() {
        // given
        final String badValue = "BadValue";
        // when
        final ChargePointStatus chargePointStatus = ChargePointStatus.fromValue(badValue);
        // then
        assertThat(chargePointStatus).isNull();
    }
}
