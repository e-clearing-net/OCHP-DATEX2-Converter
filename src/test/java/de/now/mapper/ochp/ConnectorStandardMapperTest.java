package de.now.mapper.ochp;

import eu.datex2.schema._3.energyinfrastructure.ConnectorTypeEnum2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectorStandardMapperTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of("Chademo", ConnectorTypeEnum2.CHADEMO),

                Arguments.of("DOMESTIC_A", ConnectorTypeEnum2.DOMESTIC_A),
                Arguments.of("DOMESTIC_B", ConnectorTypeEnum2.DOMESTIC_B),
                Arguments.of("DOMESTIC_C", ConnectorTypeEnum2.DOMESTIC_C),
                Arguments.of("DOMESTIC_D", ConnectorTypeEnum2.DOMESTIC_D),
                Arguments.of("DOMESTIC_E", ConnectorTypeEnum2.DOMESTIC_E),
                Arguments.of("DOMESTIC_F", ConnectorTypeEnum2.DOMESTIC_F),
                Arguments.of("DOMESTIC_G", ConnectorTypeEnum2.DOMESTIC_G),
                Arguments.of("DOMESTIC_H", ConnectorTypeEnum2.DOMESTIC_H),
                Arguments.of("DOMESTIC_I", ConnectorTypeEnum2.DOMESTIC_I),
                Arguments.of("DOMESTIC_J", ConnectorTypeEnum2.DOMESTIC_J),
                Arguments.of("DOMESTIC_K", ConnectorTypeEnum2.DOMESTIC_K),
                Arguments.of("DOMESTIC_L", ConnectorTypeEnum2.DOMESTIC_L),

                Arguments.of("IEC_60309_2_single_16", ConnectorTypeEnum2.IEC_60309_X_2_SINGLE_16),
                Arguments.of("IEC_60309_2_three_16", ConnectorTypeEnum2.IEC_60309_X_2_THREE_16),
                Arguments.of("IEC_60309_2_three_32", ConnectorTypeEnum2.IEC_60309_X_2_THREE_32),
                Arguments.of("IEC_60309_2_three_64", ConnectorTypeEnum2.IEC_60309_X_2_THREE_64),

                Arguments.of("IEC_62196_T1", ConnectorTypeEnum2.IEC_62196_T_1),
                Arguments.of("IEC_62196_T1_COMBO", ConnectorTypeEnum2.IEC_62196_T_1_COMBO),
                Arguments.of("IEC_62196_T2", ConnectorTypeEnum2.IEC_62196_T_2),
                Arguments.of("IEC_62196_T2_COMBO", ConnectorTypeEnum2.IEC_62196_T_2_COMBO),
                Arguments.of("IEC_62196_T3A", ConnectorTypeEnum2.IEC_62196_T_3_A),
                Arguments.of("IEC_62196_T3C", ConnectorTypeEnum2.IEC_62196_T_3_C),

                Arguments.of("TESLA_R", ConnectorTypeEnum2.TESLA_R),
                Arguments.of("TESLA_S", ConnectorTypeEnum2.TESLA_S)
        );
    }

    @ParameterizedTest(name = "ConnectorStandard {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapConnectorStandard(final String connectorStandard, final ConnectorTypeEnum2 expected) {
        // when
        final ConnectorTypeEnum2 connectorTypeEnum2 = ConnectorStandardMapper.INSTANCE.toConnectorTypeEnum2(connectorStandard);

        // then
        assertThat(connectorTypeEnum2).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapBadConnectorStandard() {
        // given
        final String badValue = "BadValue";
        // when
        final ConnectorTypeEnum2 connectorTypeEnum2 = ConnectorStandardMapper.INSTANCE.toConnectorTypeEnum2(badValue);
        // then
        assertThat(connectorTypeEnum2).isNull();
    }
}
