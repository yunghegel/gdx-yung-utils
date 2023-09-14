package org.yunghegel.gdx.utils.graphics.shaders.uniforms;

import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class Uniform {

    public final String name;

    public Uniform(String name) {
        this.name = name;
    }

    public abstract void set(Shader program);

}
