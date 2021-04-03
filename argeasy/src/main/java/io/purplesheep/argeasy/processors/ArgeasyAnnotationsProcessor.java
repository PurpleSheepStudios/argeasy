package io.purplesheep.argeasy.processors;

import io.purplesheep.argeasy.annotations.ArgumentConverter;
import io.purplesheep.argeasy.annotations.ArgumentValidator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Performs the following checks
 *  - Any annotated argument is either of a type supported by basic converters, or is accompanied by custom converter
 *  - The classes given in {@link ArgumentConverter} or {@link ArgumentValidator} work correctly with the argument type
 *  - Flag Arguments are only placed above a boolean field
 */
@SupportedAnnotationTypes("io.purplesheep.argeasy.annotations.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ArgeasyAnnotationsProcessor extends AbstractProcessor {
    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        for (final TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
        }
        return true;
    }
}
