package io.purplesheep.argeasy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The identifier the parser will look for, preceded with a double dash ('--'), when trying to populate
 * the target field. Long identifiers can consist of any number of alpha-numeric characters separated by single dashes.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LongIdentifier {
    String identifier();
}
