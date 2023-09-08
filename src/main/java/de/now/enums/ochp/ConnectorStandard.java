package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

public enum ConnectorStandard {

    CHADEMO("Chademo"),

    DOMESTIC_A("DOMESTIC_A"),
    DOMESTIC_B("DOMESTIC_B"),
    DOMESTIC_C("DOMESTIC_C"),
    DOMESTIC_D("DOMESTIC_D"),
    DOMESTIC_E("DOMESTIC_E"),
    DOMESTIC_F("DOMESTIC_F"),
    DOMESTIC_G("DOMESTIC_G"),
    DOMESTIC_H("DOMESTIC_H"),
    DOMESTIC_I("DOMESTIC_I"),
    DOMESTIC_J("DOMESTIC_J"),
    DOMESTIC_K("DOMESTIC_K"),
    DOMESTIC_L("DOMESTIC_L"),

    IEC_60309_2_SINGLE_16("IEC_60309_2_single_16"),
    IEC_60309_2_THREE_16("IEC_60309_2_three_16"),
    IEC_60309_2_THREE_32("IEC_60309_2_three_32"),
    IEC_60309_2_THREE_64("IEC_60309_2_three_64"),

    IEC_62196_T1("IEC_62196_T1"),
    IEC_62196_T1_COMBO("IEC_62196_T1_COMBO"),
    IEC_62196_T2("IEC_62196_T2"),
    IEC_62196_T2_COMBO("IEC_62196_T2_COMBO"),
    IEC_62196_T3A("IEC_62196_T3A"),
    IEC_62196_T3C("IEC_62196_T3C"),

    TESLA_R("TESLA_R"),
    TESLA_S("TESLA_S");

    ConnectorStandard(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    private static final Map<String, ConnectorStandard> CACHE = new HashMap<>();

    static {
        for (ConnectorStandard connectorStandard : ConnectorStandard.values()) {
            CACHE.put(connectorStandard.getValue(), connectorStandard);
        }
    }

    public static ConnectorStandard fromValue(final String value) {
        return CACHE.get(value);
    }
}
