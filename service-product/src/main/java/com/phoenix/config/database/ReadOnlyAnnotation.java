package com.phoenix.config.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//该注解应用在方法上
@Target({ElementType.METHOD, ElementType.TYPE})
//在运行时运行
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnlyAnnotation {
}