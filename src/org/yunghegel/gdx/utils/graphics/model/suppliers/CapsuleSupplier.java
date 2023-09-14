package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.CapsuleShapeBuilder;

public class CapsuleSupplier extends InstanceSupplier{

    float radius;
    float height;
    int divisions;

    public CapsuleSupplier(float radius, float height, int divisions, Color color) {
        super(color);
        this.radius = radius;
        this.height = height;
        this.divisions = divisions;
    }

    @Override
    public Model createModel() {
        modelBuilder.begin();
        b = modelBuilder.part("capsule", primitiveType, attributes, mat);
        CapsuleShapeBuilder.build(b, radius, height, divisions);
        return modelBuilder.end();
    }
}
