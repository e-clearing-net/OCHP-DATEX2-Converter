package de.now.mapper.ochp;

import de.now.enums.ochp.AuthMethod;
import eu.datex2.schema._3.energyinfrastructure.AuthenticationAndIdentificationEnum;
import eu.datex2.schema._3.energyinfrastructure.AuthenticationAndIdentificationEnum2;
import eu.datex2.schema._3.facilities.*;
import eu.ochp._1_4.AuthMethodType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

@Mapper
public interface AuthMethodMapper {

    AuthMethodMapper INSTANCE = Mappers.getMapper(AuthMethodMapper.class);

    @Mapping(source = "authMethodType", target = "value")
    AuthenticationAndIdentificationEnum toAuthenticationAndIdentificationEnum(final AuthMethodType authMethods);

    @ValueMapping(source = "DIRECT_CASH", target = "CASH_PAYMENT")
    @ValueMapping(source = "DIRECT_CREDITCARD", target = "CREDIT_CARD")
    @ValueMapping(source = "DIRECT_DEBITCARD", target = "DEBIT_CARD")
    @ValueMapping(source = "IEC_15118", target = "OVER_THE_AIR")
    @ValueMapping(source = "LOCAL_KEY", target = "EXTENDED") /* ??? waiting for info **/ // TODO
    @ValueMapping(source = "OCHP_DIRECT_AUTH", target = "APPS")
    @ValueMapping(source = "OPERATOR_AUTH", target = "WEBSITE")
    @ValueMapping(source = "PUBLIC", target = "UNLIMITED_ACCESS")
    @ValueMapping(source = "RFID_MIFARE_CLS", target = "MIFARE_CLASSIC")
    @ValueMapping(source = "RFID_MIFARE_DES", target = "MIFARE_DESFIRE")
    @ValueMapping(source = "RFID_CALYPSO", target = "CALYPSO")
    AuthenticationAndIdentificationEnum2 toAuthenticationAndIdentificationEnum2(final AuthMethod authMethod);

    List<AuthenticationAndIdentificationEnum> toAuthenticationAndIdentificationEnumList(final List<AuthMethodType> authMethods);

    @Mapping(source = "authMethodType", target = "value")
    MeansOfPaymentEnum toMeansOfPaymentEnum(final AuthMethodType authMethods);

    @ValueMapping(source = "DIRECT_CASH", target = "CASH_COINS_AND_BILLS")
    @ValueMapping(source = "DIRECT_CREDITCARD", target = "PAYMENT_CREDIT_CARD")
    @ValueMapping(source = "DIRECT_DEBITCARD", target = "PAYMENT_DEBIT_CARD")
    @ValueMapping(source = "PUBLIC", target = "UNKNOWN")
    @ValueMapping(source = "LOCAL_KEY", target = "UNKNOWN")
    @ValueMapping(source = "RFID_MIFARE_CLS", target = "UNKNOWN")
    @ValueMapping(source = "RFID_MIFARE_DES", target = "UNKNOWN")
    @ValueMapping(source = "RFID_CALYPSO", target = "UNKNOWN")
    @ValueMapping(source = "IEC_15118", target = "UNKNOWN")
    @ValueMapping(source = "OCHP_DIRECT_AUTH", target = "MOBILE_ACCOUNT")
    @ValueMapping(source = "OPERATOR_AUTH", target = "MOBILE_ACCOUNT")
    MeansOfPaymentEnum2 toMeansOfPaymentEnum2(final AuthMethod authMethod);

    List<MeansOfPaymentEnum> toPaymentMeanList(final List<AuthMethodType> authMethods);

    default MeansOfPaymentEnum2 toMeansOfPaymentEnum2(final String authMethodType) {
        return toMeansOfPaymentEnum2(AuthMethod.fromValue(authMethodType));
    }

    default AuthenticationAndIdentificationEnum2 toAuthenticationAndIdentificationEnum2(final String authMethodType) {
        return toAuthenticationAndIdentificationEnum2(AuthMethod.fromValue(authMethodType));
    }

    default GeneralRateInformation toGeneralRateInformation(final List<AuthMethodType> authMethods) {
        final GeneralRateInformation generalRateInformation = new GeneralRateInformation();
        generalRateInformation.setPaymentMethod(toPaymentMethod(authMethods));
        return generalRateInformation;
    }

    default PaymentMethod toPaymentMethod(final List<AuthMethodType> authMethods) {
        final PaymentMethod paymentMethod = new PaymentMethod();
        final List<MeansOfPaymentEnum> meansOfPaymentEnums = toPaymentMeanList(authMethods);
        if (meansOfPaymentEnums != null) {
            // remove duplicates of meansOfPaymentEnums - many source values mapped to the same target value
            paymentMethod.getPaymentMeans().addAll(removeDuplicates(meansOfPaymentEnums));
        }
        return paymentMethod;
    }

    private Collection<MeansOfPaymentEnum> removeDuplicates(final List<MeansOfPaymentEnum> meansOfPaymentEnums) {
        final EnumMap<MeansOfPaymentEnum2, MeansOfPaymentEnum> enumMap = new EnumMap<>(MeansOfPaymentEnum2.class);
        for (MeansOfPaymentEnum meansOfPaymentEnum : meansOfPaymentEnums) {
            enumMap.put(meansOfPaymentEnum.getValue(), meansOfPaymentEnum);
        }
        return enumMap.values();
    }

    @ObjectFactory
    default Rates toRates(final List<AuthMethodType> authMethods) {
        return toGeneralRateInformation(authMethods);
    }
}
