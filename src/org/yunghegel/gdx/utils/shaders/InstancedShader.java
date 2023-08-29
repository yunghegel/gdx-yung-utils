package org.yunghegel.gdx.utils.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class InstancedShader extends BaseShader {

    private Renderable renderable;
    private long startTime, updateTime, renderTime;


    public InstancedShader() {
        super();
    }

    @Override
    public void init() {
//        ShaderProgram.prependVertexCode = "#version 130\n";
//        ShaderProgram.prependFragmentCode = "#version 130\n";
        program = new ShaderProgram(Gdx.files.internal("instanced.vert"),
                Gdx.files.internal("instanced.frag"));
        if (!program.isCompiled()) {
            throw new GdxRuntimeException("Shader compile error: " + program.getLog());
        }
    }

    @Override
    public int compareTo(Shader other) {
        return 0;
    }

    @Override
    public boolean canRender(Renderable instance) {
        return false;
    }

    @Override
    public void begin(Camera camera, RenderContext context) {
        program.bind();
        program.setUniformMatrix("u_projViewTrans", camera.combined);
        program.setUniformi("u_texture", 0);
        context.setDepthTest(GL30.GL_LEQUAL);    }
}
