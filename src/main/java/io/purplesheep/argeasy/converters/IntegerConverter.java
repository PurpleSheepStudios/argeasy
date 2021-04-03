package io.purplesheep.argeasy.converters;

public class IntegerConverter implements ArgumentConverter<Integer> {
    @Override
    public Integer convert(final String value) {
        return Integer.valueOf(value);
    }
}
