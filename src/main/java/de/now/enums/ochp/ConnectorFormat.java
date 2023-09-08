package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

public enum ConnectorFormat {

    CABLE("Cable"),
    SOCKET("Socket");

    ConnectorFormat(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    private static final Map<String, ConnectorFormat> CACHE = new HashMap<>();

    static {
        for (ConnectorFormat connectorFormat : ConnectorFormat.values()) {
            CACHE.put(connectorFormat.getValue(), connectorFormat);
        }
    }

    public static ConnectorFormat fromValue(final String value) {
        return CACHE.get(value);
    }
}
