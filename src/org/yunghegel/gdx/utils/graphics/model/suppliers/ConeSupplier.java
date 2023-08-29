package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.ConeShapeBuilder;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class ConeSupplier extends InstanceSupplier{

    float width,height,depth;
    int divisions;

    public ConeSupplier(float width, float height, float depth, int divisions, Color color) {
        super(color);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.divisions = divisions;
    }

    public ConeSupplier(float width, float height, float depth, int divisions) {
        this(width, height, depth, divisions, BuilderUtils.getRandomColor());
    }

    @Override
    public Model createModel() {
        modelBuilder.begin();
        b = modelBuilder.part("cone", primitiveType, attributes, mat);
        ConeShapeBuilder.build(b, width, height, depth, divisions);
        return modelBuilder.end();
    }

}
