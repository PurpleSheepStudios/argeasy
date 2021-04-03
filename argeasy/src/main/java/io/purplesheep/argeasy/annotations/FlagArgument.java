package io.purplesheep.argeasy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to indicate an argument that takes no corresponding value. This can only be used with a boolean type field.
 * If the flag appears in the command line arguments, the boolean field will be set to true, otherwise it's false.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FlagArgument {
    char name() default Character.MIN_VALUE;
    String longName() default "";
}
