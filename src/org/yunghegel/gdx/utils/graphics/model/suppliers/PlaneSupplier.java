package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;

public class PlaneSupplier extends InstanceSupplier {

   public float scale;

    public PlaneSupplier(float scale, Color color) {
        super(color);
        this.scale = scale;
    }

    @Override
    public Model createModel() {
        modelBuilder.begin();
        b = modelBuilder.part("plane", primitiveType, attributes, mat);
        b.rect(
                -scale, -scale, 0,
                -scale, scale, 0,
                scale, scale, 0,
                scale, -scale, 0,
                0, 0, -1
        );
        return modelBuilder.end();
    }


}
