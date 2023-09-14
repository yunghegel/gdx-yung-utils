package org.yunghegel.gdx.utils.console.directory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD}) @Retention(RetentionPolicy.RUNTIME) public @interface DirectoryMember {
    String description() default "";
    String name() default "";
    String[] paramNames() default {};

    /**
     * Put these in the same order as your actual function parameters.
     *
     * @return An array of parameter descriptions.
     */
    String[] paramDescriptions() default {};
}
