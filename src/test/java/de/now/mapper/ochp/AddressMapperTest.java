package de.now.mapper.ochp;

import eu.datex2.schema._3.common.MultilingualStringValue;
import eu.datex2.schema._3.locationextension.Address;
import eu.datex2.schema._3.locationextension.AddressLine;
import eu.datex2.schema._3.locationextension.AddressLineTypeEnum2;
import eu.datex2.schema._3.locationreferencing.Location;
import eu.datex2.schema._3.locationreferencing.PointByCoordinates;
import eu.datex2.schema._3.locationreferencing.PointCoordinates;
import eu.datex2.schema._3.locationreferencing.PointLocation;
import eu.ochp._1_4.ChargePointInfo;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.math.BigInteger;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static de.now.mapper.ochp.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

class AddressMapperTest {

    private final TestUtil testUtil = new TestUtil();

    public AddressMapperTest() throws JAXBException, SAXException {
    }

    @Test
    void mapAddress() throws JAXBException {
        // given
        final ChargePointInfo chargePointInfo = testUtil.getFullChargePointInfo();

        // when
        final PointLocation pointLocation = AddressMapper.INSTANCE.toPointLocation(chargePointInfo);

        // then
        assertThat(pointLocation).isNotNull();

        final String timeZone = pointLocation.getLocationReferenceExtension().getFacilityLocation().getTimeZone();
        assertThat(timeZone).isNotNull().isEqualTo(ZoneId.of(IN_TIME_ZONE).getRules().getOffset(Instant.now()).getId());

        final Address address = pointLocation.getLocationReferenceExtension().getFacilityLocation().getAddress();
        assertThat(address).isNotNull();

        final List<AddressLine> addressLines = address.getAddressLine();
        assertThat(addressLines).isNotNull().hasSize(2);

        final AddressLine addressLineStreet = addressLines.get(0);
        assertThat(addressLineStreet.getType().getValue()).isEqualTo(AddressLineTypeEnum2.STREET);
        assertThat(addressLineStreet.getOrder()).isEqualTo(BigInteger.ONE);

        final MultilingualStringValue street = addressLineStreet.getText().getValues().getValue().get(0);
        assertThat(street.getValue()).isEqualTo(IN_STREET);
        assertThat(street.getLang()).isEqualTo("en");

        final AddressLine addressLineHouseNumber = addressLines.get(1);
        assertThat(addressLineHouseNumber.getType().getValue()).isEqualTo(AddressLineTypeEnum2.HOUSE_NUMBER);
        assertThat(addressLineHouseNumber.getOrder()).isEqualTo(BigInteger.TWO);

        final MultilingualStringValue houseNumber = addressLineHouseNumber.getText().getValues().getValue().get(0);
        assertThat(houseNumber.getValue()).isEqualTo(IN_HOUSE_NUMBER);
        assertThat(houseNumber.getLang()).isEqualTo("en");

        final MultilingualStringValue cityValues = address.getCity().getValues().getValue().get(0);
        assertThat(cityValues.getValue()).isEqualTo(IN_CITY);
        assertThat(cityValues.getLang()).isEqualTo("en");

        assertThat(address.getPostcode()).isEqualTo(IN_POSTAL_CODE);
        assertThat(address.getCountryCode()).isEqualTo("DE");
    }

    @Test
    void mapAddressWithoutHouseNumber() throws JAXBException {
        // given
        final ChargePointInfo chargePointInfo = testUtil.getMinimalChargePointInfo();
        chargePointInfo.getChargePointAddress().setHouseNumber(null);

        // when
        final PointLocation pointLocation = AddressMapper.INSTANCE.toPointLocation(chargePointInfo);

        // then
        assertThat(pointLocation).isNotNull();

        final Address address = pointLocation.getLocationReferenceExtension().getFacilityLocation().getAddress();
        assertThat(address).isNotNull();

        final List<AddressLine> addressLines = address.getAddressLine();
        assertThat(addressLines).isNotNull().hasSize(1);

        final AddressLine addressLineStreet = addressLines.get(0);
        assertThat(addressLineStreet.getType().getValue()).isEqualTo(AddressLineTypeEnum2.STREET);
        assertThat(addressLineStreet.getOrder()).isEqualTo(BigInteger.ONE);
    }

    @Test
    void mapLocationCoordinates() throws JAXBException {
        // given
        final ChargePointInfo chargePointInfo = testUtil.getMinimalChargePointInfo();

        // when
        final PointCoordinates pointCoordinates = AddressMapper.INSTANCE.toPointCoordinates(chargePointInfo);

        // then
        assertThat(pointCoordinates).isNotNull();
        assertThat(pointCoordinates.getLatitude()).isEqualTo(Float.parseFloat(IN_LAT));
        assertThat(pointCoordinates.getLongitude()).isEqualTo(Float.parseFloat(IN_LON));
    }

    @Test
    void mapPointLocationEntrance() throws JAXBException {
        // given
        final ChargePointInfo chargePointInfo = testUtil.getFullChargePointInfo();
        chargePointInfo.getChargePointAddress().setHouseNumber(null);

        // when
        final List<Location> pointLocations = AddressMapper.INSTANCE.toLocationEntranceList(chargePointInfo);

        // then
        assertThat(pointLocations).isNotNull().hasSize(1);

        final Location location = pointLocations.get(0);
        assertThat(location).isNotNull();
        assertThat(location.getClass()).isEqualTo(PointLocation.class);

        final PointLocation pointLocation = (PointLocation) location;
        assertThat(pointLocation).isNotNull();

        final PointCoordinates coordinatesForDisplay = pointLocation.getCoordinatesForDisplay();
        assertThat(coordinatesForDisplay).isNotNull();

        float latitude = coordinatesForDisplay.getLatitude();
        assertThat(latitude).isEqualTo(Float.parseFloat(IN_LAT));

        float longitude = coordinatesForDisplay.getLongitude();
        assertThat(longitude).isEqualTo(Float.parseFloat(IN_LON));

        final PointByCoordinates pointByCoordinates = pointLocation.getPointByCoordinates();
        assertThat(pointByCoordinates).isNotNull();

        final PointCoordinates pointCoordinates = pointByCoordinates.getPointCoordinates();
        assertThat(pointCoordinates).isNotNull().isEqualTo(coordinatesForDisplay);

        final Address address = pointLocation.getLocationReferenceExtension().getFacilityLocation().getAddress();
        assertThat(address).isNotNull();

        final List<AddressLine> addressLines = address.getAddressLine();
        assertThat(addressLines).isNotNull().hasSize(1);

        final AddressLine addressLineStreet = addressLines.get(0);
        assertThat(addressLineStreet).isNotNull();
        assertThat(addressLineStreet.getType().getValue()).isEqualTo(AddressLineTypeEnum2.STREET);
        assertThat(addressLineStreet.getOrder()).isEqualTo(BigInteger.ONE);

        final MultilingualStringValue multilingualStringValue = addressLineStreet.getText().getValues().getValue().get(0);
        assertThat(multilingualStringValue.getValue()).isEqualTo(IN_ENTRANCE_NAME);
        assertThat(multilingualStringValue.getLang()).isEqualTo("en");
    }
}
