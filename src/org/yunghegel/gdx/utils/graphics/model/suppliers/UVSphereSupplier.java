package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class UVSphereSupplier extends InstanceSupplier{

    private int rings;
    private int segments;
    private float radius;

    public UVSphereSupplier(float radius, int rings, int segments, Color color){
        super(color);
        this.radius = radius;
        this.rings = rings;
        this.segments = segments;
    }

    public UVSphereSupplier(float radius, int rings, int segments){
        super(BuilderUtils.getRandomColor());
        this.radius = radius;
        this.rings = rings;
        this.segments = segments;
    }


    @Override
    public Model createModel() {
        modelBuilder.begin();
        b = modelBuilder.part("uv_sphere", primitiveType, attributes,mat);

        float stepTheta = PI / (float) rings;
        float stepPhi = TWO_PI / (float) segments;

        for (int row = 1; row < rings; row++) {
            float theta = row * stepTheta;
            for (int col = 0; col < segments; col++) {
                float phi = col * stepPhi;
                float x = (float) (radius * Math.cos(phi) * Math.sin(theta));
                float y = (float) (radius * Math.cos(theta));
                float z = (float) (radius * Math.sin(phi) * Math.sin(theta));
                v(x, y, z);
            }
        }
        //top
        v(0, radius, 0);
        //bottom
        v(0, -radius, 0);
        for (int row = 0; row < rings - 2; row++) {
            for (int col = 0; col < segments; col++) {
                short index0 = (short) getIndex(row, (col+1)%segments);
                short index1 = (short) getIndex(row+1, (col + 1)%segments);
                short index2 = (short) getIndex(row + 1, col);
                short index3 = (short) getIndex(row, col);
//                b.rect(index0, index1, index2, index3);
                b.triangle(index0, index1, index2);
                b.triangle(index0, index2, index3);
                if(row == 0){
                    b.triangle(index3, (short) (b.lastIndex() - 1), index0);
                }
                if(row == rings - 3){
                    b.triangle(index2, index1, (short) (b.lastIndex() - 2));
                }
            }

        }
        return modelBuilder.end();
    }
    private int getIndex(int row, int col) {
        int idx = segments * row + col;
        return idx % b.lastIndex();
    }
}
