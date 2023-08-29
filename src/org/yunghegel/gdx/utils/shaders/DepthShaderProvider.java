package org.yunghegel.gdx.utils.shaders;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;

public class DepthShaderProvider extends BaseShaderProvider {

    private DepthShader.DepthConfig config;


    public DepthShaderProvider(DepthShader.DepthConfig config) {
        this.config = config;
    }

    @Override
    protected Shader createShader(Renderable renderable) {
        return new DepthShader(renderable,this.config);
    }
}
