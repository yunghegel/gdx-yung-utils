package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.CylinderShapeBuilder;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class CylinderSupplier extends InstanceSupplier{

    private float width,height,depth;
    private int divisions;

    public CylinderSupplier(float width, float height, float depth, int divisions, Color color) {
        super(color);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.divisions = divisions;
    }

    public CylinderSupplier(float width, float height, float depth, int divisions) {
        this(width, height, depth, divisions, BuilderUtils.getRandomColor());
    }

    @Override
    public Model createModel() {
        modelBuilder.begin();
        b = modelBuilder.part("cylinder", primitiveType, attributes, mat);
        CylinderShapeBuilder.build(b, width, height, depth, divisions);
        return modelBuilder.end();
    }

}
