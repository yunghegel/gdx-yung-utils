package org.yunghegel.gdx.utils.graphics.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.*;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import org.yunghegel.gdx.utils.ColorUtils;
import org.yunghegel.gdx.utils.graphics.MaterialUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModelBuilderPlus {
    public static final Color[] colors = {Color.RED , Color.GREEN , Color.BLUE , Color.YELLOW , Color.CYAN , Color.MAGENTA , Color.PURPLE , Color.ORANGE , Color.BROWN , Color.PINK , Color.LIME , Color.TEAL , Color.NAVY , Color.MAROON , Color.OLIVE , Color.GRAY , Color.LIGHT_GRAY , Color.DARK_GRAY , Color.WHITE , Color.BLACK};
    protected static Color COLOR_X = Color.RED;
    protected static Color COLOR_Y = Color.GREEN;
    protected static Color COLOR_Z = Color.BLUE;
    protected static Color COLOR_XYZ = Color.CYAN;

    private static final MeshPartBuilder.VertexInfo v0 = new MeshPartBuilder.VertexInfo();
    private static final MeshPartBuilder.VertexInfo v1 = new MeshPartBuilder.VertexInfo();
    static MeshPartBuilder.VertexInfo tmp0 = new MeshPartBuilder.VertexInfo();
    static MeshPartBuilder.VertexInfo tmp1 = new MeshPartBuilder.VertexInfo();
    static VertexAttributes attributes = new VertexAttributes(
            VertexAttribute.Position(),
            VertexAttribute.Normal(),
            new VertexAttribute(VertexAttributes.Usage.Tangent, 4, ShaderProgram.TANGENT_ATTRIBUTE),
            VertexAttribute.TexCoords(0));

    public static ModelInstance createAxes() {
        final float GRID_MIN = -100f;
        final float GRID_MAX = 100f;
        final float GRID_STEP = 2.5f;
        ModelBuilder modelBuilder = new ModelBuilder();
        Material mat = new Material();
        MaterialUtils.addAlphaAttribute(mat , .8f);
        Color color = new Color(Color.valueOf("7f7f7f"));
        float lightness = 0.3f;
        float alpha = 0.3f;
        modelBuilder.begin();
        MeshPartBuilder builder = modelBuilder.part("grid" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked , mat);
        builder.setColor(color);
        for (float t = GRID_MIN; t <= GRID_MAX; t += GRID_STEP) {
            builder.line(t , 0 , GRID_MIN , t , 0 , GRID_MAX);
            builder.line(GRID_MIN , 0 , t , GRID_MAX , 0 , t);
        }
        builder = modelBuilder.part("axes" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked , new Material());
        builder.setColor(Color.RED);
        builder.line(0 , 0f , 0 , 300 , 0 , 0);
        builder.setColor(Color.GREEN);
        builder.line(0 , 0f , 0 , 0 , 300 , 0);
        builder.setColor(Color.BLUE);
        builder.line(0 , 0f , 0 , 0 , 0 , 300);
        Model axesModel = modelBuilder.end();
        ModelInstance axesInstance = new ModelInstance(axesModel);

        return axesInstance;
    }

    public static ModelInstance createGrid(float step, float alpha, boolean drawAxisLines){
        final float GRID_MIN = -300f;
        final float GRID_MAX = 300f;
        final float GRID_STEP = step;
        ModelBuilder modelBuilder = new ModelBuilder();
        Material mat = new Material();
        mat.set(new BlendingAttribute(GL20.GL_SRC_ALPHA , GL20.GL_ONE_MINUS_SRC_ALPHA , alpha));
        mat.set(new DepthTestAttribute(GL20.GL_LEQUAL , false));

        Color color = new Color(Color.valueOf("7f7f7f"));



        modelBuilder.begin();
        MeshPartBuilder builder;
        if(drawAxisLines){
            builder = modelBuilder.part("axis" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.ColorUnpacked, mat);

            //first create colored axis lines at the origin
            builder.setColor(Color.RED);
            builder.line(GRID_MIN , 0f , 0 , GRID_MAX , 0 , 0);
            builder.setColor(Color.GREEN);
            builder.line(0 , GRID_MIN , 0 , 0 , GRID_MAX , 0);
            builder.setColor(Color.BLUE);
            builder.line(0 , 0f , GRID_MIN , 0 , 0 , GRID_MAX);
        }


        builder = modelBuilder.part("gridPos" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked , mat);

        //then create the grid lines,skip the lines that are on the axis
        builder.setColor(color);
        for (float t = GRID_MIN; t <= GRID_MAX; t += GRID_STEP) {
            if (t == 0) continue;
            builder.line(t , 0 , GRID_MIN , t , 0 , GRID_MAX);
        }

        builder = modelBuilder.part("gridPos" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked , mat);
        builder.setColor(color);
        for (float t = GRID_MIN; t <= GRID_MAX; t += GRID_STEP) {
            if (t == 0) continue;
            builder.line(GRID_MIN , 0 , t , GRID_MAX , 0 , t);
        }



        Model axesModel = modelBuilder.end();
        ModelInstance axesInstance = new ModelInstance(axesModel);

        return axesInstance;
    }

    public static ModelInstance createGridLines(float alpha,int size, float step, float ...subdivisions){
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

        Array<Mesh> meshes = new Array<Mesh>();

        builder = (MeshBuilder) modelBuilder.part("axis" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.ColorUnpacked, new Material());

        //first create colored axis lines at the origin
        builder.setColor(Color.RED);
        builder.line(-300 , 0f , 0 , 300 , 0 , 0);
        builder.setColor(Color.GREEN);
        builder.line(0 , -300f , 0 , 0 , 300 , 0);
        builder.setColor(Color.BLUE);
        builder.line(0 , 0f , -300 , 0 , 0 , 300);



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

    public static Vector2 calculateGridUV(float min, float max, Vector3 pos){
        float u = (pos.x - min)/(max - min);
        float v = (pos.z - min)/(max - min);
        return new Vector2(u,v);
    }






    public static ModelInstance createGrid(float step, float alpha){
        final float GRID_MIN = -300f;
        final float GRID_MAX = 300f;
        final float GRID_STEP = step;
        ModelBuilder modelBuilder = new ModelBuilder();
        Material mat = new Material();
        mat.set(new BlendingAttribute(GL20.GL_SRC_ALPHA , GL20.GL_ONE_MINUS_SRC_ALPHA , alpha));
        mat.set(new DepthTestAttribute(GL20.GL_LEQUAL , false));

        Color color = new Color(Color.valueOf("7f7f7f"));

        float lightness = 0.3f;

        modelBuilder.begin();
        MeshPartBuilder builder = modelBuilder.part("axis" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.ColorUnpacked, mat);

        //first create colored axis lines at the origin
        builder.setColor(Color.RED);
        builder.line(-300 , 0f , 0 , 300 , 0 , 0);
        builder.setColor(Color.GREEN);
        builder.line(0 , -300f , 0 , 0 , 300 , 0);
        builder.setColor(Color.BLUE);
        builder.line(0 , 0f , -300 , 0 , 0 , 300);

        builder = modelBuilder.part("grid" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked , mat);

        //then create the grid lines,skip the lines that are on the axis
        builder.setColor(color);
        for (float t = GRID_MIN; t <= GRID_MAX; t += GRID_STEP) {
            if (t == 0) continue;
            builder.line(t , 0 , GRID_MIN , t , 0 , GRID_MAX);
            builder.line(GRID_MIN , 0 , t , GRID_MAX , 0 , t);
        }


        Model axesModel = modelBuilder.end();
        ModelInstance axesInstance = new ModelInstance(axesModel);

        return axesInstance;
    }

    public static ModelInstance createAxisLines(float alpha){
        ModelBuilder modelBuilder = new ModelBuilder();
        Material mat = new Material();
        MaterialUtils.addAlphaAttribute(mat , alpha);
        Color color = new Color(Color.valueOf("7f7f7f"));
        float lightness = 0.6f;

        modelBuilder.begin();
        MeshPartBuilder builder = modelBuilder.part("axes" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked , mat);
        builder.setColor(Color.RED);
        builder.line(0 , 0.03f , 0 , 1000 , 0 , 0);
        builder.line(0 , 0.03f , 0 , -1000 , 0 , 0);
        builder.setColor(Color.BLUE);
        builder.line(0 , 0.03f , 0 , 0 , 0 , 1000);
        builder.line(0 , 0.03f , 0 , 0 , 0 , -1000);
//        builder.setColor(Ansi.GREEN);
//        builder.line(0 , 0.01f , 0 , 0 , 1000 , 0);
//        builder.line(0 , 0.01f , 0 , 0 , -1000 , 0);
        Model axesModel = modelBuilder.end();
        ModelInstance axesInstance = new ModelInstance(axesModel);

        return axesInstance;
    }

    public static ModelInstance createRayModelInstance(Vector3 origin , Vector3 direction , float length , Color color) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        Material material = new Material(PBRColorAttribute.createDiffuse(color));
        MeshPartBuilder builder = modelBuilder.part("ray" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked , material);
        builder.setColor(color);

        builder.line(origin , direction.scl(length).add(origin));
        Model model = modelBuilder.end();
        return new ModelInstance(model);
    }

    public static ModelInstance createBoundingBoxRenderable(BoundingBox boundingBox) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder builder = modelBuilder.part("BoundingBox" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorUnpacked , new Material());
        builder.setColor(Color.RED);
        BoxShapeBuilder.build(builder , boundingBox);
        Model boundingBoxModel = modelBuilder.end();

        return new ModelInstance(boundingBoxModel);
    }

    public static ModelInstance createFloor(float width , float height , float depth) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        Material mat = new Material();
        MaterialUtils.addAlphaAttribute(mat , 1f);
        MaterialUtils.addBaseColorAttribute(mat , Color.WHITE);

        MeshPartBuilder meshBuilder = modelBuilder.part("floor" , GL20.GL_TRIANGLES , VertexAttribute.Position().usage | VertexAttribute.Normal().usage | VertexAttribute.TexCoords(0).usage , mat);

        BoxShapeBuilder.build(meshBuilder , width , height , depth);
        Model floor = modelBuilder.end();

        ModelInstance floorInstance = new ModelInstance(floor);
        floorInstance.transform.trn(0 , -0.5f , 0f);

        return floorInstance;
    }

    public static ModelInstance createPlane(int width , int height , int depth) {
        Material mat = new Material();
        MaterialUtils.addAlphaAttribute(mat , 0.2f);
        MaterialUtils.addBaseColorAttribute(mat , Color.LIGHT_GRAY);
        mat.set(new IntAttribute(IntAttribute.CullFace , Gdx.gl.GL_FRONT));

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        BoundingBox boundingBox = new BoundingBox();
        boundingBox.set(new Vector3(-width / 2 , -height / 2 , -depth / 2) , new Vector3(width / 2 , height / 2 , depth / 2));
        MeshPartBuilder meshBuilder = modelBuilder.part("plane" , GL20.GL_TRIANGLES , VertexAttribute.Position().usage | VertexAttribute.Normal().usage | VertexAttribute.TexCoords(0).usage , mat);
        BoxShapeBuilder.build(meshBuilder , boundingBox);

        Model plane = modelBuilder.end();
        ModelInstance planeInstance = new ModelInstance(plane);

        return planeInstance;

    }

    public static Model buildCompassModel() {
        float compassScale = 1;
        ModelBuilder modelBuilder = new ModelBuilder();
        Model arrow = modelBuilder.createArrow(Vector3.Zero,
                Vector3.Y.cpy().scl(compassScale), null,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelBuilder.begin();

        Mesh zArrow = arrow.meshes.first().copy(false);
        zArrow.transform(new Matrix4().rotate(Vector3.X, 90));
        modelBuilder.part("x", zArrow, GL20.GL_TRIANGLES,
                new Material(ColorAttribute.createDiffuse(Color.BLUE)));

        modelBuilder.node();
        Mesh yArrow = arrow.meshes.first().copy(false);
        modelBuilder.part("y", yArrow, GL20.GL_TRIANGLES,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)));

        modelBuilder.node();
        Mesh xArrow = arrow.meshes.first().copy(false);
        xArrow.transform(new Matrix4().rotate(Vector3.Z, -90));
        modelBuilder.part("z", xArrow, GL20.GL_TRIANGLES,
                new Material(ColorAttribute.createDiffuse(Color.RED)));

        arrow.dispose();
        return modelBuilder.end();
    }

    public static Model buildBillboardModel(Texture texture, float width, float height) {
        TextureRegion textureRegion = new TextureRegion(texture, texture.getWidth(), texture.getHeight());
        Material material = new Material();
        material.set(new TextureAttribute(TextureAttribute.Diffuse, textureRegion));
        material.set(new ColorAttribute(ColorAttribute.AmbientLight, Color.WHITE));
        material.set(new BlendingAttribute());
        return buildPlaneModel(width, height, material, 0, 0, 1, 1);
    }

    public static Model buildPlaneModel(final float width,
                                        final float height, final Material material, final float u1,
                                        final float v1, final float u2, final float v2) {

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder bPartBuilder = modelBuilder.part("rect", GL20.GL_TRIANGLES,
                VertexAttributes.Usage.Position
                        | VertexAttributes.Usage.Normal
                        | VertexAttributes.Usage.TextureCoordinates, material);
        bPartBuilder.setUVRange(u1, v1, u2, v2);
        bPartBuilder.rect(-(width * 0.5f), -(height * 0.5f), 0, (width * 0.5f),
                -(height * 0.5f), 0, (width * 0.5f), (height * 0.5f), 0,
                -(width * 0.5f), (height * 0.5f), 0, 0, 0, -1);

        return (modelBuilder.end());
    }


    public static void animateModelInstancePosition(ModelInstance modelInstance , Vector3 start , Vector3 end , float alpha) {
        modelInstance.transform.translate(lerp(start , end , alpha));
    }
    public static Vector3 lerp(Vector3 start , Vector3 end , float alpha) {
        return start.cpy().lerp(end , alpha);
    }

    public static Model createArrowStub(Material mat , Vector3 from , Vector3 to) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder meshBuilder;
        // line
        meshBuilder = modelBuilder.part("line" , GL20.GL_LINES , VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates |VertexAttributes.Usage.Normal, mat);
        meshBuilder.line(from.x , from.y , from.z , to.x , to.y , to.z);
        //rectangular prism
        Node node1 = modelBuilder.node();
//        node1.translation.set(to.cpy().sub(from).scl(0.5f).add(from));
        node1.translation.set(from.cpy().add(to).scl(0.5f));
        meshBuilder = modelBuilder.part("rectangularPrism" , GL20.GL_TRIANGLES , VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates |VertexAttributes.Usage.Normal, mat);
        BoxShapeBuilder.build(meshBuilder , 0.08f/3+to.x , 0.08f/3+to.y , 0.08f/3+to.z);


        // stub
        Node node2 = modelBuilder.node();
        node2.translation.set(to.x , to.y , to.z);
        meshBuilder = modelBuilder.part("stub" , GL20.GL_TRIANGLES , VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal , mat);
        BoxShapeBuilder.build(meshBuilder , 0.05f , 0.05f , 0.05f);
        return modelBuilder.end();
    }

    public static void createOutlineModel(Model model , Color outlineColor , float fattenAmount) {
        fatten(model , fattenAmount);
        for (Material m : model.materials) {
            m.clear();

            m.set(new IntAttribute(IntAttribute.CullFace , Gdx.gl.GL_FRONT));
            m.set(PBRColorAttribute.createBaseColorFactor(outlineColor));
            m.set(new BlendingAttribute(GL20.GL_SRC_ALPHA , GL20.GL_ONE_MINUS_SRC_ALPHA));
            m.set(new BlendingAttribute(0.5f));
        }
        if (!Gdx.gl.glIsEnabled(GL20.GL_BLEND)) {
            Gdx.gl.glEnable(GL20.GL_BLEND);

        }
    }
    public static Model createOutlineModelAsCopy(Model model,Color outlineColor,float fattenAmount){
        fatten(model,fattenAmount);
        Model copy = model;
        for(Material m : copy.materials){
            m.clear();
            m.set(new IntAttribute(IntAttribute.CullFace, Gdx.gl.GL_FRONT));
            m.set(PBRColorAttribute.createBaseColorFactor(outlineColor));
            m.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
            m.set(new BlendingAttribute(0.5f));
        }
        return copy;
    }

    public static void createAlphaAttribute(Model model,float alpha){
        for (Material m : model.materials) {
            m.clear();
            m.set(new IntAttribute(IntAttribute.CullFace, Gdx.gl.GL_FRONT));
            m.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
            m.set(new BlendingAttribute(alpha));
            m.set(PBRColorAttribute.createBaseColorFactor(Color.WHITE));

        }

        if (!Gdx.gl.glIsEnabled(GL20.GL_BLEND)) {
            Gdx.gl.glEnable(GL20.GL_BLEND);

        }
    }

    public static void fatten(Model model , float amount) {
        Vector3 pos = new Vector3();
        Vector3 nor = new Vector3();
        for (Node node : model.nodes) {
            for (NodePart n : node.parts) {
                Mesh mesh = n.meshPart.mesh;
                FloatBuffer buf = mesh.getVerticesBuffer();
                int lastFloat = mesh.getNumVertices() * mesh.getVertexSize() / 4;
                int vertexFloats = ( mesh.getVertexSize() / 4 );
                VertexAttribute posAttr = mesh.getVertexAttributes().findByUsage(VertexAttributes.Usage.Position);
                VertexAttribute norAttr = mesh.getVertexAttributes().findByUsage(VertexAttributes.Usage.Normal);
                if (posAttr == null || norAttr == null) {
                    throw new IllegalArgumentException("Position/normal vertex attribute not found");
                }
                int pOff = posAttr.offset / 4;
                int nOff = norAttr.offset / 4;

                for (int i = 0; i < lastFloat; i += vertexFloats) {
                    pos.x = buf.get(pOff + i);
                    pos.y = buf.get(pOff + i + 1);
                    pos.z = buf.get(pOff + i + 2);

                    nor.x = buf.get(nOff + i);
                    nor.y = buf.get(nOff + i + 1);
                    nor.z = buf.get(nOff + i + 2);

                    nor.nor().scl(amount);

                    buf.put(pOff + i , pos.x + nor.x);
                    buf.put(pOff + i + 1 , pos.y + nor.y);
                    buf.put(pOff + i + 2 , pos.z + nor.z);
                }
            }
        }
    }

    public static List<String> getAnimationNames(ModelInstance gameModel) {
        List<String> animationNames = new ArrayList<>();
        for (Animation animation : gameModel.animations) {
            animationNames.add(animation.id);
        }
        if (animationNames.isEmpty()) {
            animationNames.add("No animations");
        }
        return animationNames;
    }

    public static Model createCylinder(float width, float height, float depth) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        Color color = getRandomColor();
        Material material = new Material(PBRColorAttribute.createDiffuse(color));
        material.set(PBRColorAttribute.createSpecular(color));
        material.set(PBRColorAttribute.createBaseColorFactor(color));
        material.set(PBRColorAttribute.createEmissive(color));
        material.set(PBRColorAttribute.createAmbient(color));
        material.set(PBRColorAttribute.createReflection(color));
        MaterialUtils.addAlphaAttribute(material,1);
        MeshPartBuilder meshBuilder = modelBuilder.part("cylinder" , GL20.GL_TRIANGLES ,  VertexAttribute.Position().usage | VertexAttribute.TexCoords(0).usage | VertexAttribute.Normal().usage, material);
        CylinderShapeBuilder.build(meshBuilder , width , height , depth , 10);
        return modelBuilder.end();

    }

    public Model createBox(int width , int height , int depth) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        Color color = getRandomColor();
        Material material = MaterialUtils.createGenericBDSFMaterial(color);
        material.set(new ColorAttribute(PBRColorAttribute.createBaseColorFactor(color)));
        MaterialUtils.addAlphaAttribute(material,1);
        MeshPartBuilder meshBuilder = modelBuilder.part("box" , GL20.GL_TRIANGLES , VertexAttribute.Position().usage | VertexAttribute.Normal().usage | VertexAttribute.TexCoords(0).usage , material);

        BoxShapeBuilder.build(meshBuilder , width , height , depth);

        return modelBuilder.end();
    }

    public static Model createSphere(float radius){
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        Color color = ColorUtils.rand();
        Material material = new Material(PBRColorAttribute.createDiffuse(color));
        material.set(PBRColorAttribute.createSpecular(color));
        material.set(PBRColorAttribute.createBaseColorFactor(color));
        material.set(PBRColorAttribute.createEmissive(color));
        material.set(PBRColorAttribute.createAmbient(color));
        material.set(PBRColorAttribute.createReflection(color));
        material.set(PBRFloatAttribute.createMetallic(100f));
        material.set(PBRFloatAttribute.createShininess(100f));
        material.set(PBRFloatAttribute.createRoughness(100));
        material.set(PBRFloatAttribute.createEmissiveIntensity(1f));
        material.set(PBRFloatAttribute.createNormalScale(.2f));
        MaterialUtils.addAlphaAttribute(material,1);
        MaterialUtils.addAlphaAttribute(material,1);
        MeshPartBuilder meshBuilder = modelBuilder.part("box" , GL20.GL_TRIANGLES , attributes , material);
        SphereShapeBuilder.build(meshBuilder , radius , radius , radius , 15 , 15);
        Model model = modelBuilder.end();

        return model;

    }

    public static Color getRandomColor(){
        Color color = new Color();
        color.r = MathUtils.random();
        color.g = MathUtils.random();
        color.b = MathUtils.random();
        int redGreenOrBlueBias = MathUtils.random(0,2);
        if(redGreenOrBlueBias == 0){
            color.r = 1;

        }
        else if(redGreenOrBlueBias == 1){
            color.g = 1;
        }
        else if(redGreenOrBlueBias == 2){
            color.b = 1;
        }

        int lightOrDarkBias = MathUtils.random(0,1);
        if (lightOrDarkBias==0){
            color.r = color.r/2;
            color.g = color.g/2;
            color.b = color.b/2;
        }



        color.a = 1f;

        return color;
    }

    public static Model createCube(float size){
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        Color color = getRandomColor();
        Material material = new Material(PBRColorAttribute.createDiffuse(color));

//        material.set(TextureAttribute.createSpecular(new Texture("images/normal_map.png")));
//        material.set(TextureAttribute.createNormal(new Texture("images/normal_map.png")));
//        material.set(TextureAttribute.createEmissive(new Texture("images/normal_map.png")));

        VertexAttributes attributes = new VertexAttributes(
                VertexAttribute.Position(),
                VertexAttribute.Normal());



        MaterialUtils.addAlphaAttribute(material,1);
        MeshPartBuilder meshBuilder = modelBuilder.part("box" , GL20.GL_TRIANGLES , attributes, material);
        BoxShapeBuilder.build(meshBuilder , size , size , size);
        Model model = modelBuilder.end();


        return model;
    }

    public static Model createCone(float width , float height,float depth){
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        Color color = getRandomColor();
        Material material = new Material(PBRColorAttribute.createDiffuse(color));
        material.set(PBRColorAttribute.createSpecular(color));
        material.set(PBRColorAttribute.createBaseColorFactor(color));
        material.set(PBRColorAttribute.createEmissive(color));
        material.set(PBRColorAttribute.createAmbient(color));
        material.set(PBRColorAttribute.createReflection(color));
        MaterialUtils.addAlphaAttribute(material,1f);
        MeshPartBuilder meshBuilder = modelBuilder.part("box" , GL20.GL_TRIANGLES , VertexAttribute.Position().usage | VertexAttribute.Normal().usage | VertexAttribute.TexCoords(0).usage , material);
        ConeShapeBuilder.build(meshBuilder , width , height , depth,40);
        return modelBuilder.end();
    }

    public static Model createCapsule(float radius , float height){
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        Color color = getRandomColor();
        Material material = new Material(PBRColorAttribute.createDiffuse(color));
        material.set(PBRColorAttribute.createSpecular(color));
        material.set(PBRColorAttribute.createBaseColorFactor(color));
        material.set(PBRColorAttribute.createEmissive(color));
        material.set(PBRColorAttribute.createAmbient(color));
        material.set(PBRColorAttribute.createReflection(color));
        MaterialUtils.addAlphaAttribute(material,1f);
        MeshPartBuilder meshBuilder = modelBuilder.part("box" , GL20.GL_TRIANGLES , VertexAttribute.Position().usage | VertexAttribute.Normal().usage | VertexAttribute.TexCoords(0).usage , material);
        //ensure height is at least 2x radius
        height = Math.max(height, 2 * radius);

        CapsuleShapeBuilder.build(meshBuilder , radius , height , 40);
        return modelBuilder.end();
    }

    public enum Axis {
        X, Y, Z, XYZ
    }



    public static Model torus(Material mat, float width, float height, int divisionsU, int divisionsV) {

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder builder = modelBuilder.part("torus", GL20.GL_TRIANGLES, VertexAttribute.Position().usage | VertexAttribute.Normal().usage | VertexAttribute.ColorUnpacked().usage, mat);
        // builder.setColor(Ansi.LIGHT_GRAY);

        MeshPartBuilder.VertexInfo curr1 = v0.set(null, null, null, null);
        curr1.hasUV = curr1.hasNormal = false;
        curr1.hasPosition = true;

        MeshPartBuilder.VertexInfo curr2 = v1.set(null, null, null, null);
        curr2.hasUV = curr2.hasNormal = false;
        curr2.hasPosition = true;
        short i1, i2, i3 = 0, i4 = 0;

        int i, j, k;
        double s, t, twopi;
        twopi = 2 * Math.PI;

        for (i = 0; i < divisionsV; i++) {
            for (j = 0; j <= divisionsU; j++) {
                for (k = 1; k >= 0; k--) {
                    s = (i + k) % divisionsV + 0.5;
                    t = j % divisionsU;

                    curr1.position.set(
                            (float) ((width + height * Math.cos(s * twopi / divisionsV))
                                    * Math.cos(t * twopi / divisionsU)),
                            (float) ((width + height * Math.cos(s * twopi / divisionsV))
                                    * Math.sin(t * twopi / divisionsU)),
                            (float) (height * Math.sin(s * twopi / divisionsV)));
                    k--;
                    s = (i + k) % divisionsV + 0.5;
                    curr2.position.set(
                            (float) ((width + height * Math.cos(s * twopi / divisionsV))
                                    * Math.cos(t * twopi / divisionsU)),
                            (float) ((width + height * Math.cos(s * twopi / divisionsV))
                                    * Math.sin(t * twopi / divisionsU)),
                            (float) (height * Math.sin(s * twopi / divisionsV)));
                    // curr2.uv.set((float) s, 0);
                    i1 = builder.vertex(curr1);
                    i2 = builder.vertex(curr2);
//                    builder.rect(i4, i2, i1, i3);
                    //two triangles
                    builder.triangle(i4, i2, i1);
                    builder.triangle(i1, i3, i4);

                    i4 = i2;
                    i3 = i1;
                }
            }
        }

        return modelBuilder.end();
    }
}
