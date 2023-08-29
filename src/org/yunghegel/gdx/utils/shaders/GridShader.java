package org.yunghegel.gdx.utils.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class GridShader extends DefaultShader {

    private float cameraNear;
    private float cameraFar;
    public Color skyColor = new Color(0.2f, 0.2f, 0.2f, 1.0f);


    public static class GridConfig extends Config {

            public String vertexPath = "glsl/grid.vs.glsl";
            public String fragmentPath = "glsl/grid.fs.glsl";

            public GridConfig() {
                super(Gdx.files.internal("glsl/grid.vs.glsl").readString(), Gdx.files.internal("glsl/grid.fs.glsl").readString());
            }

    }





    public final int u_cameraFar;
    public final int u_cameraNear;
    public final int u_fogColor;

    public static final Uniform fogFarUniform = new Uniform("u_fogFar");
    public static final Uniform fogNearUniform = new Uniform("u_fogNear");
    public static final Uniform fogColorUniform = new Uniform("u_fogColor");

    public static final Setter cameraFarSetter = new GlobalSetter() {

        @Override
        public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, (shader.camera.far));
        }
    };

    public static final Setter cameraNearSetter = new GlobalSetter() {

        @Override
        public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, (shader.camera.near));
        }
    };

    public static final Setter fogColorSetter = new GlobalSetter() {

        @Override
        public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            GridShader gridShader = (GridShader)shader;

            shader.set(inputID, gridShader.skyColor);
        }
    };

    public GridShader(Renderable renderable,GridConfig config) {
        super(renderable,config, new ShaderProgram(Gdx.files.internal(config.vertexPath), Gdx.files.internal(config.fragmentPath)));


        u_cameraFar = register(fogFarUniform, cameraFarSetter);
        u_cameraNear = register(fogNearUniform, cameraNearSetter);
        u_fogColor = register(fogColorUniform, fogColorSetter);
    }



    @Override
    public void render(Renderable renderable) {

        super.render(renderable);
        set(u_fogColor, skyColor);
    }


}
