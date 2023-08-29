package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class CubeSupplier extends BoxSupplier{

    public CubeSupplier(float size, Color color) {
        super(size, size, size, color);
    }

    public CubeSupplier(float size) {
        this(size, BuilderUtils.getRandomColor());
    }
}
