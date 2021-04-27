package io.purplesheep.argeasy.converters;

public interface ArgConverter<T> {
    T convert(final String value) throws IllegalArgumentException;
}
