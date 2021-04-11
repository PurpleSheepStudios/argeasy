package io.purplesheep.argeasy.parser;

import io.purplesheep.argeasy.annotations.Argument;

/**
 * A class that given another class with fields annotated with {@link Argument} and and
 * array of string arguments, returns a new instance with the fields populated by parsing
 * the given arguments.
 */
public class ArgumentParser {

    public static <T> T parse(final String[] args, Class<T> clazz) {
        return null;
    }

}
