package io.purplesheep.argeasy.validators;

public interface ArgumentValidator<T> {
    Boolean validate(final T value);
}
