package org.yunghegel.gdx.utils.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class DefaultShader extends com.badlogic.gdx.graphics.g3d.shaders.DefaultShader {



    public static class DefaultConfig extends Config {

        public String vertexPath = "glsl/default.vs.glsl";
        public String fragmentPath = "glsl/default.fs.glsl";

        public DefaultConfig() {
            super(Gdx.files.internal("glsl/default.vs.glsl").readString(), Gdx.files.internal("glsl/default.fs.glsl").readString());
        }

    }


    public DefaultShader(Renderable renderable, String vertexPath, String fragmentPath) {
        super(renderable, new DefaultConfig(), new ShaderProgram(Gdx.files.internal(vertexPath), Gdx.files.internal(fragmentPath)));

    }

    public DefaultShader(Renderable renderable, Config config) {
        super(renderable, config);
    }


}
