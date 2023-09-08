package de.now.mapper.ochp;

import eu.datex2.schema._3.energyinfrastructure.Connector;
import eu.datex2.schema._3.energyinfrastructure.ConnectorFormatTypeEnum;
import eu.datex2.schema._3.energyinfrastructure.ConnectorTypeEnum;
import eu.ochp._1_4.ConnectorFormat;
import eu.ochp._1_4.ConnectorStandard;
import eu.ochp._1_4.ConnectorType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ConnectorFormatMapper.class, ConnectorStandardMapper.class})
public interface ConnectorMapper {

    ConnectorMapper INSTANCE = Mappers.getMapper(ConnectorMapper.class);

    @Mapping(source = "connectorStandard", target = "connectorType")
    @Mapping(source = "connectorFormat", target = "connectorFormat")
    Connector toConnector(final ConnectorType connectorTypes);

    @Mapping(source = "connectorFormat", target = "value")
    ConnectorFormatTypeEnum toConnectorFormatTypeEnum(final ConnectorFormat connectorFormat);

    List<Connector> toConnectorList(final List<ConnectorType> connectorTypes);

    @Mapping(source = "connectorStandard", target = "value")
    ConnectorTypeEnum toConnectorTypeEnum(final ConnectorStandard connectorStandard);
}
