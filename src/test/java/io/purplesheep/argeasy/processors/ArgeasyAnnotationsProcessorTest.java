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
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Collections;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class ArgeasyAnnotationsProcessorTest {

    @Test
    void failCompilationWithErrorWhenUtilityArgumentAnnotationNotAccompaniedByTheArgumentAnnotation() throws URISyntaxException {
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

    private Compilation compile(final JavaFileObject argAnnotationForgotten) throws URISyntaxException {
        final URI argeasyTestJarURI = ClassLoader.getSystemResource("argeasy-test-jar.jar").toURI();
        final File argeasyJar = Paths.get(argeasyTestJarURI).toFile();

        if (!argeasyJar.exists())
            throw new IllegalStateException("Could not find argeasy jar. " +
                    "It should have been generated as part of the maven generate " +
                    "test resources phase.");

        return javac()
                .withProcessors(new ArgeasyAnnotationsProcessor())
                .withClasspath(Collections.singletonList(argeasyJar))
                .compile(argAnnotationForgotten);
    }

    private String getMissingArgumentMessage(Class<?> argumentUtilityClass) {
        return String.format("%s annotation used, but could not find %s on the same field",
                argumentUtilityClass.getName(),
                Argument.class.getName());
    }
}