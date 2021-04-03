package io.purplesheep.argeasy.converters;

public class DoubleConverter implements ArgumentConverter<Double> {
    @Override
    public Double convert(String value) {
        if (value == null) throw new IllegalArgumentException("Can't convert 'null' to double");
        return Double.valueOf(value);
    }
}
