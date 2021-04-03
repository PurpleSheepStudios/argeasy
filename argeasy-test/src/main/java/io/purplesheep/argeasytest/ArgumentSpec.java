package io.purplesheep.argeasytest;

import io.purplesheep.argeasy.annotations.Argument;
import io.purplesheep.argeasy.annotations.LongIdentifier;

public class ArgumentSpec {

    @Argument(required = true)
    String name;

    @Argument
    @LongIdentifier(identifier = "size")
    Integer customSize;
}