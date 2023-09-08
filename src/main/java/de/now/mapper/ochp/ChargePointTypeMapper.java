package de.now.mapper.ochp;

import de.now.enums.ochp.ChargePointType;
import eu.datex2.schema._3.energyinfrastructure.ChargingModeEnum;
import eu.datex2.schema._3.energyinfrastructure.ChargingModeEnum2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChargePointTypeMapper {

    ChargePointTypeMapper INSTANCE = Mappers.getMapper(ChargePointTypeMapper.class);

    @Mapping(source = ".", target = "value")
    ChargingModeEnum toChargingModeEnum(final ChargePointType chargePointType);

    @ValueMapping(source = "AC" , target = "MODE_2_AC_1_P")
    @ValueMapping(source = "DC" , target = "MODE_4_DC")
    ChargingModeEnum2 toChargingModeEnum2(final ChargePointType chargePointType);
}
