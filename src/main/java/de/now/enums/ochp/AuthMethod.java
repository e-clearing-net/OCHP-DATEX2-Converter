package de.now.enums.ochp;

import java.util.HashMap;
import java.util.Map;

public enum AuthMethod {

    DIRECT_CASH("DirectCash"),
    DIRECT_CREDITCARD("DirectCreditcard"),
    DIRECT_DEBITCARD("DirectDebitcard"),
    IEC_15118("Iec15118"),
    LOCAL_KEY("LocalKey"),
    OCHP_DIRECT_AUTH("OchpDirectAuth"),
    OPERATOR_AUTH("OperatorAuth"),
    PUBLIC("Public"),
    RFID_MIFARE_CLS("RfidMifareCls"),
    RFID_MIFARE_DES("RfidMifareDes"),
    RFID_CALYPSO("RfidCalypso");

    AuthMethod(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    private static final Map<String, AuthMethod> CACHE = new HashMap<>();

    static {
        for (AuthMethod authMethod : AuthMethod.values()) {
            CACHE.put(authMethod.getValue(), authMethod);
        }
    }

    public static AuthMethod fromValue(String value) {
        return CACHE.get(value);
    }
}
