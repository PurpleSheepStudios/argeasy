package io.purplesheep.argeasy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this if you want to convert any string argument value to a custom type of your choice.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ArgumentConverter {
    Class<? extends io.purplesheep.argeasy.converters.ArgumentConverter<?>> converter();
}
