package de.now.mapper.ochp;

import eu.datex2.schema._3.energyinfrastructure.EnergyInfrastructureSite;
import eu.datex2.schema._3.energyinfrastructure.EnergyInfrastructureStation;
import eu.datex2.schema._3.energyinfrastructure.EnergyInfrastructureTable;
import eu.ochp._1_4.ChargePointInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {
        AddressMapper.class,
        AuthMethodMapper.class,
        ContactDataMapper.class,
        NameTimestampVersionMapper.class,
        OpeningTimesMapper.class
})
public interface EnergyInfrastructureMapper {

    EnergyInfrastructureMapper INSTANCE = Mappers.getMapper(EnergyInfrastructureMapper.class);

    @Mapping(source = ".", target = "entrance", qualifiedByName = "toLocationEntranceList")
    @Mapping(source = ".", target = "name", qualifiedByName = "toLocationName")
    @Mapping(source = ".", target = "locationReference")
    @Mapping(source = ".", target = "operator")
    @Mapping(source = ".", target = "operatingHours")
    @Mapping(source = "locationId", target = "externalIdentifier")
    @Mapping(source = "locationId", target = "id")
    @Mapping(target = "lastUpdated", expression = "java(nameTimestampVersionMapper.toTimestampNow())")
    @Mapping(target = "brand", expression = "java(null)")
    @Mapping(target = "owner", expression = "java(null)")
    @Mapping(target = "rates", expression = "java(null)") /* will be set in the converter **/
    @Mapping(target = "version", expression = "java(nameTimestampVersionMapper.toVersion())")
    EnergyInfrastructureSite toEnergyInfrastructureSite(final ChargePointInfo chargePointInfo);

    @Mapping(source = ".", target = "energyProvider")
    @Mapping(source = ".", target = "locationReference")
    @Mapping(source = ".", target = "name", qualifiedByName = "toLocationName")
    @Mapping(source = ".", target = "operator")
    @Mapping(source = ".", target = "operatingHours")
    @Mapping(source = "authMethods", target = "authenticationAndIdentificationMethods")
    @Mapping(source = "locationId", target = "externalIdentifier")
    @Mapping(source = "locationId", target = "id")
    @Mapping(target = "lastUpdated", expression = "java(nameTimestampVersionMapper.toTimestampNow())")
    @Mapping(target = "owner", expression = "java(null)")
    @Mapping(target = "rates", expression = "java(null)")
    @Mapping(target = "refillPoint", expression = "java(null)") /* will be set in the converter **/
    @Mapping(target = "version", expression = "java(nameTimestampVersionMapper.toVersion())")
    EnergyInfrastructureStation toEnergyInfrastructureStation(final ChargePointInfo chargePointInfo);

    @Mapping(source = ".", target = "id", qualifiedByName = "toOperatorName5chars")
    @Mapping(target = "energyInfrastructureSite", expression = "java(null)") /* will be set in the converter **/
    @Mapping(target = "version", expression = "java(nameTimestampVersionMapper.toVersion())")
    EnergyInfrastructureTable toEnergyInfrastructureTable(final ChargePointInfo chargePointInfo);
}