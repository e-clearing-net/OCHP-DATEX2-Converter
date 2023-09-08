package de.now.mapper.ochp;

import eu.datex2.schema._3.common.MultilingualString;
import eu.datex2.schema._3.common.MultilingualStringValue;
import eu.datex2.schema._3.common.OverallPeriod;
import eu.datex2.schema._3.energyinfrastructure.ChargingModeEnum;
import eu.datex2.schema._3.energyinfrastructure.ChargingModeEnum2;
import eu.datex2.schema._3.energyinfrastructure.Connector;
import eu.datex2.schema._3.energyinfrastructure.ElectricChargingPoint;
import eu.datex2.schema._3.facilities.*;
import eu.datex2.schema._3.locationextension.Address;
import eu.datex2.schema._3.locationreferencing.PointCoordinates;
import eu.datex2.schema._3.locationreferencing.PointLocation;
import eu.ochp._1_4.ChargePointInfo;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

import static de.now.mapper.ochp.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

class ChargePointInfoMapperTest {

    private final TestUtil testUtil = new TestUtil();

    public ChargePointInfoMapperTest() throws JAXBException, SAXException {
    }

    @Test
    void mapChargePointInfo() throws JAXBException {
        // given
        final ChargePointInfo chargePointInfo = testUtil.getFullChargePointInfo();

        // when
        final ElectricChargingPoint electricChargingPoint = ChargePointInfoMapper.INSTANCE.toElectricChargingPoint(chargePointInfo);

        // then
        assertThat(electricChargingPoint).isNotNull();

        final String externalIdentifier = electricChargingPoint.getExternalIdentifier();
        assertThat(externalIdentifier).isNotNull().isEqualTo(NameTimestampVersionMapper.INSTANCE.cleanUpAsterisk(IN_EVSE_ID));

        final String id = electricChargingPoint.getId();
        assertThat(id).isNotNull().isEqualTo(NameTimestampVersionMapper.INSTANCE.cleanUpAsterisk(IN_EVSE_ID));

        final String version = electricChargingPoint.getVersion();
        assertThat(version).isNotNull();

        final XMLGregorianCalendar lastUpdated = electricChargingPoint.getLastUpdated();
        assertThat(lastUpdated).isNotNull().hasToString(IN_TIMESTAMP);

        final List<Float> availableVoltage = electricChargingPoint.getAvailableVoltage();
        assertThat(availableVoltage).isNotNull().hasSize(1);

        final Float voltage = availableVoltage.get(0);
        assertThat(voltage).isNotNull().isEqualTo(IN_NOMINAL_VOLTAGE.floatValue());

        final List<Float> availableChargingPower = electricChargingPoint.getAvailableChargingPower();
        assertThat(availableChargingPower).isNotNull().hasSize(2);

        final int toWatts = 1_000;

        final Float ChargingPower1 = availableChargingPower.get(0);
        assertThat(ChargingPower1).isNotNull().isEqualTo(IN_GUARANTEED_POWER * toWatts);

        final Float ChargingPower2 = availableChargingPower.get(1);
        assertThat(ChargingPower2).isNotNull().isEqualTo(IN_MAXIMUM_POWER * toWatts);

        final PointLocation pointLocation = (PointLocation) electricChargingPoint.getLocationReference();
        assertThat(pointLocation).isNotNull();

        final Address address = pointLocation.getLocationReferenceExtension().getFacilityLocation().getAddress();
        assertThat(address).isNotNull();

        final PointCoordinates pointCoordinates = pointLocation.getPointByCoordinates().getPointCoordinates();
        assertThat(pointCoordinates).isNotNull();

        final List<Connector> connectors = electricChargingPoint.getConnector();
        assertThat(connectors).isNotNull().hasSize(1);

        final Connector connector = connectors.get(0);
        assertThat(connector).isNotNull();

        final ChargingModeEnum chargingModeEnum = connector.getChargingMode();
        assertThat(chargingModeEnum).isNotNull();

        final ChargingModeEnum2 chargingModeEnum2 = chargingModeEnum.getValue();
        assertThat(chargingModeEnum2).isNotNull().isEqualTo(ChargingModeEnum2.MODE_4_DC);

        float maxPowerAtSocket = connector.getMaxPowerAtSocket();
        assertThat(maxPowerAtSocket).isEqualTo(200_000F);

        final OperatingHours operatingHours = electricChargingPoint.getOperatingHours();
        assertThat(operatingHours).isNotNull();
        assertThat(operatingHours.getClass()).isEqualTo(OperatingHoursSpecification.class);

        final ClosureInformation closureInformation = operatingHours.getClosureInformation();
        assertThat(closureInformation).isNotNull();

        final OperatingHoursSpecification operatingHoursSpecification = (OperatingHoursSpecification) operatingHours;
        final OverallPeriod overallPeriod = operatingHoursSpecification.getOverallPeriod();
        assertThat(overallPeriod).isNotNull();

        final Rates rates = electricChargingPoint.getRates();
        assertThat(rates).isNotNull();
        assertThat(rates.getClass()).isEqualTo(GeneralRateInformation.class);

        final PaymentMethod paymentMethod = rates.getPaymentMethod();
        assertThat(paymentMethod).isNotNull();

        final List<MeansOfPaymentEnum> paymentMeans = paymentMethod.getPaymentMeans();
        assertThat(paymentMeans).isNotNull().hasSize(1);

        final MeansOfPaymentEnum meansOfPaymentEnum = paymentMeans.get(0);
        assertThat(meansOfPaymentEnum).isNotNull();

        final Organisation operator = electricChargingPoint.getOperator();
        assertThat(operator).isNotNull();
        assertThat(operator.getClass()).isEqualTo(OrganisationSpecification.class);

        final OrganisationSpecification organisationSpecification = (OrganisationSpecification) operator;
        final MultilingualString name = organisationSpecification.getName();
        assertThat(name).isNotNull();

        final List<MultilingualStringValue> value = name.getValues().getValue();
        assertThat(value).isNotNull().hasSize(1);

        final MultilingualStringValue multilingualStringValue = value.get(0);
        assertThat(multilingualStringValue).isNotNull();

        final String operatorName = multilingualStringValue.getValue();
        assertThat(operatorName).isNotNull().isEqualTo("DELND");

        final Organisation owner = electricChargingPoint.getOwner();
        assertThat(owner).isNull();
    }
}