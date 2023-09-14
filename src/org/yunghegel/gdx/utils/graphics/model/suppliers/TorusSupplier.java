package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class TorusSupplier extends InstanceSupplier {

    private float majorRadius;
    private float minorRadius;
    private int majorSegments;
    private int minorSegments;
//   defaults .5f,0.1f,47,12

    public TorusSupplier(float majorRadius, float minorRadius, int majorSegments, int minorSegments, Color color){
        super(color);
        this.majorRadius = majorRadius;
        this.minorRadius = minorRadius;
        this.majorSegments = majorSegments;
        this.minorSegments = minorSegments;
    }

    public TorusSupplier(float majorRadius, float minorRadius, int majorSegments, int minorSegments){
        this(majorRadius, minorRadius, majorSegments, minorSegments, BuilderUtils.getRandomColor());
    }


    @Override
    public Model createModel() {
        modelBuilder.begin();
        b = modelBuilder.part("torus", primitiveType, attributes, mat);

        float stepU = TWO_PI / minorSegments;
        float stepV = TWO_PI / majorSegments;

        for (int i = 0; i < majorSegments; i++){
            float v = i * stepV;
            for (int j = 0; j < minorSegments; j++) {
                float u = j * stepU;
                float x = (float) ((majorRadius + minorRadius * Math.cos(u)) * Math.cos(v));
                float y = (float) (minorRadius * Math.sin(u));
                float z = (float) ((majorRadius + minorRadius * Math.cos(u)) * Math.sin(v));
                v(x, y, z);
            }
        }

        for (int i = 0; i < minorSegments; i++) {
            for (int j = 0; j < majorSegments; j++) {
                short i1, i2, i3, i4;
                i1 = toOneDimensionalIndex(i, j);
                i2 = toOneDimensionalIndex(i + 1, j);
                i3 = toOneDimensionalIndex(i + 1, j + 1);
                i4 = toOneDimensionalIndex(i, j + 1);
                b.triangle(i1, i2, i3);
                b.triangle(i1, i3, i4);
            }
        }

        return modelBuilder.end();
    }

    private short toOneDimensionalIndex(int i, int j) {
        return (short) ((short) (j % majorSegments) * minorSegments + (i % minorSegments));
    }
}
