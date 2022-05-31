package io.purplesheep.argeasy.processors;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import io.purplesheep.argeasy.annotations.Argument;
import io.purplesheep.argeasy.annotations.ArgumentConverter;
import io.purplesheep.argeasy.annotations.ArgumentDescription;
import io.purplesheep.argeasy.annotations.ArgumentValidator;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static org.junit.jupiter.api.Assertions.fail;

class ArgeasyAnnotationsProcessorTest {

    @Test
    void failsCompilationWithErrorWhenUtilityArgumentAnnotationNotAccompaniedByTheArgumentAnnotation() throws URISyntaxException {
        JavaFileObject argAnnotationForgotten = JavaFileObjects.forResource("ArgumentAnnotationForgotten.java");

        Compilation compilation = compile(argAnnotationForgotten);

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(3);

        assertThat(compilation)
                .hadErrorContaining(getMissingArgumentMessage(ArgumentConverter.class))
                .inFile(argAnnotationForgotten)
                .onLine(9)
                .atColumn(5);

        assertThat(compilation)
                .hadErrorContaining(getMissingArgumentMessage(ArgumentValidator.class))
                .inFile(argAnnotationForgotten)
                .onLine(12)
                .atColumn(5);

        assertThat(compilation)
                .hadErrorContaining(getMissingArgumentMessage(ArgumentDescription.class))
                .inFile(argAnnotationForgotten)
                .onLine(15)
                .atColumn(5);
    }

    @Test
    void failsCompilationWithErrorWhenFlagArgumentAnnotationNotOnABooleanType() throws URISyntaxException {
        JavaFileObject flagOnBoolean = JavaFileObjects.forResource("FlagAnnotationOnBoolean.java");

        Compilation compilation = compile(flagOnBoolean);

        assertThat(compilation).failed();
        assertThat(compilation)
                .hadErrorContaining("Field is marked as a flag argument but is not a boolean")
                .inFile(flagOnBoolean)
                .onLine(11)
                .atColumn(20);
    }

    @Test
    void errorWhenArgumentConverterDoesNotConvertToCorrectType() throws URISyntaxException {
        JavaFileObject badArgumentConverter = JavaFileObjects.forResource("BadArgumentConverter.java");

        Compilation compilation = compile(badArgumentConverter);

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);

        assertThat(compilation)
                .hadErrorContaining("Argument converter given for field anArgument returns java.lang.Integer rather than java.lang.Boolean")
                .inFile(badArgumentConverter)
                .onLine(7)
                .atColumn(5);
    }

    private Compilation compile(final JavaFileObject javaFileObject) throws URISyntaxException {
        final URL argeasyTestJarURL = ClassLoader.getSystemResource("argeasy-test-jar.jar");

        if (argeasyTestJarURL == null)
            fail("Could not find argeasy jar to add to classpath. " +
                    "It should have been generated as part of the maven generate " +
                    "test resources phase.");

        final File argeasyJar = Paths.get(argeasyTestJarURL.toURI()).toFile();

        return javac()
                .withProcessors(new ArgeasyAnnotationsProcessor())
                .withClasspath(Collections.singletonList(argeasyJar))
                .compile(javaFileObject);
    }

    private String getMissingArgumentMessage(Class<?> argumentUtilityClass) {
        return String.format("%s annotation used, but could not find %s on the same field",
                argumentUtilityClass.getName(),
                Argument.class.getName());
    }
}