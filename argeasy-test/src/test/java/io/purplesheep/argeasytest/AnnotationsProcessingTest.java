package io.purplesheep.argeasytest;

import io.purplesheep.argeasy.annotations.ArgumentDescription;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnnotationsProcessingTest {

    @Test
    void aTest() {
        final TestObject test = new TestObject();
        assertTrue(true);
    }

    static class TestObject {
        @ArgumentDescription(description = "The size of something")
        private Integer size;
    }
}
