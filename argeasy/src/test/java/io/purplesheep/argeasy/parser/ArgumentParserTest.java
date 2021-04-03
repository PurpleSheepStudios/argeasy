package io.purplesheep.argeasy.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ArgumentParserTest {

    @Test
    void returnsNewInstanceOfGivenClassWhenArgumentsAreNull() {
        final TestArguments result = ArgumentParser.parse(null, TestArguments.class);
        assertNotNull(result);
        assertNull(result.getName());
        assertNull(result.getCustomSize());
        assertNull(result.getReady());
        assertNull(result.getQuiet());
    }
}