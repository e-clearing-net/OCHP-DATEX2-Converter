package de.now.mapper.ochp;

import eu.datex2.schema._3.common.MultilingualString;
import eu.datex2.schema._3.common.MultilingualStringValue;
import eu.datex2.schema._3.energyinfrastructure.*;
import eu.datex2.schema._3.facilities.OperatingHours;
import eu.datex2.schema._3.facilities.Organisation;
import eu.datex2.schema._3.facilities.Rates;
import eu.datex2.schema._3.locationreferencing.Location;
import eu.datex2.schema._3.locationreferencing.LocationReference;
import eu.ochp._1_4.ChargePointInfo;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

import static de.now.mapper.ochp.TestUtil.IN_LOCATION_ID;
import static de.now.mapper.ochp.TestUtil.IN_LOCATION_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class EnergyInfrastructureMapperTest {

    private final TestUtil testUtil = new TestUtil();

    public EnergyInfrastructureMapperTest() throws JAXBException, SAXException {
    }

    @Test
    void mapEnergyInfrastructureStation() throws JAXBException {
        // given
        final ChargePointInfo chargePointInfo = testUtil.getFullChargePointInfo();

        // when
        final EnergyInfrastructureStation energyInfrastructureStation = EnergyInfrastructureMapper.INSTANCE.toEnergyInfrastructureStation(chargePointInfo);

        // then
        assertThat(energyInfrastructureStation).isNotNull();

        final Organisation energyProvider = energyInfrastructureStation.getEnergyProvider();
        assertThat(energyProvider).isNotNull();

        final LocationReference locationReference = energyInfrastructureStation.getLocationReference();
        assertThat(locationReference).isNotNull();

        final MultilingualString name = energyInfrastructureStation.getName();
        assertThat(name).isNotNull();

        final MultilingualStringValue multilingualStringValue = name.getValues().getValue().get(0);
        assertThat(multilingualStringValue).isNotNull();

        final String value = multilingualStringValue.getValue();
        assertThat(value).isNotNull().isEqualTo(IN_LOCATION_NAME);

        final Organisation operator = energyInfrastructureStation.getOperator();
        assertThat(operator).isNotNull();

        final OperatingHours operatingHours = energyInfrastructureStation.getOperatingHours();
        assertThat(operatingHours).isNotNull();

        final String version = energyInfrastructureStation.getVersion();
        assertThat(version).isNotNull();

        final List<AuthenticationAndIdentificationEnum> authenticationAndIdentificationMethods = energyInfrastructureStation.getAuthenticationAndIdentificationMethods();
        assertThat(authenticationAndIdentificationMethods).hasSize(1);

        final String externalIdentifier = energyInfrastructureStation.getExternalIdentifier();
        assertThat(externalIdentifier).isNotNull().isEqualTo(IN_LOCATION_ID);

        final String id = energyInfrastructureStation.getId();
        assertThat(id).isNotNull().isEqualTo(IN_LOCATION_ID);

        final XMLGregorianCalendar lastUpdated = energyInfrastructureStation.getLastUpdated();
        assertThat(lastUpdated).isNotNull();

        final Organisation owner = energyInfrastructureStation.getOwner();
        assertThat(owner).isNull();

        final Rates rates = energyInfrastructureStation.getRates();
        assertThat(rates).isNull();

        final List<RefillPoint> refillPoint = energyInfrastructureStation.getRefillPoint();
        assertThat(refillPoint).isEmpty();
    }

    @Test
    void mapEnergyInfrastructureSite() throws JAXBException {
        // given
        final ChargePointInfo chargePointInfo = testUtil.getFullChargePointInfo();

        // when
        final EnergyInfrastructureSite energyInfrastructureSite = EnergyInfrastructureMapper.INSTANCE.toEnergyInfrastructureSite(chargePointInfo);

        // then
        assertThat(energyInfrastructureSite).isNotNull();

        final List<Location> entrance = energyInfrastructureSite.getEntrance();
        assertThat(entrance).hasSize(1);

        final MultilingualString name = energyInfrastructureSite.getName();
        assertThat(name).isNotNull();

        final MultilingualStringValue multilingualStringValue = name.getValues().getValue().get(0);
        assertThat(multilingualStringValue).isNotNull();

        final String value = multilingualStringValue.getValue();
        assertThat(value).isNotNull().isEqualTo(IN_LOCATION_NAME);

        final LocationReference locationReference = energyInfrastructureSite.getLocationReference();
        assertThat(locationReference).isNotNull();

        final Organisation operator = energyInfrastructureSite.getOperator();
        assertThat(operator).isNotNull();

        final OperatingHours operatingHours = energyInfrastructureSite.getOperatingHours();
        assertThat(operatingHours).isNotNull();

        final String version = energyInfrastructureSite.getVersion();
        assertThat(version).isNotNull();

        final String externalIdentifier = energyInfrastructureSite.getExternalIdentifier();
        assertThat(externalIdentifier).isNotNull().isEqualTo(IN_LOCATION_ID);

        final String id = energyInfrastructureSite.getId();
        assertThat(id).isNotNull().isEqualTo(IN_LOCATION_ID);

        final XMLGregorianCalendar lastUpdated = energyInfrastructureSite.getLastUpdated();
        assertThat(lastUpdated).isNotNull();

        final MultilingualString brand = energyInfrastructureSite.getBrand();
        assertThat(brand).isNull();

        final Organisation owner = energyInfrastructureSite.getOwner();
        assertThat(owner).isNull();

        final Rates rates = energyInfrastructureSite.getRates();
        assertThat(rates).isNull();
    }

    @Test
    void mapEnergyInfrastructureTable() {
        // given
        final String inEvseId = "DE*LND*E1234";
        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setEvseId(inEvseId);
        // when
        final EnergyInfrastructureTable energyInfrastructureTable = EnergyInfrastructureMapper.INSTANCE.toEnergyInfrastructureTable(chargePointInfo);
        // then
        assertThat(energyInfrastructureTable).isNotNull();

        final String id = energyInfrastructureTable.getId();
        assertThat(id).isNotNull().isEqualTo("DELND");

        final String version = energyInfrastructureTable.getVersion();
        assertThat(version).isNotNull();

        final List<EnergyInfrastructureSite> energyInfrastructureSite = energyInfrastructureTable.getEnergyInfrastructureSite();
        assertThat(energyInfrastructureSite).isEmpty();
    }

}