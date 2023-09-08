package de.now.converter;

import eu.datex2.schema._3.energyinfrastructure.ElectricChargingPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

class Datex2SchemaTest {

    private JAXBContext jaxbContext;
    private Unmarshaller jaxbUnmarshaller;

    @BeforeEach
    public void before() throws JAXBException {
        jaxbContext = JAXBContext.newInstance(ElectricChargingPoint.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }

    @Test
    void marshall() throws JAXBException {
        final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        final ElectricChargingPoint electricChargingPoint = new ElectricChargingPoint();
        electricChargingPoint.setExternalIdentifier("DE*LND*ETEST*1");

        final StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(new JAXBElement<>(new QName("local", "electricChargingPoint"), ElectricChargingPoint.class, electricChargingPoint), writer);
        System.out.println(writer);
        assertThat(writer).hasToString("<?xml version='1.0' encoding='UTF-8' standalone='yes'?><ns7:electricChargingPoint xmlns='http://datex2.eu/schema/3/common' xmlns:ns6='http://datex2.eu/schema/3/energyInfrastructure' xmlns:ns5='http://datex2.eu/schema/3/commonExtension' xmlns:ns7='local' xmlns:ns2='http://datex2.eu/schema/3/facilities' xmlns:ns4='http://datex2.eu/schema/3/locationReferencing' xmlns:ns3='http://datex2.eu/schema/3/locationExtension'><ns2:externalIdentifier>DE*LND*ETEST*1</ns2:externalIdentifier></ns7:electricChargingPoint>".replace('\'', '\"'));
    }

    @Test
    void unmarshall() throws JAXBException {
        final InputStream resourceAsStream = getClass().getResourceAsStream("/samples/sample-datex2-electricchargingpoint.xml");
        assertThat(resourceAsStream).describedAs("resource not found in classpath").isNotNull();

        final JAXBElement<ElectricChargingPoint> jaxbChargePointInfo = jaxbUnmarshaller.unmarshal(new StreamSource(resourceAsStream), ElectricChargingPoint.class);
        final ElectricChargingPoint electricChargingPoint = jaxbChargePointInfo.getValue();
        assertThat(electricChargingPoint.getExternalIdentifier()).isEqualTo("DE*LND*ETEST*1");
    }

}