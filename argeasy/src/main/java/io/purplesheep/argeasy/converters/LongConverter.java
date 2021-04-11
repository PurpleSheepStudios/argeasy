package io.purplesheep.argeasy.converters;

public class LongConverter implements ArgConverter<Long> {
    @Override
    public Long convert(String value) {
        return Long.valueOf(value);
    }
}
