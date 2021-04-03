package io.purplesheep.argeasy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The identifier the parser will look for, preceded with a single dash ('--'), when trying to populate
 * the target field. Short identifiers consist of a single character.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ShortIdentifier {
    char identifier();
}
