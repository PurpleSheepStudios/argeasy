package io.purplesheep.argeasy.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class IntegerConverterTest {

    private IntegerConverter unit;

    @BeforeEach
    void setUp() {
        unit = new IntegerConverter();
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
    @ValueSource(strings = {"bob", "10.0", "10000,00"})
    void throwsIllegalArgumentExceptionWhenGivenNonIntegerValue(final String value) {
        assertThrows(IllegalArgumentException.class, () -> unit.convert(value));
    }

    @Test
    void throwsIllegalArgumentExceptionWhenGivenIntegerOutsideOfRange() {
        final String massiveInteger = "2147483648"; // one bigger than Integer.MAX_VALUE
        assertThrows(IllegalArgumentException.class, () -> unit.convert(massiveInteger));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
    void canConvertNonNegativeIntegerStrings(final Integer integer) {
        final Integer convertedInteger = unit.convert(integer.toString());
        assertEquals(integer, convertedInteger);
    }
}