package io.purplesheep.argeasy.converters;

public class BooleanConverter implements ArgumentConverter<Boolean> {
    @Override
    public Boolean convert(String value) {
        return Boolean.valueOf(value);
    }
}
