package de.now.mapper.ochp;

import eu.datex2.schema._3.energyinfrastructure.ConnectorFormatTypeEnum2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectorFormatMapperTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of("Socket", ConnectorFormatTypeEnum2.SOCKET),
                Arguments.of("Cable", ConnectorFormatTypeEnum2.CABLE_MODE_3)
        );
    }

    @ParameterizedTest(name = "ConnectorFormat {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapConnectorFormat(final String connectorFormat, final ConnectorFormatTypeEnum2 expected) {
        // when
        final ConnectorFormatTypeEnum2 connectorFormatTypeEnum2 = ConnectorFormatMapper.INSTANCE.toConnectorFormatTypeEnum2(connectorFormat);

        // then
        assertThat(connectorFormatTypeEnum2).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapBadConnectorFormat() {
        // given
        final String badValue = "BadValue";
        // when
        final ConnectorFormatTypeEnum2 connectorFormatTypeEnum2 = ConnectorFormatMapper.INSTANCE.toConnectorFormatTypeEnum2(badValue);
        // then
        assertThat(connectorFormatTypeEnum2).isNull();
    }
}
