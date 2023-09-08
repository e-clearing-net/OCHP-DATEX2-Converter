package de.now.enums.ochp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuthMethodTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of("Public", AuthMethod.PUBLIC),
                Arguments.of("LocalKey", AuthMethod.LOCAL_KEY),
                Arguments.of("DirectCash", AuthMethod.DIRECT_CASH),
                Arguments.of("DirectCreditcard", AuthMethod.DIRECT_CREDITCARD),
                Arguments.of("DirectDebitcard", AuthMethod.DIRECT_DEBITCARD),
                Arguments.of("RfidMifareCls", AuthMethod.RFID_MIFARE_CLS),
                Arguments.of("RfidMifareDes", AuthMethod.RFID_MIFARE_DES),
                Arguments.of("RfidCalypso", AuthMethod.RFID_CALYPSO),
                Arguments.of("Iec15118", AuthMethod.IEC_15118),
                Arguments.of("OchpDirectAuth", AuthMethod.OCHP_DIRECT_AUTH),
                Arguments.of("OperatorAuth", AuthMethod.OPERATOR_AUTH)
        );
    }

    @ParameterizedTest(name = "Value {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapAuthMethod(final String value, final AuthMethod expected) {
        // when
        final AuthMethod authMethod = AuthMethod.fromValue(value);

        // then
        assertThat(authMethod).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapBadAuthMethod() {
        // given
        final String badValue = "BadValue";
        // when
        final AuthMethod authMethod = AuthMethod.fromValue(badValue);
        // then
        assertThat(authMethod).isNull();
    }
}
