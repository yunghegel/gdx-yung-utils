package org.yunghegel.gdx.utils.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class OutlineShader {

    public ShaderProgram outlineShader;
    private FrameBufferMultisample depthFbo;
    private SpriteBatch spriteBatch;
    private boolean distanceFalloff = true;
    private ModelBatch modelBatch;

    private float outlineWidth = 0f;

    private float outlineDepthMin=0.35f;
    private float outlineDepthMax=0.9f;
    private Color innerColor = new Color(.2f, .2f, .2f, .3f);
    private Color outerColor = new Color(0, 0, 0, .7f);

    private float outlineDistanceFalloff=1f;


    public OutlineShader(boolean distanceFalloff) {
        this.distanceFalloff = distanceFalloff;
        init();

    }

    public void setOutlineWidth(float outlineWidth) {
        this.outlineWidth = outlineWidth;
    }

    public void setOutlineDepthMin(float outlineDepthMin) {
        this.outlineDepthMin = outlineDepthMin;
    }

    public void setOutlineDepthMax(float outlineDepthMax) {
        this.outlineDepthMax = outlineDepthMax;
    }

    public void setInnerColor(Color innerColor) {
        this.innerColor = innerColor;
    }

    public void setOuterColor(Color outerColor) {
        this.outerColor = outerColor;
    }

    public void setOutlineDistanceFalloff(float outlineDistanceFalloff) {
        this.outlineDistanceFalloff = outlineDistanceFalloff;
    }



    void init(){
        modelBatch = new ModelBatch(new DepthShaderProvider());
        spriteBatch = new SpriteBatch();
        String prefix = "";
        if(distanceFalloff) {
            prefix += "#define DISTANCE_FALLOFF\n";
        }

        outlineShader = new ShaderProgram(Gdx.files.internal("glsl/outline.vs.glsl").readString(),
                prefix+Gdx.files.internal("glsl/outline.fs.glsl").readString());
        if(!outlineShader.isCompiled()) throw new GdxRuntimeException("Outline Shader failed: " + outlineShader.getLog());
    }

    private FrameBufferMultisample ensureFBO(FrameBufferMultisample fbo, boolean hasDepth) {
        int w = Gdx.graphics.getBackBufferWidth();
        int h = Gdx.graphics.getBackBufferHeight();
        if(fbo == null || fbo.getWidth() != w || fbo.getHeight() != h){
            if(fbo != null) fbo.dispose();
            fbo = FrameBufferUtils.createMultisample(GLFormat.RGBA8,w,h,true,8);
        }
        return fbo;
    }

    protected void captureDepth(Array<RenderableProvider> renderables, Camera camera) {
        depthFbo = ensureFBO(depthFbo, true);
        depthFbo.begin();
        Gdx.gl.glClearColor(.2f, .2f, .2f, 0.0f);
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
        modelBatch.begin(camera);
        modelBatch.render(renderables);
        modelBatch.end();
        depthFbo.end();
    }

    public void renderOutline(Array<RenderableProvider> renderables, Camera camera) {
        captureDepth(renderables, camera);
        outlineShader.bind();

        float size =  outlineWidth;
        float depthMin = (float) Math.pow(outlineDepthMin, 10);
        float depthMax = (float) Math.pow(outlineDepthMax, 10);

        outlineShader.setUniformf("u_size", Gdx.graphics.getWidth() * size, Gdx.graphics.getHeight() * size);
        outlineShader.setUniformf("u_depth_min", depthMin);
        outlineShader.setUniformf("u_depth_max", depthMax);
        outlineShader.setUniformf("u_inner_color", innerColor);
        outlineShader.setUniformf("u_outer_color", outerColor);

        if(distanceFalloff){

            float distanceFalloff = outlineDistanceFalloff;
            if(distanceFalloff <= 0){
                distanceFalloff = .001f;
            }
            outlineShader.setUniformf("u_depthRange", camera.far / (camera.near * distanceFalloff));
        }


        spriteBatch.enableBlending();
        spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, 1, 1);
        spriteBatch.setShader(outlineShader);
        spriteBatch.begin();
        spriteBatch.draw(depthFbo.getColorBufferTexture(), 0, 0, 1, 1, 0f, 0f, 1f, 1f);
        spriteBatch.end();
        spriteBatch.setShader(null);



    }



}
