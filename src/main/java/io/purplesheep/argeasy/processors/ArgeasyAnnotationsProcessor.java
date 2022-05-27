package io.purplesheep.argeasy.processors;

import com.google.auto.service.AutoService;
import io.purplesheep.argeasy.annotations.Argument;
import io.purplesheep.argeasy.annotations.ArgumentConverter;
import io.purplesheep.argeasy.annotations.ArgumentValidator;
import io.purplesheep.argeasy.converters.ArgConverter;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.List;
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

    public static final String NO_ARGUMENT_ANNOTATION_ERROR = "No argument annotation found for this field, but other argument utility annotations exist";

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        final Messager messager = processingEnv.getMessager();

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

                for (AnnotationMirror annotationMirror : annotationMirrors) {
                    final TypeMirror annotationType = annotationMirror.getAnnotationType().asElement().asType();

                    // make sure that arguments marked as type flag or assignable to a boolean
                    if (isSameType(annotationType, getTypeMirror(Argument.class))) {
                        final Argument argument = annotatedElement.getAnnotation(Argument.class);
                        final TypeMirror booleanTypeMirror = getTypeMirror(Boolean.class);
                        boolean elementIsABoolean = processingEnv.getTypeUtils().isAssignable(annotatedElement.asType(), booleanTypeMirror);
                        if (argument.type().equals(FLAG) && !elementIsABoolean) {
                            final String message = "Field is marked as a flag argument but is not a boolean";
                            printAnnotationError(message, annotatedElement);
                        }
                    }

                    // check that the type of argument converter is assignable to the type of the variable
                    // error if the converter does not directly implement the ArgConverter interface
                    if (isSameType(annotationType, getTypeMirror(ArgConverter.class))) {
                        final ArgumentConverter argumentConverter = annotatedElement.getAnnotation(ArgumentConverter.class);

//                        TypeElement typeElement = processingEnv.getElementUtils().getTypeElement(argumentConverter.converter().getCanonicalName());
//                        annotationMirror.getElementValues().get(typeElement)
                    }

                }

            }
        }
        return false;
    }

    private boolean isSameType(TypeMirror type1, TypeMirror type2) {
        return processingEnv.getTypeUtils().isSameType(type1, type2);
    }

    private Optional<? extends AnnotationMirror> findAnnotationMirror(final List<? extends AnnotationMirror> annotationMirrors, final Class<?> clazz) {
        return annotationMirrors.stream()
                .filter(annotationMirror -> processingEnv.getTypeUtils().isSameType(getTypeMirror(clazz), annotationMirror.getAnnotationType()))
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
