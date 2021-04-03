package io.purplesheep.argeasy.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoubleConverterTest {

    private DoubleConverter unit;

    @BeforeEach
    void setUp() {
        unit = new DoubleConverter();
    }

    @Test
    void throwsIllegalArgumentExceptionWhenGivenNullValue() {
        assertThrows(IllegalArgumentException.class, () -> unit.convert(null));
    }

    @Test
    void throwsIllegalArgumentExceptionWhenGivenEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> unit.convert(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"badger", "10000,00"})
    void throwsIllegalArgumentExceptionWhenGivenNonDoubleValue(final String value) {
        assertThrows(IllegalArgumentException.class, () -> unit.convert(value));
    }

    @Test
    void returnsInfinityWhenGivenDoubleOutsideOfMaxRange() {
        final String massiveDouble = "1E1024"; // max double exponent is 1023
        final Double convertedDouble = unit.convert(massiveDouble);
        assertEquals(Double.POSITIVE_INFINITY, convertedDouble);
    }

    @Test
    void returnsZeroWhenGivenDoubleNeedsTooMuchResolution() {
        final String superSmallDouble = "-4.9E-325"; // smallest double is 4.9E-324
        final Double convertedDouble = unit.convert(superSmallDouble);
        assertEquals(-0.0, convertedDouble);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-Double.MAX_VALUE, -Double.MIN_VALUE, 0, Double.MIN_VALUE,Double.MAX_VALUE})
    void canConvertBoundaryDoublesFromString(final Double doubleString) {
        final Double convertedDouble = unit.convert(doubleString.toString());
        assertEquals(doubleString, convertedDouble);
    }

    @ParameterizedTest
    @MethodSource("basicDoublesWithString")
    void canConvertBasicDoublesFromString(final StringDoublePair pair) {
        final Double convertedDouble = unit.convert(pair.getString());
        assertEquals(pair.getDouble(), convertedDouble);
    }

    private static Stream<Arguments> basicDoublesWithString() {
        return Stream.<StringDoublePair>builder()
                .add(new StringDoublePair("-53", -53.0))
                .add(new StringDoublePair("-0.000000000001", -0.000000000001))
                .add(new StringDoublePair("3.16161616161616", 3.16161616161616))
                .add(new StringDoublePair("98767.23", 98767.23))
                .build()
                .map(Arguments::of);
    }

    private static class StringDoublePair {
        final private String aString;
        final private Double aDouble;

        private StringDoublePair(String aString, Double aDouble) {
            this.aString = aString;
            this.aDouble = aDouble;
        }

        public Double getDouble() {
            return aDouble;
        }

        public String getString() {
            return aString;
        }
    }
}