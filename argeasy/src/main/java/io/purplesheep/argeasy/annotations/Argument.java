package io.purplesheep.argeasy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to tell the parser that the given field as an argument.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Argument {

    boolean required() default false;
    char name() default Character.MIN_VALUE;
    String longName() default "";
    ArgumentType type() default ArgumentType.KEY_VALUE;

    enum ArgumentType {
        FLAG, KEY_VALUE
    }
}
