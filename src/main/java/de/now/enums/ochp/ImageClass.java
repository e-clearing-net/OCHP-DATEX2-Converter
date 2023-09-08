package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

public enum ImageClass {

    NETWORK_LOGO("networkLogo"),
    OPERATOR_LOGO("operatorLogo"),
    OWNER_LOGO("ownerLogo"),
    STATION_PHOTO("stationPhoto"),
    LOCATION_PHOTO("locationPhoto"),
    ENTRANCE_PHOTO("entrancePhoto"),
    OTHER_PHOTO("otherPhoto"),
    OTHER_LOGO("otherLogo"),
    OTHER_GRAPHIC("otherGraphic");

    ImageClass(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    private static final Map<String, ImageClass> CACHE = new HashMap<>();

    static {
        for (ImageClass imageClass : ImageClass.values()) {
            CACHE.put(imageClass.getValue(), imageClass);
        }
    }

    public static ImageClass fromValue(final String value) {
        return CACHE.get(value);
    }
}
