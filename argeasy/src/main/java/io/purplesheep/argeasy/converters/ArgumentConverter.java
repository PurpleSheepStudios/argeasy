package io.purplesheep.argeasy.converters;

public interface ArgumentConverter<T> {
    T convert(final String value) throws IllegalArgumentException;
}
