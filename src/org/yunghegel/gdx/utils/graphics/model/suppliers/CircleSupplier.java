package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class CircleSupplier extends InstanceSupplier{

    int vertices;
    float radius,centerY;

    public CircleSupplier(Color color, int vertices, float radius, float centerY) {
        super(color);
        this.vertices = vertices;
        this.radius = radius;
        this.centerY = centerY;
    }

    public CircleSupplier(int vertices, float radius, float centerY) {
        this(BuilderUtils.getRandomColor(), vertices, radius, centerY);
    }

    @Override
    public Model createModel() {
        modelBuilder.begin();
        b = modelBuilder.part("circle", primitiveType, attributes, mat);
        float angle = 0;
        float step = TWO_PI / (float) vertices;
        for (int i = 0; i < vertices; i++) {
            float x = (float) (Math.cos(angle) * radius);
            float z = (float) (Math.sin(angle) * radius);
            v(x, centerY, z);
            angle += step;
        }


       v(0, centerY, 0);
        for (short i = 0; i < vertices; i++) {
            short i1 = (short) (i %vertices);
            short i2 = (short) ((short) (i+1)%vertices);
            b.triangle(i2, i1, (short) vertices);
        }
        return modelBuilder.end();
    }
}
