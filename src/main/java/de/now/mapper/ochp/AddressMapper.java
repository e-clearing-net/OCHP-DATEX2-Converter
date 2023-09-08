package de.now.mapper.ochp;

import de.now.datex2.factory.Datex2Factory;
import de.now.enums.ochp.CountryCode;
import de.now.enums.ochp.GeoClass;
import de.now.enums.ochp.LanguageCode;
import eu.datex2.schema._3.common.MultilingualString;
import eu.datex2.schema._3.locationextension.AddressLine;
import eu.datex2.schema._3.locationextension.AddressLineTypeEnum2;
import eu.datex2.schema._3.locationreferencing.*;
import eu.ochp._1_4.AdditionalGeoPointType;
import eu.ochp._1_4.AddressType;
import eu.ochp._1_4.ChargePointInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

import java.math.BigInteger;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    List<Location> toLocation(final List<PointLocation> pointLocations);

    @Mapping(source = ".", target = "pointByCoordinates.pointCoordinates", qualifiedByName = "toPointCoordinates")
    @Mapping(source = ".", target = "coordinatesForDisplay", qualifiedByName = "toPointCoordinates")
    @Mapping(source = ".", target = "locationReferenceExtension.facilityLocation.address.addressLine", qualifiedByName = "toAddressLine")
    @Mapping(source = "chargePointInfo.chargePointAddress.zipCode", target = "locationReferenceExtension.facilityLocation.address.postcode")
    @Mapping(source = ".", target = "locationReferenceExtension.facilityLocation.address.city", qualifiedByName = "toCityMultilingualString")
    @Mapping(source = "chargePointInfo.chargePointAddress.country", target = "locationReferenceExtension.facilityLocation.address.countryCode", qualifiedByName = "toCountry2from3Chars")
    @Mapping(source = "chargePointInfo.timeZone", target = "locationReferenceExtension.facilityLocation.timeZone", qualifiedByName = "toTimeZone")
    PointLocation toPointLocation(final ChargePointInfo chargePointInfo);

    @Named("toPointCoordinates")
    @Mapping(source = "chargePointInfo.chargePointLocation.lat", target = "latitude")
    @Mapping(source = "chargePointInfo.chargePointLocation.lon", target = "longitude")
    PointCoordinates toPointCoordinates(final ChargePointInfo chargePointInfo);

    @Named("toAddressLine")
    default List<AddressLine> toAddressLine(final ChargePointInfo chargePointInfo) {
        final String lang =  LanguageCode.convertLanguageCode3to2(chargePointInfo.getLocationNameLang());
        final AddressType address = chargePointInfo.getChargePointAddress();

        final AddressLine addressLineStreet = Datex2Factory.createAddressLine(AddressLineTypeEnum2.STREET, address.getAddress(), lang, BigInteger.ONE);

        final String houseNumber = address.getHouseNumber();
        if (houseNumber != null) {
            final AddressLine addressLineHouseNumber = Datex2Factory.createAddressLine(AddressLineTypeEnum2.HOUSE_NUMBER, houseNumber, lang, BigInteger.TWO);
            return List.of(addressLineStreet, addressLineHouseNumber);
        }

        return List.of(addressLineStreet);
    }

    @Named("toCityMultilingualString")
    default MultilingualString toCityMultilingualString(final ChargePointInfo chargePointInfo) {
        return Datex2Factory.createMultilingualString(chargePointInfo.getChargePointAddress().getCity(), LanguageCode.convertLanguageCode3to2(chargePointInfo.getLocationNameLang()));
    }

    @Named("toCountry2from3Chars")
    default String toCountry2from3Chars(final String countryCode3chars) {
        return CountryCode.convertCountryCode3to2(countryCode3chars);
    }

    @Named("toTimeZone")
    default String toTimeZone(String timeZoneValue) {
        if (timeZoneValue == null) {
            return null;
        }

        return ZoneId.of((timeZoneValue)).getRules().getOffset(Instant.now()).getId();
    }

    default Location toLocation(final PointLocation pointLocation) {
        return pointLocation;
    }

    @Named("toLocationEntranceList")
    default List<Location> toLocationEntranceList(final ChargePointInfo chargePointInfo) {
        return toLocation(toPointLocationEntranceList(chargePointInfo));
    }

    @ObjectFactory
    default LocationReference toLocationReference(final ChargePointInfo chargePointInfo) {
        return toPointLocation(chargePointInfo);
    }

    default List<PointLocation> toPointLocationEntranceList(final ChargePointInfo chargePointInfo) {

        final PointLocation pointLocation = toPointLocation(chargePointInfo);

        final AdditionalGeoPointType entranceInfo = getEntranceInfo(chargePointInfo.getRelatedLocation());
        if (entranceInfo != null) {
            // coordinates
            final String lat = entranceInfo.getLat();
            final String lon = entranceInfo.getLon();

            final PointCoordinates coordinatesForDisplay = pointLocation.getCoordinatesForDisplay();
            coordinatesForDisplay.setLatitude(Float.parseFloat(lat));
            coordinatesForDisplay.setLongitude(Float.parseFloat(lon));

            final PointByCoordinates pointByCoordinates = pointLocation.getPointByCoordinates();
            pointByCoordinates.setPointCoordinates(coordinatesForDisplay);

            // street
            final String optionalName = entranceInfo.getName();
            if (optionalName != null) {
                final AddressLine addressLineStreet = Datex2Factory.createAddressLine(AddressLineTypeEnum2.STREET, optionalName, LanguageCode.convertLanguageCode3to2(chargePointInfo.getLocationNameLang()), BigInteger.ONE);
                final List<AddressLine> addressLine = pointLocation.getLocationReferenceExtension().getFacilityLocation().getAddress().getAddressLine();
                addressLine.clear();
                addressLine.add(addressLineStreet);
            }
        }

        return List.of(pointLocation);
    }

    private AdditionalGeoPointType getEntranceInfo(final List<AdditionalGeoPointType> relatedLocations) {
        if (relatedLocations.isEmpty()) {
            return null;
        }

        for (AdditionalGeoPointType additionalGeoPointType : relatedLocations) {
            final GeoClass geoClass = GeoClass.fromValue(additionalGeoPointType.getType());
            if (GeoClass.ENTRANCE.equals(geoClass)) {
                return additionalGeoPointType;
            }
        }

        return null;
    }
}