package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

public enum EvseStatusMinor {

    AVAILABLE("available"),
    BLOCKED("blocked"),
    CHARGING("charging"),
    OUT_OF_ORDER("outoforder"),
    RESERVED("reserved");

    EvseStatusMinor(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    private static final Map<String, EvseStatusMinor> CACHE = new HashMap<>();

    static {
        for (EvseStatusMinor evseStatusMinor : EvseStatusMinor.values()) {
            CACHE.put(evseStatusMinor.getValue(), evseStatusMinor);
        }
    }

    public static EvseStatusMinor fromValue(final String value) {
        return CACHE.get(value);
    }
}
