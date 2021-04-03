package io.purplesheep.argeasy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define a validator to use on an argument once it's been converted to it's target type.
 * Arguments that do not comply to their validation will cause an exception at the point of parsing.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ArgumentValidator {
    Class<? extends io.purplesheep.argeasy.validators.ArgumentValidator<?>> validator();
}
