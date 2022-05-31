package io.purplesheep.argeasy.processors;

import com.google.auto.service.AutoService;
import io.purplesheep.argeasy.annotations.Argument;
import io.purplesheep.argeasy.annotations.ArgumentConverter;
import io.purplesheep.argeasy.annotations.ArgumentValidator;
import io.purplesheep.argeasy.converters.ArgConverter;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static io.purplesheep.argeasy.annotations.Argument.ArgumentType.FLAG;

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

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        for (final TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            for (final Element annotatedElement : annotatedElements) {
                final List<? extends AnnotationMirror> annotationMirrors = annotatedElement.getAnnotationMirrors();

                // check element has the @Annotation annotation
                if (annotatedElement.getAnnotation(Argument.class) == null) {
                    annotationMirrors.forEach(annotationMirror -> {
                        final String message = String.format("%s annotation used, but could not find %s on the same field",
                                annotationMirror.getAnnotationType(), Argument.class.getName());
                        printAnnotationError(message, annotatedElement, annotationMirror);
                    });
                }

                if (isSameType(annotation.asType(), getTypeMirror(Argument.class))) {
                    checkThatArgumentAnnotatesABooleanIfItHasAFlagType(annotatedElement);
                }

                if (isSameType(annotation.asType(), getTypeMirror(ArgumentConverter.class))) {
                    checkThatArgumentConverterConvertsToTheCorrectType(annotatedElement);
                }
            }
        }
        return false;
    }

    private void checkThatArgumentAnnotatesABooleanIfItHasAFlagType(Element annotatedElement) {
        final Argument argument = annotatedElement.getAnnotation(Argument.class);
        final TypeMirror booleanTypeMirror = getTypeMirror(Boolean.class);
        boolean elementIsABoolean = processingEnv.getTypeUtils().isAssignable(annotatedElement.asType(), booleanTypeMirror);
        if (argument.type().equals(FLAG) && !elementIsABoolean) {
            final String message = "Field is marked as a flag argument but is not a boolean";
            printAnnotationError(message, annotatedElement);
        }
    }

    /**
     * Check that the type of argument converter is assignable to the type of the variable
     * error if the converter does not directly implement the ArgConverter interface
     * @param annotatedElement an element that definitely is annotated with the {@link ArgumentConverter} annotation
     */
    private void checkThatArgumentConverterConvertsToTheCorrectType(Element annotatedElement) {
        // check that the type of argument converter is assignable to the type of the variable
        // error if the converter does not directly implement the ArgConverter interface
        final Optional<? extends AnnotationMirror> argConverterMirror = getAnnotationMirror(annotatedElement, ArgumentConverter.class);
        final Optional<? extends AnnotationValue> converter = argConverterMirror
                .flatMap(m -> getAnnotationValue(m, "converter"));

        if (converter.isPresent()) {
            final TypeMirror converterType = (TypeMirror) converter.get().getValue();

            final TypeMirror converterInterface = processingEnv.getTypeUtils().directSupertypes(converterType).stream()
                    .filter(superTypes -> ArgConverter.class.getCanonicalName().equals(processingEnv.getTypeUtils().erasure(superTypes).toString()))
                    .findAny()
                    .orElseThrow(() -> new IllegalStateException("This should not be possible as converters need to extend this interface"));

            final DeclaredType declaredType = (DeclaredType) converterInterface;
            final TypeMirror converterInnerType = declaredType.getTypeArguments().get(0);

            if (!processingEnv.getTypeUtils().isAssignable(converterInnerType, annotatedElement.asType())) {
                final String errorMessage = "Argument converter given for field %s returns %s rather than %s";
                printAnnotationError(String.format(errorMessage, annotatedElement.getSimpleName(), converterInnerType, annotatedElement.asType()), annotatedElement, argConverterMirror.get());
            }
        }
    }

    private boolean isSameType(final TypeMirror type1, final TypeMirror type2) {
        return processingEnv.getTypeUtils().isSameType(type1, type2);
    }

    private static Optional<? extends AnnotationMirror> getAnnotationMirror(final Element element, final Class<?> clazz) {
        return element.getAnnotationMirrors().stream()
                .filter(m -> m.getAnnotationType().toString().equals(clazz.getName()))
                .findAny();
    }

    private static Optional<? extends AnnotationValue> getAnnotationValue(AnnotationMirror annotationMirror, String key) {
        return annotationMirror.getElementValues().entrySet().stream()
                .filter(entry -> entry.getKey().getSimpleName().toString().equals(key))
                .map(Map.Entry::getValue)
                .findAny();
    }

    private TypeMirror getTypeMirror(final Class<?> clazz) {
        return processingEnv.getElementUtils().getTypeElement(clazz.getCanonicalName()).asType();
    }

    private void printAnnotationError(final String message, final Element element, final AnnotationMirror annotationMirror) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element, annotationMirror);
    }

    private void printAnnotationError(final String message, final Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
