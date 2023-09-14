package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class WedgeSupplier extends InstanceSupplier{

    private float radius, height, angle;

    public WedgeSupplier(Color color, float radius) {
        super(color);
        this.radius = radius;
        this.height = height;
        this.angle = angle;
    }

    public WedgeSupplier(float radius) {
        this(BuilderUtils.getRandomColor(), radius);
    }

    @Override
    public Model createModel() {
        modelBuilder.begin();
        b = modelBuilder.part("wedge", primitiveType, attributes, mat);
        mat.set(new IntAttribute(IntAttribute.CullFace, 0));

        float x = (float) (radius * Math.cos(angle));
        float z = (float) (radius * Math.sin(angle));

        v(-radius,radius,radius);
        v(radius,radius,radius);
        v(radius,radius,-radius);
        v(-radius,radius,-radius);

        v(radius,-radius,-radius);
        v(-radius,-radius,-radius);

        short i1, i2, i3, i4;

//        b.rect( i1 = 0, i2 = 1, i3 = 2, i4 = 3);

        b.triangle( i1 = 0, i2 = 1, i3 = 2);
        b.triangle( i1 = 0, i2 = 2, i3 = 3);

        b.index( i1 = 0,i2=3,i3=5);
        b.index( i1 = 4,i2=2,i3=1);

//        b.rect( i1 = 1,i2=0,i3=5,i4=4);

        b.triangle( i1 = 1,i2=0,i3=5);
        b.triangle( i1 = 1,i2=5,i3=4);

//        b.rect( i1 = 4,i2=5,i3=3,i4=2);

        b.triangle( i1 = 4,i2=5,i3=3);
        b.triangle( i1 = 4,i2=3,i3=2);


        return modelBuilder.end();

    }
}
