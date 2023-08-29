package org.yunghegel.gdx.utils.shaders;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;

public class GridShaderProvider extends BaseShaderProvider {

    private GridShader.GridConfig config;

    public GridShaderProvider() {
        config = new GridShader.GridConfig();
    }

    @Override
    protected Shader createShader(Renderable renderable) {
        return new GridShader(renderable, config);
    }
}
