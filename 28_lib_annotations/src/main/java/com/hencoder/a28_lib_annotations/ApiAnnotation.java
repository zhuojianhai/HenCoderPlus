package com.hencoder.a28_lib_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface ApiAnnotation {
    String author() default "zhuojianhai";
    String date();
    int version() default 1;
}
