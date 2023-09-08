package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

public enum EvseStatusMajor {

    AVAILABLE("available"),
    NOT_AVAILABLE("not-available"),
    UNKNOWN("unknown");

    EvseStatusMajor(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    private static final Map<String, EvseStatusMajor> CACHE = new HashMap<>();

    static {
        for (EvseStatusMajor evseStatusMajor : EvseStatusMajor.values()) {
            CACHE.put(evseStatusMajor.getValue(), evseStatusMajor);
        }
    }

    public static EvseStatusMajor fromValue(final String value) {
        return CACHE.get(value);
    }
}
