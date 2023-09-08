package de.now.mapper.ochp;

import de.now.enums.ochp.AuthMethod;
import de.now.factory.OchpFactory;
import eu.datex2.schema._3.facilities.*;
import eu.ochp._1_4.AuthMethodType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AuthMethodMapperTest {

    @Test
    void mapAuthMethodTypeToRate() {
        // given
        final AuthMethodType authMethodType1 = OchpFactory.createAuthMethodType(AuthMethod.DIRECT_CASH);
        // when
        final Rates rates = AuthMethodMapper.INSTANCE.toRates(List.of(authMethodType1));
        // then
        assertThat(rates).isNotNull();
        assertThat(rates.getClass()).isEqualTo(GeneralRateInformation.class);

        final PaymentMethod paymentMethod = rates.getPaymentMethod();
        assertThat(paymentMethod).isNotNull();
    }

    @Test
    void mapAuthMethodTypeToGeneralRateInformation() {
        // given
        final AuthMethodType authMethodType = OchpFactory.createAuthMethodType(AuthMethod.DIRECT_CASH);
        // when
        final GeneralRateInformation generalRateInformation = AuthMethodMapper.INSTANCE.toGeneralRateInformation(List.of(authMethodType));
        // then
        assertThat(generalRateInformation).isNotNull();

        final PaymentMethod paymentMethod = generalRateInformation.getPaymentMethod();
        assertThat(paymentMethod).isNotNull();
    }

    @Test
    void mapAuthMethodTypeToPaymentMethod() {
        // given
        final AuthMethodType authMethodType1 = OchpFactory.createAuthMethodType(AuthMethod.DIRECT_CASH);
        final AuthMethodType authMethodType2 = OchpFactory.createAuthMethodType(AuthMethod.DIRECT_CREDITCARD);
        final AuthMethodType authMethodType3 = OchpFactory.createAuthMethodType(AuthMethod.DIRECT_DEBITCARD);
        // when
        final PaymentMethod paymentMethod = AuthMethodMapper.INSTANCE.toPaymentMethod(List.of(authMethodType1, authMethodType2, authMethodType3));
        // then
        assertThat(paymentMethod).isNotNull();

        final List<MeansOfPaymentEnum> paymentMeans = paymentMethod.getPaymentMeans();
        assertThat(paymentMeans).isNotNull().hasSize(3);

        final MeansOfPaymentEnum meansOfPaymentEnum1 = paymentMeans.get(0);
        assertThat(meansOfPaymentEnum1).isNotNull();

        final MeansOfPaymentEnum meansOfPaymentEnum2 = paymentMeans.get(1);
        assertThat(meansOfPaymentEnum2).isNotNull();

        final MeansOfPaymentEnum meansOfPaymentEnum3 = paymentMeans.get(2);
        assertThat(meansOfPaymentEnum3).isNotNull();
    }

    @Test
    void mapAuthMethodTypeToPaymentMeanList() {
        // given
        final AuthMethodType authMethodType = OchpFactory.createAuthMethodType(AuthMethod.DIRECT_CASH);
        // when
        final List<MeansOfPaymentEnum> meansOfPaymentEnums = AuthMethodMapper.INSTANCE.toPaymentMeanList(List.of(authMethodType));
        // then
        assertThat(meansOfPaymentEnums).isNotNull().hasSize(1);

        final MeansOfPaymentEnum meansOfPaymentEnum = meansOfPaymentEnums.get(0);
        assertThat(meansOfPaymentEnum).isNotNull();

        final MeansOfPaymentEnum2 value = meansOfPaymentEnum.getValue();
        assertThat(value).isNotNull().isEqualTo(MeansOfPaymentEnum2.CASH_COINS_AND_BILLS);
    }

    @Test
    void mapAuthMethodTypeToMeansOfPaymentEnum() {
        // given
        final AuthMethodType authMethodType = OchpFactory.createAuthMethodType(AuthMethod.DIRECT_CREDITCARD);
        // when
        final MeansOfPaymentEnum meansOfPaymentEnum = AuthMethodMapper.INSTANCE.toMeansOfPaymentEnum(authMethodType);
        // then
        assertThat(meansOfPaymentEnum).isNotNull();

        final MeansOfPaymentEnum2 value = meansOfPaymentEnum.getValue();
        assertThat(value).isNotNull().isEqualTo(MeansOfPaymentEnum2.PAYMENT_CREDIT_CARD);
    }

    @Test
    void mapAuthMethodTypeToMeansOfPaymentEnum2String() {
        // given
        final String inValue = AuthMethod.DIRECT_DEBITCARD.getValue();
        // when
        final MeansOfPaymentEnum2 meansOfPaymentEnum2 = AuthMethodMapper.INSTANCE.toMeansOfPaymentEnum2(inValue);
        // then
        assertThat(meansOfPaymentEnum2).isNotNull().isEqualTo(MeansOfPaymentEnum2.PAYMENT_DEBIT_CARD);
    }

    @Test
    void mapAuthMethodTypeToMeansOfPaymentEnum2BadString() {
        // given
        final String badValue = "BadValue";
        // when
        final MeansOfPaymentEnum2 meansOfPaymentEnum2 = AuthMethodMapper.INSTANCE.toMeansOfPaymentEnum2(badValue);
        // then
        assertThat(meansOfPaymentEnum2).isNull();
    }

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of(AuthMethod.DIRECT_CASH, MeansOfPaymentEnum2.CASH_COINS_AND_BILLS),
                Arguments.of(AuthMethod.DIRECT_CREDITCARD, MeansOfPaymentEnum2.PAYMENT_CREDIT_CARD),
                Arguments.of(AuthMethod.DIRECT_DEBITCARD, MeansOfPaymentEnum2.PAYMENT_DEBIT_CARD),
                Arguments.of(AuthMethod.PUBLIC, MeansOfPaymentEnum2.UNKNOWN),
                Arguments.of(AuthMethod.LOCAL_KEY, MeansOfPaymentEnum2.UNKNOWN),
                Arguments.of(AuthMethod.RFID_MIFARE_CLS, MeansOfPaymentEnum2.UNKNOWN),
                Arguments.of(AuthMethod.RFID_MIFARE_DES, MeansOfPaymentEnum2.UNKNOWN),
                Arguments.of(AuthMethod.RFID_CALYPSO, MeansOfPaymentEnum2.UNKNOWN),
                Arguments.of(AuthMethod.IEC_15118, MeansOfPaymentEnum2.UNKNOWN),
                Arguments.of(AuthMethod.OCHP_DIRECT_AUTH, MeansOfPaymentEnum2.MOBILE_ACCOUNT),
                Arguments.of(AuthMethod.OPERATOR_AUTH, MeansOfPaymentEnum2.MOBILE_ACCOUNT)
        );
    }

    @ParameterizedTest(name = "AuthMethod {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapAuthMethodTypeToMeansOfPaymentEnum2Enum(final AuthMethod authMethod, final MeansOfPaymentEnum2 expected) {
        // when
        final MeansOfPaymentEnum2 meansOfPaymentEnum2 = AuthMethodMapper.INSTANCE.toMeansOfPaymentEnum2(authMethod);
        // then
        assertThat(meansOfPaymentEnum2).isEqualTo(expected);
    }
}
