package io.purplesheep.argeasy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to document the meaning and usage of an argument. It will be used when generating the default
 * help section for the program.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ArgumentDescription {
    String description();
}
