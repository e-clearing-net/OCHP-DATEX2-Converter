package de.now.mapper.ochp;

import de.now.factory.OchpFactory;
import eu.datex2.schema._3.energyinfrastructure.*;
import eu.ochp._1_4.ConnectorFormat;
import eu.ochp._1_4.ConnectorStandard;
import eu.ochp._1_4.ConnectorType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectorMapperTest {

    @Test
    void mapConnectors() {
        // given
        final ConnectorType inConnectorType = new ConnectorType();

        // when
        final List<Connector> connectors = ConnectorMapper.INSTANCE.toConnectorList(List.of(inConnectorType));

        // then
        assertThat(connectors).isNotNull().hasSize(1);

        final Connector connector = connectors.get(0);
        assertThat(connector).isNotNull();
    }

    @Test
    void mapConnector() {
        // given
        final ConnectorType inConnectorType = new ConnectorType();

        // when
        final Connector connector = ConnectorMapper.INSTANCE.toConnector(inConnectorType);

        // then
        assertThat(connector).isNotNull();
    }

    private static Stream<Arguments> getConnectorFormats() {
        return Stream.of(
                Arguments.of("Socket", ConnectorFormatTypeEnum2.SOCKET),
                Arguments.of("Cable", ConnectorFormatTypeEnum2.CABLE_MODE_3)
        );
    }

    @ParameterizedTest(name = "ConnectorFormat {0}, Expected {1}")
    @MethodSource("getConnectorFormats")
    void mapConnectorFormat(final String inConnectorFormatValue, final ConnectorFormatTypeEnum2 expected) {
        // given
        final ConnectorFormat inConnectorFormat = OchpFactory.createConnectorFormat(de.now.enums.ochp.ConnectorFormat.fromValue(inConnectorFormatValue));

        // when
        final ConnectorFormatTypeEnum connectorFormatTypeEnum = ConnectorMapper.INSTANCE.toConnectorFormatTypeEnum(inConnectorFormat);

        // then
        assertThat(connectorFormatTypeEnum).isNotNull();

        final ConnectorFormatTypeEnum2 value = connectorFormatTypeEnum.getValue();
        assertThat(value).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapConnectorType() {
        // given
        final ConnectorStandard inConnectorStandard = OchpFactory.createConnectorStandard(de.now.enums.ochp.ConnectorStandard.TESLA_R);

        // when
        final ConnectorTypeEnum connectorTypeEnum = ConnectorMapper.INSTANCE.toConnectorTypeEnum(inConnectorStandard);

        // then
        assertThat(connectorTypeEnum).isNotNull();

        final ConnectorTypeEnum2 value = connectorTypeEnum.getValue();
        assertThat(value).isNotNull().isEqualTo(ConnectorTypeEnum2.TESLA_R);
    }

}
