import io.purplesheep.argeasy.annotations.Argument;
import io.purplesheep.argeasy.annotations.ArgumentConverter;
import io.purplesheep.argeasy.annotations.ArgumentDescription;
import io.purplesheep.argeasy.annotations.ArgumentValidator;
import io.purplesheep.argeasy.converters.DoubleConverter;
import io.purplesheep.argeasy.validators.ArgValidator;

public class FlagAnnotationOnBoolean {

    @Argument(type = Argument.ArgumentType.FLAG)
    private Object a1;

    @Argument(type = Argument.ArgumentType.FLAG)
    private Boolean a2;

    @Argument(type = Argument.ArgumentType.FLAG)
    private boolean a3;
}