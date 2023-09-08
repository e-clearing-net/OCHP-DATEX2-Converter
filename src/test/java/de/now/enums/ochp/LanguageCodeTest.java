package de.now.enums.ochp;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LanguageCodeTest {

    @Test
    void mapLanguageCode2() {
        // given
        final String code2 = "DE";
        // when
        final LanguageCode languageCode = LanguageCode.fromLanguageCode2or3(code2);
        // then
        assertThat(languageCode).isNotNull().isEqualTo(LanguageCode.DEU);
    }

    @Test
    void mapLanguageCode3() {
        // given
        final String code3 = "DEU";
        // when
        final LanguageCode languageCode = LanguageCode.fromLanguageCode2or3(code3);
        // then
        assertThat(languageCode).isNotNull().isEqualTo(LanguageCode.DEU);
    }

    @Test
    void mapLanguageCode3To2() {
        // given
        final String code3 = "DEU";
        // when
        final String languageCode2 = LanguageCode.convertLanguageCode3to2(code3);
        // then
        assertThat(languageCode2).isNotNull().isEqualTo(LanguageCode.DEU.getAlpha2code());
    }

    @Test
    void mapBadLanguageCode() {
        final String code2 = "BadValue";
        // when
        final LanguageCode languageCode = LanguageCode.fromLanguageCode2or3(code2);
        // then
        assertThat(languageCode).isNull();
    }
}
