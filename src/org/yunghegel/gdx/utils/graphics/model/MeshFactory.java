package org.yunghegel.gdx.utils.graphics.model;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;

public class MeshFactory {

    /**
     * Returns a grid mesh on the XY plane with the given size.
     * @param size The size of the grid.
     * @return
     */
    public static Mesh xyGrid(final int size,VertexAttribute... attributes) {
        final int floatsPerVertex = 6;
        final int vertexCount = size * size;
        float [] vertices = new float[vertexCount * floatsPerVertex];

        for(int i=0 ; i<size ; i++){
            for(int j=0 ; j<size ; j++){
                int index = (i * size + j) * floatsPerVertex;
                vertices[index] = j;
                vertices[index + 1] = i;
                vertices[index + 2] = 0;

                vertices[index + 3] = 0;
                vertices[index + 4] = 0;
                vertices[index + 5] = 1;
            }
        }

        final int indicesCount = (size - 1) * (size - 1) * 6;
        short[] indices = new short[indicesCount];
        for(int i=0 ; i<size-1 ; i++){
            for(int j=0 ; j<size-1 ; j++){
                int index = (i * (size-1) + j) * 6;
                indices[index] = (short)(i * size + j);
                indices[index + 1] = (short)(i * size + j + 1);
                indices[index + 2] = (short)((i+1) * size + j);

                indices[index + 3] = (short)((i+1) * size + j);
                indices[index + 4] = (short)(i * size + j + 1);
                indices[index + 5] = (short)((i+1) * size + j+1);
            }
        }

        Mesh mesh = new Mesh(true, vertices.length, vertices.length, attributes);
        mesh.setVertices(vertices);
        mesh.setIndices(indices);

        return mesh;
    }

    /**
     * Returns a grid mesh on the XZ plane with the given size.
     * @param size The size of the grid.
     * @return
     */

    public static Mesh xzGrid(final int size,VertexAttribute... attributes){
        final int floatsPerVertex = 6;
        final int vertexCount = size * size;
        float [] vertices = new float[vertexCount * floatsPerVertex];

        for(int i=0 ; i<size ; i++){
            for(int j=0 ; j<size ; j++){
                int index = (i * size + j) * floatsPerVertex;
                vertices[index] = j;
                vertices[index + 1] = 0;
                vertices[index + 2] = i;

                vertices[index + 3] = 0;
                vertices[index + 4] = 1;
                vertices[index + 5] = 0;
            }
        }

        final int indicesCount = (size - 1) * (size - 1) * 6;
        short[] indices = new short[indicesCount];
        for(int i=0 ; i<size-1 ; i++){
            for(int j=0 ; j<size-1 ; j++){
                int index = (i * (size-1) + j) * 6;
                indices[index] = (short)(i * size + j);
                indices[index + 1] = (short)(i * size + j + 1);
                indices[index + 2] = (short)((i+1) * size + j);

                indices[index + 3] = (short)((i+1) * size + j);
                indices[index + 4] = (short)(i * size + j + 1);
                indices[index + 5] = (short)((i+1) * size + j+1);
            }
        }

        Mesh mesh = new Mesh(true, vertices.length, vertices.length, attributes);
        mesh.setVertices(vertices);
        mesh.setIndices(indices);

        return mesh;
    }

    /**
     * Returns a grid mesh on the YZ plane with the given size.
     * @param size The size of the grid.
     * @return
     */
    public static Mesh yzGrid(int size,VertexAttribute... attributes){
        final int floatsPerVertex = 6;
        final int vertexCount = size * size;
        float [] vertices = new float[vertexCount * floatsPerVertex];

        for(int i=0 ; i<size ; i++){
            for(int j=0 ; j<size ; j++){
                int index = (i * size + j) * floatsPerVertex;
                vertices[index] = 0;
                vertices[index + 1] = j;
                vertices[index + 2] = i;

                vertices[index + 3] = 1;
                vertices[index + 4] = 0;
                vertices[index + 5] = 0;
            }
        }

        final int indicesCount = (size - 1) * (size - 1) * 6;
        short[] indices = new short[indicesCount];
        for(int i=0 ; i<size-1 ; i++){
            for(int j=0 ; j<size-1 ; j++){
                int index = (i * (size-1) + j) * 6;
                indices[index] = (short)(i * size + j);
                indices[index + 1] = (short)(i * size + j + 1);
                indices[index + 2] = (short)((i+1) * size + j);

                indices[index + 3] = (short)((i+1) * size + j);
                indices[index + 4] = (short)(i * size + j + 1);
                indices[index + 5] = (short)((i+1) * size + j+1);
            }
        }

        Mesh mesh = new Mesh(true, vertices.length, vertices.length, attributes);
        mesh.setVertices(vertices);
        mesh.setIndices(indices);

        return mesh;
    }


}
