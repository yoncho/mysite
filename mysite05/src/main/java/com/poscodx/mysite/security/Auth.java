package com.poscodx.mysite.security;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({TYPE,METHOD})
public @interface Auth {
	public String Role() default "USER"; //value 는 auth("test")할때 test를 읽어옴..!!
	public boolean test() default false;
}
