package de.now.mapper.ochp;

import eu.ochp._1_4.ChargePointInfo;
import eu.ochp._1_4.GetChargePointListResponse;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

public class TestUtil {
    // Charge Point
    public static final String IN_EVSE_ID = "DE*LND*E12345";
    public static final String IN_LOCATION_ID = "DELNDL10";
    public static final String IN_LOCATION_NAME = "Test Location Name 500";
    public static final String IN_LOCATION_NAME_LANG = "ENG";
    // Charge Point Address
    public static final String IN_STREET = "Test Street Name";
    public static final String IN_HOUSE_NUMBER = "200B";
    public static final String IN_CITY = "Test City";
    public static final String IN_POSTAL_CODE = "ABCDE 1234";
    // Charge Point Location
    public static final String IN_LAT = "50.000000";
    public static final String IN_LON = "-100.000000";

    public static final String IN_TIMESTAMP = "2022-02-21T11:12:57Z";
    public static final String IN_TIME_ZONE = "Europe/Berlin";

    // relatedLocation
    public static final String IN_ENTRANCE_NAME = "Test Entrance Name 1234";

    public static final Float IN_GUARANTEED_POWER = 40F;
    public static final float IN_MAXIMUM_POWER = 200;
    public static final Integer IN_NOMINAL_VOLTAGE = 240;

    private final Unmarshaller unmarshaller;

    public TestUtil() throws JAXBException, SAXException {
        final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        unmarshaller = JAXBContext.newInstance(GetChargePointListResponse.class).createUnmarshaller();
        unmarshaller.setSchema(schemaFactory.newSchema(contextClassLoader.getResource("schemas/ochp-v1_4_1/message-elements.xsd")));
    }

    protected ChargePointInfo getFullChargePointInfo() throws JAXBException {
        return getChargePointInfo("/ochp/full-chargepointinfo.xml");
    }

    protected ChargePointInfo getMinimalChargePointInfo() throws JAXBException {
        return getChargePointInfo("/ochp/minimal-chargepointinfo.xml");
    }

    private ChargePointInfo getChargePointInfo(final String ochpXmlPath) throws JAXBException {
        final GetChargePointListResponse getChargePointListResponse = unmarshaller.unmarshal(
                new StreamSource(getClass().getResourceAsStream(ochpXmlPath)),
                GetChargePointListResponse.class
        ).getValue();

        return getChargePointListResponse.getChargePointInfoArray().get(0);
    }
}
