package com.mamags.mamag.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by samer on 10/10/2017.
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoGson {
    // A reference to the AutoValue-generated class (e.g. AutoValue_MyClass). This is
    // necessary to handle obfuscation of the class names.
    public Class autoValueClass();
}