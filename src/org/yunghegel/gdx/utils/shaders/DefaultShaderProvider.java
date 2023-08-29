package org.yunghegel.gdx.utils.shaders;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;

public class DefaultShaderProvider extends BaseShaderProvider {

    private DefaultShader.DefaultConfig config;

    public DefaultShaderProvider(DefaultShader.DefaultConfig config) {
        this.config = config;
    }

    @Override
    protected Shader createShader(Renderable renderable) {
        return new DefaultShader(renderable, config.vertexPath, config.fragmentPath);
    }
}
