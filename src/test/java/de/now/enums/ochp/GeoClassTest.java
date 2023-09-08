package de.now.enums.ochp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GeoClassTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of("entrance", GeoClass.ENTRANCE),
                Arguments.of("exit", GeoClass.EXIT),
                Arguments.of("access", GeoClass.ACCESS),
                Arguments.of("ui", GeoClass.UI),
                Arguments.of("other", GeoClass.OTHER)
        );
    }

    @ParameterizedTest(name = "Value {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapGeoClass(final String value, final GeoClass expected) {
        // when
        final GeoClass geoClass = GeoClass.fromValue(value);

        // then
        assertThat(geoClass).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapBadGeoClass() {
        // given
        final String badValue = "BadValue";
        // when
        final GeoClass geoClass = GeoClass.fromValue(badValue);
        // then
        assertThat(geoClass).isNull();
    }
}
