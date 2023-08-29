package org.yunghegel.gdx.utils.graphics;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import org.yunghegel.gdx.utils.graphics.model.ModelBuilderPlus;
import org.yunghegel.gdx.utils.shaders.DefaultShader;
import org.yunghegel.gdx.utils.shaders.DefaultShaderProvider;
import org.yunghegel.gdx.utils.shaders.GridShader;
import org.yunghegel.gdx.utils.shaders.GridShaderProvider;

public class Grid {

    private ModelBatch batch;
    private ModelInstance grid;
    private ModelInstance axis;
    private ModelInstance origin;

    private MeshPartBuilder.VertexInfo tmp0 = new MeshPartBuilder.VertexInfo();
    private MeshPartBuilder.VertexInfo tmp1 = new MeshPartBuilder.VertexInfo();

    float step,near=1,far=50;
    int size;

    public Grid(float alpha,int size, float step, float ...subdivisions) {
        batch = new ModelBatch(new GridShaderProvider());
        grid = new ModelInstance(createGridLines(alpha, size ,step , subdivisions));
        alpha+=(1-alpha)/2;
        axis = new ModelInstance(createAxisLines(alpha, size));
        origin = new ModelInstance(createOriginLines(alpha, size));


        this.step = step;
        this.size = size;

    }

    public void render(Camera cam) {
        batch.begin(cam);
        batch.render(axis);
        batch.render(grid);
        batch.end();

    }

    public void render(Camera cam, Environment env) {
        translateToBeInView(cam, (int) size, near, far, step);
        batch.begin(cam);
        batch.render(axis,env);
        batch.render(grid,env);
        batch.end();


//        grid.transform.setTranslation(cam.position.x,0,cam.position.z);
    }

    public boolean isGridOutOfRange(Vector3 camPos, int size, float fogNear, float fogFar){
        return camPos.x < -size - fogFar || camPos.x > size + fogFar || camPos.z < -size - fogFar || camPos.z > size + fogFar;
    }

    public void translateToBeInView(Camera cam, int size, float fogNear, float fogFar, float step){
        //we want the effect that the grid is infinite, so we translate it to be in view
        //we need to snap the grid to its step size



        Vector3 camPos = cam.position.cpy();
        Vector3 gridPos = new Vector3();



        grid.transform.getTranslation(gridPos);

        float x = camPos.x;
        float z = camPos.z;

        //round to the nearest int multiple of step

        x = Math.round(x/step)*step;
        z = Math.round(z/step)*step;

        grid.transform.setTranslation(x,0,z);
        origin.transform.setTranslation(x,0,z);









    }

    public ModelInstance createGridLines(float alpha,int size, float step, float ...subdivisions){
        int numSubdivisions = subdivisions.length;
        float GRID_MIN = -size;
        float GRID_MAX = size;
        float GRID_STEP = step;

        ModelBuilder modelBuilder = new ModelBuilder();
        Material mat = new Material();
        mat.set(new BlendingAttribute(GL20.GL_SRC_ALPHA , GL20.GL_ONE_MINUS_SRC_ALPHA, alpha));
//        mat.set(new DepthTestAttribute(GL20.GL_LEQUAL , false));

        Color color = new Color(1,1,1f,1f);


        modelBuilder.begin();
        MeshBuilder builder;

        builder = (MeshBuilder) modelBuilder.part("grid_pos" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.ColorUnpacked, mat);


        builder.setColor(color);
        for (float t = GRID_MIN; t <= GRID_MAX; t += GRID_STEP) {
            if (t == 0) continue;
            builder.setColor(color);
            Vector3 normal = new Vector3(0,1,0);
            Vector3 position1 = new Vector3(t,0,GRID_MIN);
            Vector3 position2 = new Vector3(t,0,GRID_MAX);
            Vector2 uv1 = calculateGridUV(GRID_MIN,GRID_MAX,position1);
            Vector2 uv2 = calculateGridUV(GRID_MIN,GRID_MAX,position2);
            tmp0.set(position1,normal,color,new Vector2(0,0));
            tmp1.set(position2,normal,color,new Vector2(0,0));

            builder.line(tmp0,tmp1);

        }



        builder = (MeshBuilder) modelBuilder.part("grid_neg" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.ColorUnpacked , mat);
        builder.setColor(color);
        for (float t = GRID_MIN; t <= GRID_MAX; t += GRID_STEP) {
            if (t == 0) continue;
            builder.setColor(color);
            Vector3 normal = new Vector3(0,1,0);
            Vector3 position1 = new Vector3(GRID_MIN,0,t);
            Vector3 position2 = new Vector3(GRID_MAX,0,t);
            Vector2 uv1 = calculateGridUV(GRID_MIN,GRID_MAX,position1);
            Vector2 uv2 = calculateGridUV(GRID_MIN,GRID_MAX,position2);
            tmp0.set(position1,normal,color,uv1);
            tmp1.set(position2,normal,color,uv2);
            builder.line(tmp0,tmp1);
        }

        float lastSubdivision = 0;

        for (int i = 0; i < numSubdivisions; i++) {
            GRID_STEP = subdivisions[i];
            Material material = new Material();
            float new_alpha = alpha/(i+2);
            material.set(new BlendingAttribute(GL20.GL_SRC_ALPHA , GL20.GL_ONE_MINUS_SRC_ALPHA , new_alpha));
//            material.set(new DepthTestAttribute(GL20.GL_LEQUAL , false));

            builder = (MeshBuilder) modelBuilder.part("grid_pos_subdivision_" +i , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates  | VertexAttributes.Usage.ColorUnpacked, material);

            for (float t = GRID_MIN; t <= GRID_MAX; t += GRID_STEP) {
                if (t == 0) continue;
                //check if this line is one we've already drawn
                if(t % (lastSubdivision) == 0) continue;
                builder.setColor(color);
                Vector3 normal = new Vector3(0,1,0);
                Vector3 position1 = new Vector3(t,0,GRID_MIN);
                Vector3 position2 = new Vector3(t,0,GRID_MAX);
                Vector2 uv1 = calculateGridUV(GRID_MIN,GRID_MAX,position1);
                Vector2 uv2 = calculateGridUV(GRID_MIN,GRID_MAX,position2);

                tmp0.set(position1,normal,color,uv1);
                tmp1.set(position2,normal,color,uv2);

                builder.line(tmp0,tmp1);
            }
            builder = (MeshBuilder) modelBuilder.part("grid_neg_subdivision_" +i , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.ColorUnpacked, material);

            for (float t = GRID_MIN; t <= GRID_MAX; t += GRID_STEP) {
                if (t == 0) continue;
                //check if this line is one we've already drawn
                if(t % (lastSubdivision) == 0) continue;
                builder.setColor(color);
                Vector3 normal = new Vector3(0,1,0);
                Vector3 position1 = new Vector3(GRID_MIN,0,t);
                Vector3 position2 = new Vector3(GRID_MAX,0,t);
                Vector2 uv1 = calculateGridUV(GRID_MIN,GRID_MAX,position1);
                Vector2 uv2 = calculateGridUV(GRID_MIN,GRID_MAX,position2);
                tmp0.set(position1,normal,color,uv1);
                tmp1.set(position2,normal,color,uv2);
                builder.line(tmp0,tmp1);
            }

            lastSubdivision = GRID_STEP;

        }


        Model axesModel = modelBuilder.end();

        return new ModelInstance(axesModel);

    }

    public ModelInstance createAxisLines(float alpha,int size){
        float GRID_MIN = -size;
        float GRID_MAX = size;

        ModelBuilder modelBuilder = new ModelBuilder();
        Material mat = new Material();
        mat.set(new BlendingAttribute(GL20.GL_SRC_ALPHA , GL20.GL_ONE, alpha));
            mat.set(new DepthTestAttribute(GL20.GL_LEQUAL , true));

        modelBuilder.begin();
        MeshBuilder builder;



        builder = (MeshBuilder) modelBuilder.part("axis" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.ColorUnpacked, mat);

        //first create colored axis lines at the origin


        builder.setColor(Color.RED);
        builder.line(GRID_MIN , 0f , 0 , GRID_MAX , 0 , 0);
//        builder.setColor(Color.GREEN);
//        builder.line(0 , GRID_MIN , 0 , 0 , GRID_MAX , 0);
        builder.setColor(Color.BLUE);
        builder.line(0 , 0f , GRID_MIN , 0 , 0 , GRID_MAX);

        Model axesModel = modelBuilder.end();

        return new ModelInstance(axesModel);
    }

    public ModelInstance createOriginLines(float alpha,int size){
        float GRID_MIN = -size;
        float GRID_MAX = size;

        ModelBuilder modelBuilder = new ModelBuilder();
        Material mat = new Material();
        mat.set(new BlendingAttribute(GL20.GL_SRC_ALPHA , GL20.GL_ONE, alpha));
        mat.set(new DepthTestAttribute(GL20.GL_LEQUAL , true));

        modelBuilder.begin();

        Color color = new Color(1,1,1f,1f);

        MeshBuilder builder;
        builder = (MeshBuilder) modelBuilder.part("axis" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.ColorUnpacked, mat);

        Vector3 normal,position1,position2;
        Vector2 uv1,uv2;

        normal = new Vector3(0,1,0);
        position1 = new Vector3(GRID_MIN,0,0);
        position2 = new Vector3(GRID_MAX,0,0);
        uv1 = calculateGridUV(GRID_MIN,GRID_MAX,position1);
        uv2 = calculateGridUV(GRID_MIN,GRID_MAX,position2);
        tmp0.set(position1,normal,color,uv1);
        tmp1.set(position2,normal,color,uv2);

        builder.setColor(color);
        builder.line(tmp0,tmp1);

        position1 = new Vector3(0,GRID_MIN,0);
        position2 = new Vector3(0,GRID_MAX,0);
        uv1 = calculateGridUV(GRID_MIN,GRID_MAX,position1);
        uv2 = calculateGridUV(GRID_MIN,GRID_MAX,position2);
        tmp0.set(position1,normal,color,uv1);
        tmp1.set(position2,normal,color,uv2);

        builder.setColor(color);
        builder.line(tmp0,tmp1);

        position1 = new Vector3(0,0,GRID_MIN);
        position2 = new Vector3(0,0,GRID_MAX);
        uv1 = calculateGridUV(GRID_MIN,GRID_MAX,position1);
        uv2 = calculateGridUV(GRID_MIN,GRID_MAX,position2);
        tmp0.set(position1,normal,color,uv1);
        tmp1.set(position2,normal,color,uv2);

        builder.setColor(color);
        builder.line(tmp0,tmp1);

        Model axesModel = modelBuilder.end();

        return new ModelInstance(axesModel);
    }

    public Vector2 calculateGridUV(float min, float max, Vector3 pos){
        float u = (pos.x - min)/(max - min);
        float v = (pos.z - min)/(max - min);
        return new Vector2(u,v);
    }

}
