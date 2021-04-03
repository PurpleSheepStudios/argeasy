package io.purplesheep.argeasy.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BooleanConverterTest {

    private BooleanConverter unit;

    @BeforeEach
    void setUp() {
        unit = new BooleanConverter();
    }

    @Test
    void returnsFalseWhenGivenNullValue() {
        assertFalse(unit.convert(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"false", "FALSE", "yes", " "})
    void returnsFalseWhenGivenAnythingOtherThanTrue(final String notTrueString) {
        assertFalse(unit.convert(notTrueString));
    }

    @ParameterizedTest
    @ValueSource(strings = {"true", "TrUe", "TRUE"})
    void returnsTrueWhenGivenTrueString(final String trueString) {
        assertTrue(unit.convert(trueString));
    }
}