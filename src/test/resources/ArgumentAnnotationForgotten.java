import io.purplesheep.argeasy.annotations.Argument;
import io.purplesheep.argeasy.annotations.ArgumentConverter;
import io.purplesheep.argeasy.annotations.ArgumentDescription;
import io.purplesheep.argeasy.annotations.ArgumentValidator;
import io.purplesheep.argeasy.converters.DoubleConverter;
import io.purplesheep.argeasy.validators.ArgValidator;

public class ArgumentAnnotationForgotten {
    @ArgumentConverter(converter = DoubleConverter.class)
    private Double a1;

    @ArgumentValidator(validator = IntValidator.class)
    private Integer a2;

    @ArgumentDescription(description = "A description")
    private boolean a3;

    private static class IntValidator implements ArgValidator<Integer> {
        @Override
        public Boolean validate(final Integer value) {
            return 0 < value;
        }
    }
}