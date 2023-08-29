package org.yunghegel.gdx.utils.graphics.model;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;

public class MeshBuilder {

    private float[] vertices;
    private short[] indices;

    private int vertexCount;
    private int indexCount;

    private VertexAttributes attributes;
    private int stride;

    public short iOff;

    public MeshBuilder() {
        vertices = new float[16];
        indices  = new short[8 ];
    }

    public MeshBuilder(VertexAttributes attributes) {
        this();
        setAttributes(attributes);
    }

    public MeshBuilder(long usage) {
        this(getAttributes(usage));
    }

    public void setAttributes(VertexAttributes attributes) {
        this.attributes = attributes;
        this.stride = attributes.vertexSize >> 2;
    }

    public void setAttibutes(long usage) {
        setAttributes(getAttributes(usage));
    }

    private void ensureVertexCapacity(int capacity) {
        if (capacity >= vertices.length) {
            float[] tmp = new float[(int) (capacity * 1.75F)];
            System.arraycopy(vertices, 0, tmp, 0, vertexCount);
            vertices = tmp;
        }
    }

    private void ensureIndexCapacity(int capacity) {
        if (capacity >= indices.length) {
            short[] tmp = new short[(int) (capacity * 1.75F)];
            System.arraycopy(indices, 0, tmp, 0, indexCount);
            indices = tmp;
        }
    }

    public void addVertices(float... vertices) {
        int n = vertices.length;
        ensureVertexCapacity(vertexCount + n);
        System.arraycopy(vertices, 0, this.vertices, vertexCount, n);
        vertexCount += n;
    }

    public void addIndices(short... indices) {
        int n = indices.length;
        ensureIndexCapacity(indexCount + n);
        System.arraycopy(indices, 0, this.indices, indexCount, n);
        indexCount += n;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getIndexCount() {
        return indexCount;
    }

    public void clear() {
        vertexCount = 0;
        indexCount = 0;
        iOff = 0;
    }

    public Mesh end(boolean clear) {

        Mesh mesh = new Mesh(true, vertexCount / stride, indexCount, attributes);
        mesh.setVertices(vertices, 0, vertexCount).setIndices(indices, 0, indexCount);

        if(clear) {
            clear();
        }

        return mesh;
    }

    public static VertexAttributes getAttributes(long usage) {

        final Array<VertexAttribute> attrs = new Array<VertexAttribute>();
        if ((usage & VertexAttributes.Usage.Position) == VertexAttributes.Usage.Position)
            attrs.add(new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE));
        if ((usage & VertexAttributes.Usage.ColorPacked) == VertexAttributes.Usage.ColorPacked)
            attrs.add(new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE));
        else if ((usage & VertexAttributes.Usage.ColorUnpacked) == VertexAttributes.Usage.ColorUnpacked)
            attrs.add(new VertexAttribute(VertexAttributes.Usage.ColorUnpacked, 4, ShaderProgram.COLOR_ATTRIBUTE));
        if ((usage & VertexAttributes.Usage.Normal) == VertexAttributes.Usage.Normal)
            attrs.add(new VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE));
        if ((usage & VertexAttributes.Usage.TextureCoordinates) == VertexAttributes.Usage.TextureCoordinates)
            attrs.add(new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE+"0"));
        final VertexAttribute attributes[] = new VertexAttribute[attrs.size];
        for (int i = 0; i < attributes.length; i++)
            attributes[i] = attrs.get(i);

        return new VertexAttributes(attributes);

    }


}
