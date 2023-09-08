package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

public enum ChargePointStatus {

    CLOSED("Closed"),
    INOPERATIVE("Inoperative"),
    OPERATIVE("Operative"),
    PLANNED("Planned"),
    UNKNOWN("Unknown");

    ChargePointStatus(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    private static final Map<String, ChargePointStatus> CACHE = new HashMap<>();

    static {
        for (ChargePointStatus chargePointStatus : ChargePointStatus.values()) {
            CACHE.put(chargePointStatus.getValue(), chargePointStatus);
        }
    }

    public static ChargePointStatus fromValue(String value) {
        return CACHE.get(value);
    }
}
