package org.yunghegel.gdx.utils.graphics.instancing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.ArrowShapeBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.CapsuleShapeBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.SphereShapeBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.*;
import org.lwjgl.opengl.GL11;
import org.yunghegel.gdx.utils.MathUtils;
import org.yunghegel.gdx.utils.graphics.model.MeshUtils;
import org.yunghegel.gdx.utils.graphics.model.Primitive;
import org.yunghegel.gdx.utils.graphics.model.PrimitiveSupplier;
import org.yunghegel.gdx.utils.graphics.model.suppliers.BoxSupplier;
import org.yunghegel.gdx.utils.graphics.model.suppliers.ConeSupplier;
import org.yunghegel.gdx.utils.shaders.InstancedShader;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Arrays;

public class InstancedModel {

    public ModelInstance model;
    public int numInstances;
    private Renderable renderable;

    private Quaternion q;
    private Matrix4 mat4;
    private Vector3 vec3Temp;
    private float[] floatTemp=new float[16];
    private Mesh mesh;
    FloatBuffer offsets;
    private Texture texture;
    private ModelBatch batch;
    private long startTime, updateTime, renderTime;
    Environment environment;
    Camera camera;
    ShaderProgram shaderProgram;

    private int count;
    private static float CULLING_FACTOR;  // distance from camera


    private int targetIndex, instanceUpdated;
    private ShaderProgram shader;
    private InstanceDataSupplier instanceDataSupplier;
    private final static int INSTANCE_COUNT_SQRT = 16;
    private final static int INSTANCE_COUNT = INSTANCE_COUNT_SQRT * INSTANCE_COUNT_SQRT*INSTANCE_COUNT_SQRT;
    public InstancedModel(Camera camera) {
        q=new Quaternion();
        mat4=new Matrix4();
        vec3Temp=new Vector3();
        texture = new Texture(Gdx.files.internal("grass.png")); // our mascot!
//        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
//        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        batch = new ModelBatch();
        this.camera = camera;


        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.9f, 0.9f, 0.9f, 1f));
//            this.model = model;
        init();
        String vertexShader = "attribute vec4 a_position;    \n" + "attribute vec4 a_color;\n" + "attribute vec2 a_texCoord0;\n"
                + "uniform mat4 u_worldView;\n" + "varying vec4 v_color;" + "varying vec2 v_texCoords;"
                + "void main()                  \n" + "{                            \n" + "   v_color = vec4(1, 1, 1, 1); \n"
                + "   v_texCoords = a_texCoord0; \n" + "   gl_Position =  u_worldView * a_position;  \n"
                + "}                            \n";
        String fragmentShader = "#ifdef GL_ES\n" + "precision mediump float;\n" + "#endif\n" + "varying vec4 v_color;\n"
                + "varying vec2 v_texCoords;\n" + "uniform sampler2D u_texture;\n" + "void main()                                  \n"
                + "{                                            \n" + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n"
                + "}";

        shader = new ShaderProgram(vertexShader, fragmentShader);
        if (shader.isCompiled() == false) {
            Gdx.app.log("ShaderTest", shader.getLog());
            Gdx.app.exit();
        }

    }

    private BaseShader createShader() {
        return new BaseShader() {

            @Override
            public void begin(Camera camera, RenderContext context) {

                program.bind();
                context.setCullFace(GL30.GL_NONE);

//                if(camera!=null)
                    program.setUniformMatrix("u_projViewTrans", camera.combined);
                program.setUniformi("u_texture", 0);
//                context.setDepthTest(GL30.GL_LEQUAL);
                Gdx.gl.glCullFace(GL30.GL_NONE);
            }

            @Override
            public void render(Renderable renderable) {
                if(camera!=null)
                    program.setUniformMatrix("u_projViewTrans", camera.combined);

                super.render(renderable);
            }

            @Override
            public void init () {
                ShaderProgram.prependVertexCode = "#version 300 es\n";
                ShaderProgram.prependFragmentCode = "#version 300 es\n";
                program = new ShaderProgram(Gdx.files.internal("instanced.vert"),
                        Gdx.files.internal("instanced.frag"));
                if (!program.isCompiled()) {
                    throw new GdxRuntimeException("Shader compile error: " + program.getLog());
                }
                init(program, renderable);
            }

            @Override
            public int compareTo (Shader other) {
                return 0;
            }

            @Override
            public boolean canRender (Renderable instance) {
                return true;
            }
        };
    }
    ModelInstance instance;
    private void createInstancedMesh(){
        if (Gdx.gl30 == null) {
            throw new GdxRuntimeException("GLES 3.0 profile required for this test");
        }
//
//        ModelBuilder modelBuilder = new ModelBuilder();
//        modelBuilder.begin();
//        MeshPartBuilder builder = modelBuilder.part("instances", GL30.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates, new Material());
//        SphereShapeBuilder.build(builder, 0.25f,0.25f,0.25f,20,20);
//        Model model = modelBuilder.end();

        instance = new ConeSupplier(0.25f, 0.5f,0.25f,10).build();
        Model model = instance.model;
//        int vertexCount = model.meshParts.get(0).mesh.getNumVertices();
//        int vertexSize = model.meshParts.get(0).mesh.getVertexSize() / 4;
//        float[] vertices = new float[vertexSize*vertexCount];
//        int numIndices = model.meshParts.get(0).mesh.getNumIndices();
////        short[] indices = new short[numIndices];
//
//        model.meshParts.get(0).mesh.getVertices(vertices);
//        model.meshParts.get(0).mesh.getIndices(indices);
//
//        System.out.println("vertexSize: " + vertexSize);
//        System.out.println("vertexCount: " + vertexCount);
//        System.out.println("numIndices: " + numIndices);
//
//        model.meshParts.get(0).mesh.getVertices(vertices);
//        model.meshParts.get(0).mesh.getIndices(indices);
//
//        System.out.println(model.meshes.first().getVertexAttributes());

        float size = 0.25f;


        mesh = new Mesh(true, 24, 36,
                new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_position"),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0")
        );

        mesh.enableInstancedRendering(true, INSTANCE_COUNT,
                new VertexAttribute(VertexAttributes.Usage.Generic, 4, "i_worldTrans", 0),
                new VertexAttribute(VertexAttributes.Usage.Generic, 4, "i_worldTrans", 1),
                new VertexAttribute(VertexAttributes.Usage.Generic, 4, "i_worldTrans", 2),
                new VertexAttribute(VertexAttributes.Usage.Generic, 4, "i_worldTrans", 3));
        float[] vertices = new float[] {
                -size, size, -size, 0.0f, 1.0f,
                size, size, -size, 1.0f, 1.0f,
                size, -size, -size, 1.0f, 0.0f,
                -size, -size, -size, 0.0f, 0.0f,
                size, size, size, 1.0f, 1.0f,
                -size, size, size, 0.0f, 1.0f,
                -size, -size, size, 0.0f, 0.0f,
                size, -size, size, 1.0f, 0.0f,
                -size, size, size, 1.0f, 1.0f,
                -size, size, -size, 0.0f, 1.0f,
                -size, -size, -size, 0.0f, 0.0f,
                -size, -size, size, 1.0f, 0.0f,
                size, size, -size, 1.0f, 1.0f,
                size, size, size, 0.0f, 1.0f,
                size, -size, size, 0.0f, 0.0f,
                size, -size, -size, 1.0f, 0.0f,
                -size, size, size, 1.0f, 1.0f,
                size, size, size, 0.0f, 1.0f,
                size, size, -size, 0.0f, 0.0f,
                -size, size, -size, 1.0f, 0.0f,
                -size, -size, -size, 1.0f, 1.0f,
                size, -size, -size, 0.0f, 1.0f,
                size, -size, size, 0.0f, 0.0f,
                -size, -size, size, 1.0f, 0.0f
        };

        // 36 indices
        short[] indices = new short[]
                {0, 1, 2, 2, 3, 0, 4, 5, 6, 6, 7, 4, 8, 9, 10, 10, 11, 8, 12, 13,
                        14, 14, 15, 12, 16, 17, 18, 18, 19, 16, 20, 21, 22, 22, 23, 20 };

        mesh.setVertices(vertices);
        mesh.setIndices(indices);



        System.out.println(Arrays.toString(vertices));
        System.out.println(Arrays.toString(indices));

        offsets = BufferUtils.newFloatBuffer(INSTANCE_COUNT * 16);
        ((Buffer)offsets).position(0);
        perspectiveCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        perspectiveCamera.position.set(0f, 0f, 10f);
        perspectiveCamera.lookAt(0, 0, 0);
        perspectiveCamera.near = 1f;
        perspectiveCamera.far = 300f;

    }

    private void createInstanceData(){
        for (int x = (-INSTANCE_COUNT_SQRT/2)+1; x <= INSTANCE_COUNT_SQRT/2; x++) {
            for (int y = 0; y < INSTANCE_COUNT_SQRT ; y++) {
                for (int z = -INSTANCE_COUNT_SQRT/2; z < INSTANCE_COUNT_SQRT/2 ; z++) {
                    vec3Temp.set(
                            x/10f,
                            y/10f ,
                            z/10f );

                    // set random rotation
                    q.setEulerAngles(MathUtils.random(-90, 90), MathUtils.random(-90, 90), MathUtils.random(-90, 90));

                    // create matrix transform
                    mat4.set(vec3Temp, q);
//                    mat4.setToLookAt(vec3Temp    , camera.direction, Vector3.Y);
//                    mat4.getRotation(q);
//                    mat4.setTranslation(vec3Temp);
//                    mat4.rotate(q);

                    // put the 16 floats for mat4 in the float buffer
                    offsets.put(mat4.getValues());
                }
            }

        }

        ((Buffer)offsets).position(0);
        mesh.setInstanceData(offsets);
    }

    private void createRenderable(){
        renderable = new Renderable();
        renderable.material = new Material();
        renderable.meshPart.set("quad instanced", mesh, 0, 36, GL20.GL_TRIANGLES);
        renderable.worldTransform.idt();
        renderable.environment=environment;
        renderable.shader = createShader();

        renderable.shader.init();
    }

    PerspectiveCamera perspectiveCamera=new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


    public void render (float delta, Camera camera) {
//        for (int x = 0; x < INSTANCE_COUNT; x++) {
//            targetIndex = x * 16; // each instance uses 16 floats for matrix4
//
//            // get position of instance (x, y, z)
//            vec3Temp.set(offsets.get(targetIndex + 12), offsets.get(targetIndex + 13), offsets.get(targetIndex + 14));
//
//            // Attempt culling if not within camera's frustum, or too far away to be noticed rotating
//
//            instanceUpdated++;
//
//            // TODO: maybe we can use the other get() methods?
//            // Get the maxtrix4
//            floatTemp[0] = offsets.get(targetIndex);
//            floatTemp[1] = offsets.get(targetIndex + 1);
//            floatTemp[2] = offsets.get(targetIndex + 2);
//            floatTemp[3] = offsets.get(targetIndex + 3);
//            floatTemp[4] = offsets.get(targetIndex + 4);
//            floatTemp[5] = offsets.get(targetIndex + 5);
//            floatTemp[6] = offsets.get(targetIndex + 6);
//            floatTemp[7] = offsets.get(targetIndex + 7);
//            floatTemp[8] = offsets.get(targetIndex + 8);
//            floatTemp[9] = offsets.get(targetIndex + 9);
//            floatTemp[10] = offsets.get(targetIndex + 10);
//            floatTemp[11] = offsets.get(targetIndex + 11);
//            floatTemp[12] = vec3Temp.x; // saves time
//            floatTemp[13] = vec3Temp.y; // saves time
//            floatTemp[14] = vec3Temp.z; // saves time
//            floatTemp[15] = offsets.get(targetIndex + 15);
//            mat4.setTranslation(vec3Temp);
//            mat4.setToLookAt(camera.direction, Vector3.Y);
//
//            mat4.setTranslation(vec3Temp);
//
//
//            // spin every other cube differently - use just one for slight speed up
////            if (x % 2 == 0)
////                mat4.rotate(Vector3.X, 45 * delta);
////            else
////                mat4.rotate(Vector3.Y, 45 * delta);
//
//            // update float buffer and update the mesh instance data
//            offsets.position(targetIndex);
//            offsets.put(mat4.getValues());
//            renderable.meshPart.mesh.updateInstanceData(targetIndex, mat4.getValues());
//        }
//        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
        texture.bind();
        if(camera==null){
            System.out.println("camera is null");
        }
        batch.begin(camera);
        batch.render(renderable);
        batch.render(instance);
        batch.end();
    }

    private void init(){
        createInstancedMesh();
        createInstanceData();
        createRenderable();
    }







}
