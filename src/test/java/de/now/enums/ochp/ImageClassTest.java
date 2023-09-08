package de.now.enums.ochp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ImageClassTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of("networkLogo", ImageClass.NETWORK_LOGO),
                Arguments.of("operatorLogo", ImageClass.OPERATOR_LOGO),
                Arguments.of("ownerLogo", ImageClass.OWNER_LOGO),
                Arguments.of("stationPhoto", ImageClass.STATION_PHOTO),
                Arguments.of("locationPhoto", ImageClass.LOCATION_PHOTO),
                Arguments.of("entrancePhoto", ImageClass.ENTRANCE_PHOTO),
                Arguments.of("otherPhoto", ImageClass.OTHER_PHOTO),
                Arguments.of("otherLogo", ImageClass.OTHER_LOGO),
                Arguments.of("otherGraphic", ImageClass.OTHER_GRAPHIC)
        );
    }

    @ParameterizedTest(name = "Value {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapImageClass(final String value, final ImageClass expected) {
        // when
        final ImageClass imageClass = ImageClass.fromValue(value);
        // then
        assertThat(imageClass).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapBadImageClass() {
        // given
        final String badValue = "BadValue";
        // when
        final ImageClass imageClass = ImageClass.fromValue(badValue);
        // then
        assertThat(imageClass).isNull();
    }
}
