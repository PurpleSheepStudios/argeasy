package io.purplesheep.argeasytest;

import io.purplesheep.argeasy.annotations.Argument;

public class ArgumentSpec {

    @Argument(required = true)
    String name;

    @Argument(longName = "size")
    Integer customSize;
}