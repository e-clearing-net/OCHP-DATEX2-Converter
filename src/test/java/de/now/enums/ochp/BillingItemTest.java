package de.now.enums.ochp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BillingItemTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of("energy", BillingItem.ENERGY),
                Arguments.of("mass", BillingItem.MASS),
                Arguments.of("parkingtime", BillingItem.PARKING_TIME),
                Arguments.of("power", BillingItem.POWER),
                Arguments.of("reservation", BillingItem.RESERVATION),
                Arguments.of("reservationtime", BillingItem.RESERVATION_TIME),
                Arguments.of("serviceFee", BillingItem.SERVICE_FEE),
                Arguments.of("usagetime", BillingItem.USAGE_TIME),
                Arguments.of("volume", BillingItem.VOLUME)
        );
    }

    @ParameterizedTest(name = "Value {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapBillingItem(final String value, final BillingItem expected) {
        // when
        final BillingItem billingItem = BillingItem.fromValue(value);

        // then
        assertThat(billingItem).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapBadBillingItem() {
        // given
        final String badValue = "BadValue";
        // when
        final BillingItem billingItem = BillingItem.fromValue(badValue);
        // then
        assertThat(billingItem).isNull();
    }
}
