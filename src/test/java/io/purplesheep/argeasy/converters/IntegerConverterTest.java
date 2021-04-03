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
    @ValueSource(strings = {"bob", "10.0", "2345470O34", "10000,00"})
    void throwsIllegalArgumentExceptionWhenGivenNonIntegerValue(final String value) {
        assertThrows(IllegalArgumentException.class, () -> unit.convert(value));
    }

    @Test
    void throwsIllegalArgumentExceptionWhenGivenIntegerOutsideOfRange() {
        final String massiveInteger = "2147483648"; // one bigger than Integer.MAX_VALUE
        assertThrows(IllegalArgumentException.class, () -> unit.convert(massiveInteger));
    }

    @ParameterizedTest
    @ValueSource(ints = {-256, 0, 10, 999999999})
    void canConvertNonNegativeIntegerStrings(final Integer integer) {
        final Integer convertedInteger = unit.convert(integer.toString());
        assertEquals(integer, convertedInteger);
    }


}