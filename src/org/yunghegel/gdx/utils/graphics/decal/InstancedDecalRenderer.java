package org.yunghegel.gdx.utils.graphics.decal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class InstancedDecalRenderer {

    float[] vertices;
    Mesh mesh;
    int numInstances;
    private FloatBuffer offsets;
    private Array<InstancedDecal> decals = new Array<InstancedDecal>();
    ShaderProgram shader;
    InstancedDecal decal;
    private boolean needUpdate = false;
    Camera camera;




    public InstancedDecalRenderer(int numInstances, InstancedDecal decal, Camera camera){
        this.camera=camera;
        this.numInstances=numInstances;
        this.decal=decal;
        shader = createShaderProgram();
        initialize(numInstances);
    }




    public void initialize(int size) {
        vertices = new float[4*5];

        Mesh.VertexDataType vertexDataType = Mesh.VertexDataType.VertexArray;

        if (Gdx.gl30 != null) {
            vertexDataType = Mesh.VertexDataType.VertexBufferObjectWithVAO;
        }

        mesh = new Mesh(vertexDataType, false, 4, 6,
                new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + "0"));

        mesh.enableInstancedRendering(true,numInstances,new VertexAttribute(VertexAttributes.Usage.Generic, 3, "i_position"));
        offsets = BufferUtils.newFloatBuffer(numInstances * 3);

        mesh.setInstanceData(offsets);

        short[] indices = new short[6];
        int v = 0;

        for (int i = 0; i < indices.length; i += 6, v += 4) {
            indices[i] = (short) v;
            indices[i + 1] = (short) (v + 2);
            indices[i + 2] = (short) (v + 1);
            indices[i + 3] = (short) (v + 1);
            indices[i + 4] = (short) (v + 2);
            indices[i + 5] = (short) (v+3);
        }

        mesh.setIndices(indices);
    }

    public void render(){
        if(needUpdate){
            createInstanceData();
            needUpdate=false;
        }
        decal.getMaterial().set();
        decal.update();
        flush(shader);
    }


    protected void flush(ShaderProgram shader) {
        createInstanceData();
        shader.setUniformMatrix("u_projTrans", camera.projection);
        shader.setUniformi("u_texture", 0);
        shader.setUniformMatrix("u_viewTrans",camera.view);
        mesh.setVertices(vertices);
        mesh.render(shader, GL20.GL_TRIANGLES);
    }

    public void updateInstanceData(){
        needUpdate = true;
    }

    private void createInstanceData(){
        offsets = BufferUtils.newFloatBuffer(numInstances * 3);
        for (int i = 0; i <numInstances ; i++) {
            int offset = i * 3;
            offsets.position(offset);
            offsets.put(decal.instanceDataSupplier.get());
            mesh.updateInstanceData(0,offsets);
        }
    }

    private ShaderProgram createShaderProgram(){
        ShaderProgram shaderProgram = new ShaderProgram(Gdx.files.internal("glsl/instanced_decal.vs.glsl"),Gdx.files.internal("glsl/instanced_decal.fs.glsl"));
        if(!shaderProgram.isCompiled()){
            throw new IllegalArgumentException("Shader compilation failed:\n"+shaderProgram.getLog());
        }
        return shaderProgram;
    }


}
