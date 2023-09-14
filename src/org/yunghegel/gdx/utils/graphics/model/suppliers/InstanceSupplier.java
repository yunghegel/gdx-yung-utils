package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;
import org.yunghegel.gdx.utils.graphics.model.ModelSupplier;

public abstract class InstanceSupplier implements ModelSupplier {


    protected ModelBuilder modelBuilder = new ModelBuilder();
    protected MeshPartBuilder b;
    public MeshPartBuilder.VertexInfo v0 = new MeshPartBuilder.VertexInfo();
    public MeshPartBuilder.VertexInfo v1 = new MeshPartBuilder.VertexInfo();
    protected short i1, i2, i3, i4;

    public static final float PI = (float) Math.PI;
    public static final float HALF_PI = PI * 0.5f;
    public static final float TWO_PI = PI + PI;
    public static final float ONE_THIRD = 1f / 3f;
    public static final float QUARTER_PI = PI / 4f;
    public static final float ZERO_TOLERANCE = 0.00001f;
    public static final float TRIBONACCI_CONSTANT = 1.8392868f;
    public static final float GOLDEN_RATIO = (float) ((1f + Math.sqrt(5f)) / 2f);


    public InstanceSupplier(Color color){
        this.color = color;
        this.mat = BuilderUtils.buildMaterial(color);
    }


    protected Material mat;
    protected Color color;
    protected Vector3 pos = new Vector3();
    protected int primitiveType= GL20.GL_TRIANGLES;
    protected long attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal | VertexAttributes.Usage.ColorUnpacked;

    public InstanceSupplier setPrimitiveType(int primitiveType) {
        this.primitiveType = primitiveType;
        return this;
    }

    public InstanceSupplier setAttributes(long attributes) {
        this.attributes = attributes;
        return this;
    }

    public InstanceSupplier setColor(Color color) {
        this.color = color;
        return this;
    }

    public InstanceSupplier setPosition(Vector3 pos) {
        this.pos = pos;
        return this;
    }

    public InstanceSupplier setPosition(float x,float y,float z) {
        this.pos.set(x,y,z);
        return this;
    }

    public InstanceSupplier addMaterialAttribute(Attribute attribute) {
        this.mat.set(attribute);
        return this;
    }

    public InstanceSupplier addMaterialAttributes(Attribute ... attributes) {
        for (Attribute attribute : attributes) {
            this.mat.set(attribute);
        }
        return this;
    }

    public ModelInstance build(){
        return new ModelInstance(createModel(), pos);
    }

    public MeshPartBuilder.VertexInfo createVertexInfo(Vector3 pos, Vector3 normal, Color color){
        return new MeshPartBuilder.VertexInfo().setPos(pos).setNor(normal).setCol(color);
    }

    public MeshPartBuilder.VertexInfo createVertexInfo(Vector3 pos, Vector3 normal){
        return new MeshPartBuilder.VertexInfo().setPos(pos).setNor(normal).setCol(color);
    }

    public MeshPartBuilder.VertexInfo createVInfo(Vector3 pos,Color color){
        Vector3 normal = pos.cpy().nor();
        return new MeshPartBuilder.VertexInfo().setPos(pos).setNor(normal).setCol(color);
    }

    public MeshPartBuilder.VertexInfo createVInfo(float x,float y,float z){
        Vector3 normal = pos.cpy().nor();
        return new MeshPartBuilder.VertexInfo().setPos(pos).setNor(normal);
    }



    public Vector3 v3(float x,float y,float z){
        return new Vector3(x,y,z);
    }

    public short v(float x,float y,float z){
        b.setColor(color);
       return b.vertex(v3(x,y,z),v3(x,y,z).cpy().nor(),color,null);
    }


    public int toOneDimensionalIndex(int rowIndex, int colIndex, int numberOfColumns) {
        return rowIndex * numberOfColumns + colIndex;
    }


}
