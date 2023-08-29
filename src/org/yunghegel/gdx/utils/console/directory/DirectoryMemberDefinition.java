package org.yunghegel.gdx.utils.console.directory;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.Method;

import java.util.Objects;


public class DirectoryMemberDefinition {

    Method method;
    String name;
    String description;
    Array<String> parameters;

    Class parent;

    public DirectoryMemberDefinition(Class parent, Method method, String name,  String description, Array<String> parameters) {
        Objects.requireNonNull(parent);
        Objects.requireNonNull(method);

        this.parent = parent;
        this.method = method;

        this.name = name;
        this.description = description;
        this.parameters = parameters;
    }

}
