package org.yunghegel.gdx.utils.console.directory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) @Retention(RetentionPolicy.RUNTIME) public @interface Directory {
    String name() default "dir";
    String path() default "root/";
    String description() default "";

}
