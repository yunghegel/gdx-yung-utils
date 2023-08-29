package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class ArcSupplier extends InstanceSupplier {

    private float startAngle, endAngle, radius;
    private int vertices;

    public ArcSupplier(float startAngle, float endAngle, float radius, int vertices, Color color) {
        super(color);
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.radius = radius;
        this.vertices = vertices;
    }

    public ArcSupplier(float startAngle, float endAngle, float radius, int vertices) {
        this(startAngle, endAngle, radius, vertices, BuilderUtils.getRandomColor());
    }


    @Override
    public Model createModel() {
        modelBuilder.begin();
        b = modelBuilder.part("arc", primitiveType, attributes, mat);
        mat.set(new IntAttribute(IntAttribute.CullFace, 0));

        float angleBetweenPoints = calculateAngleBetweenPoints();
        for (int i = 0; i < vertices; i++) {
            float currentAngle = angleBetweenPoints * i;
            float x = (float) (radius * Math.cos(currentAngle));
            float z = (float) (radius * Math.sin(currentAngle));

            float x2 = (float) (radius * Math.cos(currentAngle + angleBetweenPoints));
            float z2 = (float) (radius * Math.sin(currentAngle + angleBetweenPoints));

            i1 = v(0, 0, 0);
            i2=v(x, 0, z);
            i3= v(x2, 0, z2);

            b.triangle(i1, i3, i2);

        }


        return modelBuilder.end();
    }
    private float calculateAngleBetweenPoints() {
        return startAngle + ((endAngle - startAngle) / ((float) vertices - 1));
    }
}
