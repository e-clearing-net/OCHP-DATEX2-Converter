package de.now.mapper.ochp;

import de.now.enums.ochp.ConnectorStandard;
import eu.datex2.schema._3.energyinfrastructure.ConnectorTypeEnum2;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConnectorStandardMapper {

    ConnectorStandardMapper INSTANCE = Mappers.getMapper(ConnectorStandardMapper.class);

    @ValueMapping(source = "IEC_60309_2_SINGLE_16", target = "IEC_60309_X_2_SINGLE_16")
    @ValueMapping(source = "IEC_60309_2_THREE_16", target = "IEC_60309_X_2_THREE_16")
    @ValueMapping(source = "IEC_60309_2_THREE_32", target = "IEC_60309_X_2_THREE_32")
    @ValueMapping(source = "IEC_60309_2_THREE_64", target = "IEC_60309_X_2_THREE_64")
    @ValueMapping(source = "IEC_62196_T1", target = "IEC_62196_T_1")
    @ValueMapping(source = "IEC_62196_T1_COMBO", target = "IEC_62196_T_1_COMBO")
    @ValueMapping(source = "IEC_62196_T2", target = "IEC_62196_T_2")
    @ValueMapping(source = "IEC_62196_T2_COMBO", target = "IEC_62196_T_2_COMBO")
    @ValueMapping(source = "IEC_62196_T3A", target = "IEC_62196_T_3_A")
    @ValueMapping(source = "IEC_62196_T3C", target = "IEC_62196_T_3_C")
    ConnectorTypeEnum2 toConnectorTypeEnum2(final ConnectorStandard connectorStandard);

    default ConnectorTypeEnum2 toConnectorTypeEnum2(final String ochpConnectorStandard) {
        return toConnectorTypeEnum2(ConnectorStandard.fromValue(ochpConnectorStandard));
    }
}
