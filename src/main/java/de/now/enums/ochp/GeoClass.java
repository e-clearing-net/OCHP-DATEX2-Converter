package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

public enum GeoClass {
    ENTRANCE("entrance"),
    EXIT("exit"),
    ACCESS("access"),
    UI("ui"),
    OTHER("other");

    GeoClass(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    private static final Map<String, GeoClass> CACHE = new HashMap<>();

    static {
        for (GeoClass geoClass : GeoClass.values()) {
            CACHE.put(geoClass.getValue(), geoClass);
        }
    }

    public static GeoClass fromValue(String value) {
        return CACHE.get(value);
    }
}
