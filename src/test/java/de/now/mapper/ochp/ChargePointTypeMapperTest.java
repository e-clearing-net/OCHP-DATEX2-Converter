package de.now.mapper.ochp;

import de.now.enums.ochp.ChargePointType;
import eu.datex2.schema._3.energyinfrastructure.ChargingModeEnum2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ChargePointTypeMapperTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of(ChargePointType.AC, ChargingModeEnum2.MODE_2_AC_1_P),
                Arguments.of(ChargePointType.DC, ChargingModeEnum2.MODE_4_DC)
        );
    }

    @ParameterizedTest(name = "ChargePointType {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapChargePointType(final ChargePointType chargePointType, final ChargingModeEnum2 expected) {
        // when
        final ChargingModeEnum2 chargingModeEnum2 = ChargePointTypeMapper.INSTANCE.toChargingModeEnum2(chargePointType);

        // then
        assertThat(chargingModeEnum2).isNotNull().isEqualTo(expected);
    }
}
