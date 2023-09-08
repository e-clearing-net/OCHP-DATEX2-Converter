package de.now.enums.ochp;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryCodeTest {

    @Test
    void mapCountryCode2() {
        // given
        final String code2 = "DE";
        // when
        final CountryCode countryCode = CountryCode.fromCountryCode2or3(code2);
        // then
        assertThat(countryCode).isNotNull().isEqualTo(CountryCode.GERMANY);
    }

    @Test
    void mapCountryCode3() {
        // given
        final String code3 = "DEU";
        // when
        final CountryCode countryCode = CountryCode.fromCountryCode2or3(code3);
        // then
        assertThat(countryCode).isNotNull().isEqualTo(CountryCode.GERMANY);
    }

    @Test
    void mapBadCountryCode() {
        final String code2 = "BadValue";
        // when
        final CountryCode countryCode = CountryCode.fromCountryCode2or3(code2);
        // then
        assertThat(countryCode).isNull();
    }
}
