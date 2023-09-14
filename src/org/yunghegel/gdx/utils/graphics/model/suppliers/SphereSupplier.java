package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.SphereShapeBuilder;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class SphereSupplier extends InstanceSupplier{

    float radius;
    int divisionsU;
    int divisionsV;

    public SphereSupplier(float radius, int divisionsU, int divisionsV, Color color) {
        super(color);
        this.radius = radius;
        this.divisionsU = divisionsU;
        this.divisionsV = divisionsV;
    }

    public SphereSupplier(float radius, int divisionsU, int divisionsV) {
        this(radius, divisionsU, divisionsV, BuilderUtils.getRandomColor());
    }

    @Override
    public Model createModel() {
        modelBuilder.begin();
        b = modelBuilder.part("sphere", primitiveType, attributes, mat);
        SphereShapeBuilder.build(b, radius, radius, radius, divisionsU, divisionsV);
        return modelBuilder.end();
    }
}
