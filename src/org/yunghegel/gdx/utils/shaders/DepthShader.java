package org.yunghegel.gdx.utils.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class DepthShader extends DefaultShader {

    private final String vertexShader;
    private final String fragmentShader;

    public static class DepthConfig extends Config {

        public String vertexPath = "glsl/depth.vs.glsl";
        public String fragmentPath = "glsl/depth.fs.glsl";


        public DepthConfig() {
            super(Gdx.files.internal("glsl/depth.vs.glsl").readString(), Gdx.files.internal("glsl/depth.fs.glsl").readString());
        }

    }

    public static class DepthInputs {
        public final static Uniform projViewTrans = new Uniform("u_projViewTrans");
        public final static Uniform bones = new Uniform("u_bones");
        public final static Uniform alphaTest = new Uniform("u_alphaTest");
    }

    public DepthShader(Renderable renderable, DepthConfig config) {
        super(renderable,new DepthConfig(), new ShaderProgram(Gdx.files.internal(config.vertexPath), Gdx.files.internal(config.fragmentPath)));
        vertexShader = Gdx.files.internal(config.vertexPath).readString();
        fragmentShader = Gdx.files.internal(config.fragmentPath).readString();
    }

    @Override
    public int compareTo(com.badlogic.gdx.graphics.g3d.Shader other) {
        if (other == null) return -1;
        return 0;
    }

    @Override
    public boolean canRender(Renderable instance) {
        return true;
    }
}
