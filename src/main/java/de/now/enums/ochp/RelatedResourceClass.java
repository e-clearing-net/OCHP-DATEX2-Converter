package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

public enum RelatedResourceClass {

    FEEDBACK_FORM("feedbackForm"),
    OPERATOR_MAP("operatorMap"),
    OPERATOR_PAYMENT("operatorPayment"),
    OWNER_HOMEPAGE("ownerHomepage"),
    STATION_INFO("stationInfo"),
    SURROUNDING_INFO("surroundingInfo");

    RelatedResourceClass(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    private static final Map<String, RelatedResourceClass> CACHE = new HashMap<>();

    static {
        for (RelatedResourceClass relatedResourceClass : RelatedResourceClass.values()) {
            CACHE.put(relatedResourceClass.getValue(), relatedResourceClass);
        }
    }

    public static RelatedResourceClass fromValue(final String value) {
        return CACHE.get(value);
    }
}
