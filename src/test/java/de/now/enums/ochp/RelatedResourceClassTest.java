package de.now.enums.ochp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RelatedResourceClassTest {

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of("operatorMap", RelatedResourceClass.OPERATOR_MAP),
                Arguments.of("operatorPayment", RelatedResourceClass.OPERATOR_PAYMENT),
                Arguments.of("stationInfo", RelatedResourceClass.STATION_INFO),
                Arguments.of("surroundingInfo", RelatedResourceClass.SURROUNDING_INFO),
                Arguments.of("ownerHomepage", RelatedResourceClass.OWNER_HOMEPAGE),
                Arguments.of("feedbackForm", RelatedResourceClass.FEEDBACK_FORM)
        );
    }

    @ParameterizedTest(name = "Value {0}, Expected {1}")
    @MethodSource("getTestValues")
    void mapRelatedResourceClass(final String value, final RelatedResourceClass expected) {
        // when
        final RelatedResourceClass relatedResourceClass = RelatedResourceClass.fromValue(value);

        // then
        assertThat(relatedResourceClass).isNotNull().isEqualTo(expected);
    }

    @Test
    void mapBadRelatedResourceClass() {
        // given
        final String badValue = "BadValue";
        // when
        final RelatedResourceClass relatedResourceClass = RelatedResourceClass.fromValue(badValue);
        // then
        assertThat(relatedResourceClass).isNull();
    }
}
