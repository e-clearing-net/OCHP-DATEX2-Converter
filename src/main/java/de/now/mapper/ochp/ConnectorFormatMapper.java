package de.now.mapper.ochp;

import de.now.enums.ochp.ConnectorFormat;
import eu.datex2.schema._3.energyinfrastructure.ConnectorFormatTypeEnum2;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConnectorFormatMapper {

    ConnectorFormatMapper INSTANCE = Mappers.getMapper(ConnectorFormatMapper.class);

    @ValueMapping(source = "CABLE", target = "CABLE_MODE_3")
    ConnectorFormatTypeEnum2 toConnectorFormatTypeEnum2(final ConnectorFormat connectorFormat);

    default ConnectorFormatTypeEnum2 toConnectorFormatTypeEnum2(final String ochpConnectorFormat) {
        return toConnectorFormatTypeEnum2(ConnectorFormat.fromValue(ochpConnectorFormat));
    }
}
