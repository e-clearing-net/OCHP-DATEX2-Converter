package de.now.datex2.factory;

import eu.datex2.schema._3.common.MultilingualString;
import eu.datex2.schema._3.common.MultilingualStringValue;
import eu.datex2.schema._3.locationextension.AddressLine;
import eu.datex2.schema._3.locationextension.AddressLineTypeEnum;
import eu.datex2.schema._3.locationextension.AddressLineTypeEnum2;

import java.math.BigInteger;

public class Datex2Factory {

    private Datex2Factory() {
    }

    public static AddressLine createAddressLine(
            final AddressLineTypeEnum2 addressLineTypeEnum,
            final String multilingualStringValue,
            final String lang,
            final BigInteger order
    ) {
        final AddressLine addressLine = new AddressLine();
        addressLine.setType(createAddressLineTypeEnum(addressLineTypeEnum));
        addressLine.setText(createMultilingualString(multilingualStringValue, lang));
        addressLine.setOrder(order);
        return addressLine;
    }

    public static AddressLineTypeEnum createAddressLineTypeEnum(final AddressLineTypeEnum2 enumValue) {
        final AddressLineTypeEnum addressLineTypeEnum = new AddressLineTypeEnum();
        addressLineTypeEnum.setValue(enumValue);
        return addressLineTypeEnum;
    }

    public static MultilingualString createMultilingualString(final String value, final String lang) {
        final MultilingualString.Values values = new MultilingualString.Values();
        values.getValue().add(createMultilingualStringValue(value, lang));

        final MultilingualString result = new MultilingualString();
        result.setValues(values);

        return result;
    }

    public static MultilingualStringValue createMultilingualStringValue(final String value, final String lang) {
        final MultilingualStringValue multilingualStringValue = new MultilingualStringValue();
        multilingualStringValue.setValue(value);
        multilingualStringValue.setLang(lang);
        return multilingualStringValue;
    }
}
