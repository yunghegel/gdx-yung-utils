package org.yunghegel.gdx.utils.graphics.model;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import org.yunghegel.gdx.utils.graphics.model.ModelUtils;

public class MeshUtils {

    private static final Vector3 v = new Vector3();
    private static final Matrix4 m = new Matrix4();

    public static void offsetUVs(NodePart part, Vector2 displacement) {
        offsetUVs(part.meshPart.mesh, part.meshPart.offset, part.meshPart.size, displacement);
    }
    public static void offsetUVs(MeshPart meshPart, Vector2 displacement) {
        offsetUVs(meshPart.mesh, meshPart.offset, meshPart.size, displacement);
    }

    public static VertexAttributes createAttributes (long usage) {
        final Array<VertexAttribute> attrs = new Array<VertexAttribute>();
        if ((usage & VertexAttributes.Usage.Position) == VertexAttributes.Usage.Position)
            attrs.add(new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE));
        if ((usage & VertexAttributes.Usage.ColorUnpacked) == VertexAttributes.Usage.ColorUnpacked)
            attrs.add(new VertexAttribute(VertexAttributes.Usage.ColorUnpacked, 4, ShaderProgram.COLOR_ATTRIBUTE));
        if ((usage & VertexAttributes.Usage.ColorPacked) == VertexAttributes.Usage.ColorPacked)
            attrs.add(new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE));
        if ((usage & VertexAttributes.Usage.Normal) == VertexAttributes.Usage.Normal) attrs.add(new VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE));
        if ((usage & VertexAttributes.Usage.TextureCoordinates) == VertexAttributes.Usage.TextureCoordinates)
            attrs.add(new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + "0"));
        final VertexAttribute[] attributes = new VertexAttribute[attrs.size];
        for (int i = 0; i < attributes.length; i++)
            attributes[i] = attrs.get(i);
        return new VertexAttributes(attributes);
    }

    public static void offsetUVs(Mesh mesh, int offset, int size, Vector2 displacement) {
        short[] indices = new short[size];
        mesh.getIndices(offset, size, indices, 0);
        int stride = mesh.getVertexSize() / 4;
        float[] vertices = new float[mesh.getNumVertices() * stride];
        mesh.getVertices(vertices);
        VertexAttribute attribute = mesh.getVertexAttribute(VertexAttributes.Usage.TextureCoordinates);
        int uvOffset = attribute.offset / 4;

        for(int i=0 ; i<size ; i++){
            int vertex = indices[i] & 0xFFFF;
            int uvIndex = vertex * stride + uvOffset;
            vertices[uvIndex] += displacement.x;
            vertices[uvIndex+1] += displacement.y;
        }

        mesh.setVertices(vertices);
    }
    public static float[] extractTriangles(ModelInstance modelInstance) {
        int indices = 0;
        Array<Node> nodes = ModelUtils.collectNodes(modelInstance);
        for(Node node : nodes)
            for(NodePart part : node.parts)
                indices += part.meshPart.size;
        float[] triangles = new float[indices * 3];
        int offset = 0;
        for(Node node : nodes)
            for(NodePart part : node.parts)
                offset = extractTriangles(triangles, offset, node, part, modelInstance.transform);
        return triangles;
    }

    private static int extractTriangles(float[] triangles, int triangleOffset, Node node, NodePart part, Matrix4 transform) {
        int size = part.meshPart.size;
        int offset = part.meshPart.offset;
        Mesh mesh = part.meshPart.mesh;
        short[] indices = new short[size];
        mesh.getIndices(offset, size, indices, 0);
        int stride = mesh.getVertexSize() / 4;
        float[] vertices = new float[mesh.getNumVertices() * stride];
        mesh.getVertices(vertices);
        VertexAttribute attribute = mesh.getVertexAttribute(VertexAttributes.Usage.Position);
        int posOffset = attribute.offset / 4;
        m.set(node.globalTransform).mul(transform);
        for(int i=0 ; i<size ; i+=3){
            int vertex = indices[i] & 0xFFFF;
            int vindex = vertex * stride + posOffset;
            float x = vertices[vindex];
            float y = vertices[vindex+1];
            float z = vertices[vindex+2];
            v.set(x, y, z).mul(m);
            triangles[triangleOffset++] = v.x;
            triangles[triangleOffset++] = v.y;
            triangles[triangleOffset++] = v.z;
        }
        return triangleOffset;
    }

    public static float[] getVerticesByAttribute(Mesh mesh,VertexAttribute attribute){
        int stride = mesh.getVertexSize() / 4;
        int offset = attribute.offset / 4;
        int size = attribute.numComponents;

        float[] result = new float[mesh.getNumVertices() * size];
        float[] vertices = new float[mesh.getNumVertices() * stride];
        mesh.getVertices(vertices);

        for(int i=0 ; i<result.length ; i++){
            int vertex = i / size;
            int vindex = vertex * stride + offset;
            result[i] = vertices[vindex];
        }


        return result;
    }

    public static float[][] getIndexedVertexList(Mesh mesh){
        int stride = mesh.getVertexSize() / 4;
        float[] vertices = new float[mesh.getNumVertices() * stride];
        mesh.getVertices(vertices);
        short[] indices = new short[mesh.getNumIndices()];
        mesh.getIndices(indices);
        VertexAttribute attribute = mesh.getVertexAttribute(VertexAttributes.Usage.Position);
        int posOffset = attribute.offset / 4;
        float[][] result = new float[mesh.getNumIndices()/3][];
        for(int i=0 ; i<indices.length ; i+=3){
            int vertex = indices[i] & 0xFFFF;
            int vindex = vertex * stride + posOffset;
            float x = vertices[vindex];
            float y = vertices[vindex+1];
            float z = vertices[vindex+2];
            result[i/3] = new float[]{x,y,z};
        }
        return result;
    }

    public static int getVertexCountByAttribute(Mesh mesh,VertexAttribute attribute){
        int stride = mesh.getVertexSize() / 4;
        return mesh.getNumVertices() * stride;
    }

    public static int getTotalVerticesCount(Mesh mesh){
        int stride = mesh.getVertexSize() / 4;
        return mesh.getNumVertices() * stride;
    }

    public static int getTotalIndicesCount(Mesh mesh){
        return mesh.getNumIndices();
    }

    //get total in model
    public static int getTotalVerticesCount(ModelInstance modelInstance){
        int count = 0;
        Array<Node> nodes = ModelUtils.collectNodes(modelInstance);
        for(Node node : nodes)
            for(NodePart part : node.parts)
                count += getTotalVerticesCount(part.meshPart.mesh);
        return count;
    }

    public static int getTotalIndicesCount(ModelInstance modelInstance){
        int count = 0;
        Array<Node> nodes = ModelUtils.collectNodes(modelInstance);
        for(Node node : nodes)
            for(NodePart part : node.parts)
                count += getTotalIndicesCount(part.meshPart.mesh);
        return count;
    }

    public static float[] getAtrributeAtIndex(Mesh mesh,VertexAttribute attribute,int index){
        //check if mesh has attribute
        int size = attribute.numComponents;
        int stride = mesh.getVertexSize() / 4;
        float[] vals = new float[size];
        float[] vertices = new float[mesh.getNumVertices() * stride];
        mesh.getVertices(vertices);
        int offset = attribute.offset / 4;
        for(int i=0 ; i<size ; i++){
            vals[i] = vertices[index*stride+offset+i];
        }
        return vals;
    }

    public static float[] getPositionAtIndex(Mesh mesh,int index){
        VertexAttribute attribute = mesh.getVertexAttribute(VertexAttributes.Usage.Position);
        return getAtrributeAtIndex(mesh,attribute,index);
    }

    public static float[] getNormalAtIndex(Mesh mesh,int index){
        VertexAttribute attribute = mesh.getVertexAttribute(VertexAttributes.Usage.Normal);
        return getAtrributeAtIndex(mesh,attribute,index);
    }

    public static float[] getUVAtIndex(Mesh mesh,int index){
        VertexAttribute attribute = mesh.getVertexAttribute(VertexAttributes.Usage.TextureCoordinates);
        return getAtrributeAtIndex(mesh,attribute,index);
    }

    public static float[] getColorAtIndex(Mesh mesh,int index){
        VertexAttribute attribute = mesh.getVertexAttribute(VertexAttributes.Usage.ColorUnpacked);
        return getAtrributeAtIndex(mesh,attribute,index);
    }

    public static float[] getVerticesArray(Mesh mesh){
        int stride = mesh.getVertexSize() / 4;
        float[] vertices = new float[mesh.getNumVertices() * stride];
        mesh.getVertices(vertices);
        return vertices;
    }











}
