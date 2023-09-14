package org.yunghegel.gdx.utils.graphics.decal;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

import java.nio.FloatBuffer;

public class InstancedDecal extends Decal {

    //position, texcoord, and instance position
    private static final int VERTEX_SIZE =  3+2;
    private static final int VERTEX_SIZE_BYTES = VERTEX_SIZE * 4;

    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;
    private static final int U1 = 3;
    private static final int V1 = 4;
    private static final int IX = 5;
    private static final int IY = 6;
    private static final int IZ = 7;

    protected InstanceDataSupplier instanceDataSupplier;
    private FloatBuffer offsets;
    protected int numInstances;

    public interface InstanceDataSupplier {
        float[] get();
    }

    public InstancedDecal(int numInstances, TextureRegion textureRegion,InstanceDataSupplier instanceDataSupplier){
        super();
        this.numInstances = numInstances;
        this.instanceDataSupplier = instanceDataSupplier;
        setTextureRegion(textureRegion);
    }

    public void updateInstanceData(){

    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    protected void transformVertices(){
        float x, y, z, w;
        float tx, ty;
        if (transformationOffset != null) {
            tx = -transformationOffset.x;
            ty = -transformationOffset.y;
        } else {
            tx = ty = 0;
        }

        for (int i = 1; i <=4  ; i++) {


            int i1=VERTEX_SIZE * i + X;
            int i2=VERTEX_SIZE * i + Y;
            int i3=VERTEX_SIZE * i + Z;


            x = (vertices[i1] + tx)* scale.x;
            y = (vertices[i2] + ty) * scale.y;
            z = vertices[i3];

            //apply rotation

            vertices[i1] = rotation.w * x + rotation.y * z - rotation.z * y;
            vertices[i2] = rotation.w * y + rotation.z * x - rotation.x * z;
            vertices[i3] = rotation.w * z + rotation.x * y - rotation.y * x;
            w = -rotation.x * x - rotation.y * y - rotation.z * z;
            rotation.conjugate();

            x=vertices[i1];
            y=vertices[i2];
            z=vertices[i3];

            vertices[i1] = w * rotation.x + x * rotation.w + y * rotation.z - z * rotation.y;
            vertices[i2] = w * rotation.y + y * rotation.w + z * rotation.x - x * rotation.z;
            vertices[i3] = w * rotation.z + z * rotation.w + x * rotation.y - y * rotation.x;
            rotation.conjugate();

            //apply position
            vertices[i1] += position.x-tx;
            vertices[i2] += position.y-ty;
            vertices[i3] += position.z;
        }

    }


    @Override
    protected void updateUVs(){
        TextureRegion tr = getTextureRegion();
        if (tr == null) return;

        float u = tr.getU();
        float v = tr.getV();

        float u2 = tr.getU2();
        float v2 = tr.getV2();

        for (int i = 1; i<=4 ; i++) {
            int i1= i * U1;
            int i2=i *V1;
            vertices[i1] = u + (u2 - u) * vertices[i1];
            vertices[i2] = v + (v2 - v) * vertices[i2];
        }

    }

    public void setInstancePosition(float x,float y,float z){
        for (int i = 1; i <=4  ; i++) {
            int i1=VERTEX_SIZE * i + IX;
            int i2=VERTEX_SIZE * i + IY;
            int i3=VERTEX_SIZE * i + IZ;
            vertices[i1]=x;
            vertices[i2]=y;
            vertices[i3]=z;
        }
    }



}
