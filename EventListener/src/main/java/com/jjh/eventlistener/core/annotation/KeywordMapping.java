package com.jjh.eventlistener.core.annotation;

import static com.jjh.eventlistener.core.type.FindKeywordMethod.startWith;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.jjh.eventlistener.core.type.FindKeywordMethod;



@Retention(RUNTIME)
@Target(METHOD)
public @interface KeywordMapping {
    
    String value();
    String[] msgMapKeys();
    String delimiter() default " ";
    FindKeywordMethod method() default startWith;
}

