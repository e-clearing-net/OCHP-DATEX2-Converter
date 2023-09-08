package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

public enum GeneralLocation {

    ON_STREET("on-street"),
    PARKING_GARAGE("parking-garage"),
    UNDERGROUND_GARAGE("underground-garage"),
    PARKING_LOT("parking-lot"),
    OTHER("other"),
    UNKNOWN("unknown"),
    PRIVATE("private");

    GeneralLocation(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    private static final Map<String, GeneralLocation> CACHE = new HashMap<>();

    static {
        for (GeneralLocation generalLocation : GeneralLocation.values()) {
            CACHE.put(generalLocation.getValue(), generalLocation);
        }
    }

    public static GeneralLocation fromValue(final String value) {
        return CACHE.get(value);
    }
}
