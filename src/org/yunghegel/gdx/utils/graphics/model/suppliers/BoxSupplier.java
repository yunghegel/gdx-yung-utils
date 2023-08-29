package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class BoxSupplier extends InstanceSupplier {

    private float width,depth,height;

    public BoxSupplier(float width, float depth, float height, Color color) {
        super(color);
        this.width = width;
        this.depth = depth;
        this.height = height;
    }

    public BoxSupplier(float width, float depth, float height) {
        this(width, depth, height, BuilderUtils.getRandomColor());
    }

    @Override
    public Model createModel() {
        modelBuilder.begin();
        b = modelBuilder.part("box", primitiveType, attributes, mat);
        BoxShapeBuilder.build(b, width, depth, height);
        return modelBuilder.end();
    }


}
