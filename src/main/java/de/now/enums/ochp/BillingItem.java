package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

public enum BillingItem {

    ENERGY("energy"),
    MASS("mass"),
    PARKING_TIME("parkingtime"),
    POWER("power"),
    RESERVATION("reservation"),
    RESERVATION_TIME("reservationtime"),
    SERVICE_FEE("serviceFee"),
    USAGE_TIME("usagetime"),
    VOLUME("volume");

    BillingItem(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    private static final Map<String, BillingItem> CACHE = new HashMap<>();

    static {
        for (BillingItem billingItem : BillingItem.values()) {
            CACHE.put(billingItem.getValue(), billingItem);
        }
    }

    public static BillingItem fromValue(String value) {
        return CACHE.get(value);
    }
}
