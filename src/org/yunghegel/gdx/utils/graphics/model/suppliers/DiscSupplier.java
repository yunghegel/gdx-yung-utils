package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;
import org.yunghegel.gdx.utils.graphics.model.ModelSupplier;

public class DiscSupplier extends InstanceSupplier {

    private int rotationSegments;
    private int discSegments;
    private float outerRadius;
    private float innerRadius;

    public DiscSupplier(float outerRadius, float innerRadius, int rotationSegments, int discSegments, Color color){
        super(color);
        this.outerRadius = outerRadius;
        this.innerRadius = innerRadius;
        this.rotationSegments = rotationSegments;
        this.discSegments = discSegments;
    }

    public DiscSupplier(float outerRadius, float innerRadius, int rotationSegments, int discSegments){
        this(outerRadius, innerRadius, rotationSegments, discSegments, BuilderUtils.getRandomColor());
    }

    @Override
    public Model createModel() {
        modelBuilder.begin();
        addMaterialAttribute(new IntAttribute(IntAttribute.CullFace, 0));
        b = modelBuilder.part("disc", primitiveType, VertexAttributes.Usage.Position, mat);
        if (innerRadius > 0) {
            createDisc(discSegments, innerRadius);
        } else {
            createDisc(discSegments - 1, outerRadius / discSegments);
            createTriangleFan();
        }
        return modelBuilder.end();
    }
    private void addFace(int i, int j, int segments) {
        if (i >= segments)
            return;
        int idx0 = toOneDimensionalIndex(i, j);
        int idx1 = toOneDimensionalIndex(i + 1, j);
        int idx2 = toOneDimensionalIndex(i + 1, j + 1);
        int idx3 = toOneDimensionalIndex(i, j + 1);
        b.triangle((short) idx0, (short) idx2, (short) idx1);
        b.triangle((short) idx0, (short) idx3, (short) idx2);

    }

    private int toOneDimensionalIndex(int i, int j) {
        return toOneDimensionalIndex(i, j % rotationSegments, rotationSegments);
    }

    private void createDisc(int segments, float startRadius) {
        float angle = 0;
        float radius = (outerRadius - innerRadius) / (float) discSegments;


        for (int i = 0; i <= segments; i++) {
            for (int j = 0; j < rotationSegments; j++) {
                float x = (float) ((startRadius + (i * radius)) * Math.cos(angle));
                float y = 0;
                float z = (float) ((startRadius + (i * radius)) * Math.sin(angle));
                v(x, y, z);
                addFace(i, j, segments);
                angle += InstanceSupplier.TWO_PI / (float) rotationSegments;
            }
            angle = 0;
        }
    }

    private void createTriangleFan() {
        int idx = b.lastIndex();
       v(0, 0, 0);
        for (int i = 0; i < rotationSegments; i++) {
            b.triangle((short) idx, (short) i, (short) ((i + 1) % rotationSegments));

        }
    }

}
