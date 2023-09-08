package de.now.converter;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import de.now.helper.DateTimeHelper;
import de.now.mapper.ochp.NameTimestampVersionMapper;
import de.now.mapper.ochp.TestUtil;
import de.now.mapper.ochp.ChargePointInfoMapper;
import de.now.mapper.ochp.EnergyInfrastructureMapper;
import eu.datex2.schema._3.common.InternationalIdentifier;
import eu.datex2.schema._3.common.PayloadPublication;
import eu.datex2.schema._3.energyinfrastructure.*;
import eu.ochp._1_4.ChargePointInfo;
import eu.ochp._1_4.GetChargePointListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ConverterSchemaTest {

    private Unmarshaller unmarshaller;
    private Marshaller marshaller;

    @BeforeEach
    public void before() throws JAXBException, SAXException {
        final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        unmarshaller = JAXBContext.newInstance(GetChargePointListResponse.class).createUnmarshaller();
        unmarshaller.setSchema(schemaFactory.newSchema(contextClassLoader.getResource("schemas/ochp-v1_4_1/message-elements.xsd")));

        marshaller = JAXBContext.newInstance(PayloadPublication.class).createMarshaller();
        marshaller.setSchema(schemaFactory.newSchema(contextClassLoader.getResource("schemas/datex2-v3_2/DATEXII_3_D2Payload.xsd")));
        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
            @Override
            public String getPreferredPrefix(String arg0, String arg1, boolean arg2) {
                return "ns";
            }
        });
    }

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of("/ochp/full-chargepointinfo.xml"),
                Arguments.of("/ochp/minimal-chargepointinfo.xml")
        );
    }

    @ParameterizedTest
    @MethodSource("getTestValues")
    void mapOchpToDatex(final String ochpXmlPath) throws JAXBException {
        // when
        final GetChargePointListResponse getChargePointListResponse = unmarshaller.unmarshal(
                new StreamSource(getClass().getResourceAsStream(ochpXmlPath)),
                GetChargePointListResponse.class
        ).getValue();

        // then
        final List<ChargePointInfo> chargePointInfoArray = getChargePointListResponse.getChargePointInfoArray();
        assertThat(chargePointInfoArray).isNotEmpty();

        final ChargePointInfo chargePointInfo = chargePointInfoArray.get(0);
        assertThat(chargePointInfo).isNotNull();

        final ElectricChargingPoint electricChargingPoint = ChargePointInfoMapper.INSTANCE.toElectricChargingPoint(chargePointInfo);
        assertThat(electricChargingPoint).isNotNull();

        final EnergyInfrastructureMapper mapper = EnergyInfrastructureMapper.INSTANCE;

        final EnergyInfrastructureStation energyInfrastructureStation = mapper.toEnergyInfrastructureStation(chargePointInfo);
        energyInfrastructureStation.getRefillPoint().add(electricChargingPoint);

        final EnergyInfrastructureSite energyInfrastructureSite = mapper.toEnergyInfrastructureSite(chargePointInfo);
        energyInfrastructureSite.getEnergyInfrastructureStation().add(energyInfrastructureStation);

        final EnergyInfrastructureTable energyInfrastructureTable = mapper.toEnergyInfrastructureTable(chargePointInfo);
        energyInfrastructureTable.getEnergyInfrastructureSite().add(energyInfrastructureSite);

        final EnergyInfrastructureTablePublication payloadPublication = new EnergyInfrastructureTablePublication();
        payloadPublication.setPublicationTime(DateTimeHelper.getNowAtUtcAsXMLGregorianCalendar());
        payloadPublication.setLang("de");
        payloadPublication.setModelBaseVersion("3");
        payloadPublication.getEnergyInfrastructureTable().add(energyInfrastructureTable);

        final InternationalIdentifier internationalIdentifier = new InternationalIdentifier();
        internationalIdentifier.setCountry("DE");
        internationalIdentifier.setNationalIdentifier("LND");
        payloadPublication.setPublicationCreator(internationalIdentifier);

        final JAXBElement<PayloadPublication> elem = new JAXBElement<>(new QName("local", "payload"), PayloadPublication.class, payloadPublication);

        final StringWriter writer = new StringWriter();
        marshaller.marshal(elem, writer);
        assertThat(writer).isNotNull();
        assertThat(writer.toString()).isNotEmpty().contains(NameTimestampVersionMapper.INSTANCE.cleanUpAsterisk(TestUtil.IN_EVSE_ID), TestUtil.IN_LOCATION_ID);
        System.out.println(writer);
    }
}