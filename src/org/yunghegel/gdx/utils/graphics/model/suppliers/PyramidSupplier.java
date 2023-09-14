package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class PyramidSupplier extends InstanceSupplier{

    float size,height;

    public PyramidSupplier(float size, float height, Color color) {
        super(color);
        this.size = size;
        this.height = height;

    }

    public PyramidSupplier(float size, float height) {
        this(size, height, BuilderUtils.getRandomColor());
    }

    @Override
    public Model createModel() {
        modelBuilder.begin();
        b = modelBuilder.part("square_pyramid", primitiveType, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.ColorUnpacked, mat);
        v(-size,0,-size);
        v(-size,0,size);
        v(size,0,size);
        v(size,0,-size);
        v(0,height,0);


        b.triangle( i1=0, i2=1, i3=2);
        b.triangle( i1=0, i2=2, i3=3);
        b.triangle( i1=0, i2=1, i3=4);
        b.triangle( i1=1, i2=2, i3=4);
        b.triangle( i1=2, i2=3, i3=4);
        b.triangle( i1=3, i2=0, i3=4);

        b.triangle( i1=1, i2=0, i3=2);
        b.triangle( i1=2, i2=0, i3=3);


        return modelBuilder.end();
    }
}
