package de.now.mapper.ochp;

import de.now.enums.ochp.ChargePointType;
import eu.datex2.schema._3.energyinfrastructure.*;
import eu.ochp._1_4.ChargePointInfo;
import eu.ochp._1_4.RatingsType;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {
        AddressMapper.class,
        AuthMethodMapper.class,
        ConnectorMapper.class,
        ContactDataMapper.class,
        OpeningTimesMapper.class,
        NameTimestampVersionMapper.class
})
public interface ChargePointInfoMapper {

    ChargePointInfoMapper INSTANCE = Mappers.getMapper(ChargePointInfoMapper.class);

    @Mapping(source = ".", target = "lastUpdated", qualifiedByName = "toTimestamp")
    @Mapping(source = ".", target = "locationReference")
    @Mapping(source = ".", target = "name", qualifiedByName = "toOperatorName")
    @Mapping(source = ".", target = "operatingHours")
    @Mapping(source = ".", target = "operator")
    @Mapping(source = "authMethods", target = "rates")
    @Mapping(source = "connectors", target = "connector")
    @Mapping(source = "evseId", target = "externalIdentifier", qualifiedByName = "cleanUpAsterisk")
    @Mapping(source = "evseId", target = "id", qualifiedByName = "cleanUpAsterisk")
    @Mapping(target = "owner", expression = "java(null)")
    @Mapping(target = "version", expression = "java(nameTimestampVersionMapper.toVersion())")
    ElectricChargingPoint toElectricChargingPoint(final ChargePointInfo chargePointInfo);

    @AfterMapping
    default void updateConnectors(final ChargePointInfo cpi, @MappingTarget ElectricChargingPoint electricChargingPoint) {
        final String chargePointTypeString = cpi.getChargePointType();
        final ChargePointType chargePointType = ChargePointType.valueOf(chargePointTypeString);

        final ChargingModeEnum chargingModeEnum = ChargePointTypeMapper.INSTANCE.toChargingModeEnum(chargePointType);

        final List<Connector> connectors = electricChargingPoint.getConnector();
        for (Connector connector : connectors) {
            connector.setChargingMode(chargingModeEnum);
            setConnectorPower(cpi, connector);
        }
    }

    @AfterMapping
    default void updateRatings(final ChargePointInfo cpi, @MappingTarget ElectricChargingPoint electricChargingPoint) {
        final RatingsType ratings = cpi.getRatings();
        if (ratings != null) {
            final List<Float> availableChargingPowers = electricChargingPoint.getAvailableChargingPower();

            // OCHP has the power value in kW and DATEX in Watts
            final int toWatts = 1_000;

            final Float guaranteedPower = ratings.getGuaranteedPower();
            if (guaranteedPower != null) {
                availableChargingPowers.add(guaranteedPower * toWatts);
            }

            final float maximumPower = ratings.getMaximumPower();
            availableChargingPowers.add(maximumPower * toWatts);

            final Integer nominalVoltage = ratings.getNominalVoltage();
            if (nominalVoltage != null) {
                electricChargingPoint.getAvailableVoltage().add(nominalVoltage.floatValue());
            }
        }
    }

    private void setConnectorPower(final ChargePointInfo cpi, final Connector connector) {
        float maximumPowerWatts = 0F;
        final RatingsType ratings = cpi.getRatings();
        if (ratings != null) {
            // OCHP has the power value in kW and DATEX in Watts
            final float toWatts = 1_000F;
            maximumPowerWatts = ratings.getMaximumPower() * toWatts;
        }

        final ConnectorTypeEnum connectorType = connector.getConnectorType();
        if (connectorType != null) {
            if (ConnectorTypeEnum2.DOMESTIC_F.equals(connectorType.getValue())) {
                connector.setMaxPowerAtSocket(3_700F);
            } else if (ConnectorTypeEnum2.IEC_62196_T_2.equals(connectorType.getValue())) {
                connector.setMaxPowerAtSocket(Math.min(maximumPowerWatts, 22_000F));
            } else if (ConnectorTypeEnum2.IEC_62196_T_1.equals(connectorType.getValue())) {
                connector.setMaxPowerAtSocket(7_400F);
            } else {
                connector.setMaxPowerAtSocket(maximumPowerWatts);
            }
        }
    }
}
