package io.purplesheep.argeasy.processors;

import com.google.auto.service.AutoService;
import io.purplesheep.argeasy.annotations.Argument;
import io.purplesheep.argeasy.annotations.ArgumentConverter;
import io.purplesheep.argeasy.annotations.ArgumentValidator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;

/**
 * Performs the following checks
 *  - Any annotated argument is either of a type supported by basic converters, or is accompanied by custom converter
 *  - The classes given in {@link ArgumentConverter} or {@link ArgumentValidator} work correctly with the argument type
 *  - Flag Arguments are only placed above a boolean field
 *  - Any annotation that isn't {@link Argument} must be accompanied by that annotation
 */
@SupportedAnnotationTypes("io.purplesheep.argeasy.annotations.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class ArgeasyAnnotationsProcessor extends AbstractProcessor {

    public static final String NO_ARGUMENT_ANNOTATION_ERROR = "No argument annotation found for this field, but other argument utility annotations exist";

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        final Messager messager = processingEnv.getMessager();

        for (final TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (final Element annotatedElement : annotatedElements) {
                final List<? extends AnnotationMirror> annotationMirrors = annotatedElement.getAnnotationMirrors();

                final Argument argumentInfo = annotatedElement.getAnnotation(Argument.class);

//                 check element has the Annotation annotation
                if (argumentInfo == null) {
                    annotationMirrors.forEach(annotationMirror -> {
                        final String message = String.format("%s annotation used, but could not find %s on the same field",
                                annotationMirror.getAnnotationType(), Argument.class.getName());
                        printAnnotationError(message, annotatedElement, annotationMirror);
                    });
                    ;
                }
//
//                 check that if the argument type is of type FLAG, that the field is a boolean
//                annotatedElement.asType().getKind().
//                if (argumentInfo.type().equals(ArgumentType.FLAG)) {
//
            }
        }
        return false;
    }

    private void printAnnotationError(final String message, final Element element, final AnnotationMirror annotationMirror) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element, annotationMirror);
    }
}
