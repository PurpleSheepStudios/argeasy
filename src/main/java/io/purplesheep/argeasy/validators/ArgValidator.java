package io.purplesheep.argeasy.validators;

public interface ArgValidator<T> {
    Boolean validate(final T value);
}
