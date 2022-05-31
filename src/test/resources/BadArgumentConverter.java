import io.purplesheep.argeasy.annotations.Argument;
import io.purplesheep.argeasy.annotations.ArgumentConverter;
import io.purplesheep.argeasy.converters.IntegerConverter;

public class BadArgumentConverter {
    @Argument
    @ArgumentConverter(converter = IntegerConverter.class)
    private Boolean anArgument;

    @Argument
    @ArgumentConverter(converter = IntegerConverter.class)
    private Number anotherArgument;

    @Argument
    @ArgumentConverter(converter = IntegerConverter.class)
    private Integer exactArgument;

    @Argument
    @ArgumentConverter(converter = IntegerConverter.class)
    private int unboxedArgument;
}