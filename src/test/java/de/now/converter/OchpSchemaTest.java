package de.now.converter;

import eu.ochp._1_4.ChargePointInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

class OchpSchemaTest {

    private JAXBContext jaxbContext;
    private Unmarshaller jaxbUnmarshaller;

    @BeforeEach
    public void before() throws JAXBException {
        jaxbContext = JAXBContext.newInstance(ChargePointInfo.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }

    @Test
    void marshall() throws JAXBException {
        final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setEvseId("DE*LND*ETEST*1");

        final StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(new JAXBElement<>(new QName("local", "chargePointInfo"), ChargePointInfo.class, chargePointInfo), writer);
        assertThat(writer).hasToString("<?xml version='1.0' encoding='UTF-8' standalone='yes'?><ns2:chargePointInfo xmlns='http://ochp.eu/1.4.1' xmlns:ns2='local'><evseId>DE*LND*ETEST*1</evseId></ns2:chargePointInfo>".replace('\'', '\"'));
    }

    @Test
    void unmarshall() throws JAXBException {
        final InputStream resourceAsStream = getClass().getResourceAsStream("/samples/sample-ochp-chargepoint.xml");
        assertThat(resourceAsStream).isNotNull();

        final JAXBElement<ChargePointInfo> jaxbChargePointInfo = jaxbUnmarshaller.unmarshal(new StreamSource(resourceAsStream), ChargePointInfo.class);
        final ChargePointInfo chargePointInfo = jaxbChargePointInfo.getValue();
        assertThat(chargePointInfo.getEvseId()).isEqualTo("DE*LND*ETEST*1");
        assertThat(chargePointInfo.getTimestamp().getDateTime()).isEqualTo("2021-12-23T09:34:56Z");
    }

}