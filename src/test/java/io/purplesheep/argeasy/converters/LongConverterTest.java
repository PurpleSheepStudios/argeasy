package io.purplesheep.argeasy.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LongConverterTest {
    private LongConverter unit;

    @BeforeEach
    void setUp() {
        unit = new LongConverter();
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
    @ValueSource(strings = {"jim", "10.0", "103502,00"})
    void throwsIllegalArgumentExceptionWhenGivenNonLongValue(final String value) {
        assertThrows(IllegalArgumentException.class, () -> unit.convert(value));
    }

    @Test
    void throwsIllegalArgumentExceptionWhenGivenLongOutsideOfRange() {
        final String massiveLong = "9223372036854775808"; // one bigger than Long.MAX_VALUE
        assertThrows(IllegalArgumentException.class, () -> unit.convert(massiveLong));
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MIN_VALUE, 0, Long.MAX_VALUE})
    void canConvertNonNegativeIntegerStrings(final Long actualLong) {
        final Long convertedLong = unit.convert(actualLong.toString());
        assertEquals(actualLong, convertedLong);
    }
}