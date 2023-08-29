package org.yunghegel.gdx.utils.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFormat;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.VisTable;
import org.lwjgl.opengl.GL30;
import org.yunghegel.gdx.gizmo.core.utility.CompassGizmo;
import org.yunghegel.gdx.utils.graphics.FrameBufferUtils;
import org.yunghegel.gdx.utils.graphics.ScreenUtil;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GLCanvas extends Widget {
    public Viewport viewport;
    private final static Vector2 temp = new Vector2();
    public Camera camera;
    public RenderViewport renderViewport;
    public CompassGizmo compassGizmo;
    private FrameBuffer fbo;
    private Texture texture;
    private boolean useFbo = false;
    private boolean renderToTexture = false;
    private SpriteBatch spriteBatch;

    private boolean requestRender = false;

    private boolean initialFrame = false;
    private Stage afterRenderStage;
    private VisTable root;

    ModelBatch batch;
    public GLCanvas(Viewport viewport) {
        this.viewport = viewport;
        this.camera = viewport.getCamera();
        prepareUI();

        root.setFillParent(true);

        batch = new ModelBatch();
        compassGizmo = new CompassGizmo(new InputMultiplexer(),batch,camera,viewport, Input.Keys.C);
        GLFrameBuffer.FrameBufferBuilder frameBufferBuilder = new GLFrameBuffer.FrameBufferBuilder(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        frameBufferBuilder.addBasicColorTextureAttachment(Pixmap.Format.RGBA8888);
        frameBufferBuilder.addDepthRenderBuffer(GL30.GL_DEPTH_COMPONENT32F);
        fbo = frameBufferBuilder.build();
//        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    private void prepareUI() {
        afterRenderStage = new Stage(new ScreenViewport());
        root = new VisTable();
        root.setFillParent(true);
        afterRenderStage.addActor(root);
    }

    public void addStageActor(Actor actor){

    }

   public final Cell addAndConfiguretAfterRenderStage(BiConsumer<Actor, Cell> ac, Actor actor){
        Cell cell = root.add(actor);
        if(ac!=null) ac.accept(root,cell);
        return cell;

    }

    @Override
    public void act(float delta) {
        temp.set(0, 0);
        localToScreenCoordinates(temp);
        viewport.setScreenPosition(viewportOriginalX + MathUtils.round(temp.x), viewportOriginalY + MathUtils.round(Gdx.graphics.getHeight() - temp.y));
    }

    int viewportOriginalX, viewportOriginalY;
    @Override
    public void layout() {
        temp.set(0, 0);
        localToScreenCoordinates(temp);
        viewport.update(MathUtils.round(getWidth()), MathUtils.round(getHeight()));
        viewportOriginalX = viewport.getScreenX();
        viewportOriginalY = viewport.getScreenY();


        BoundingBox box = new BoundingBox();
        compassGizmo.compass.calculateBoundingBox(box);
        Vector2 pos= ScreenUtil.toOpenGLCoords(viewportOriginalX + MathUtils.round(temp.x)+viewport.getScreenWidth(), viewportOriginalY + MathUtils.round(Gdx.graphics.getHeight() - temp.y)+viewport.getScreenHeight());
        compassGizmo.setPosition(pos.x-box.getWidth()-0.01f,pos.y-box.getHeight()-0.01f);

        ScreenUtil.enableScissor();
        ScreenUtil.scissor(viewportOriginalX + MathUtils.round(temp.x), viewportOriginalY + MathUtils.round(Gdx.graphics.getHeight() - temp.y), MathUtils.round(getWidth()), MathUtils.round(getHeight()));
    }

    public void render(float delta,RenderViewport render){



        if(requestRender||!initialFrame){

            if(useFbo){
                fbo = FrameBufferUtils.ensureSize(fbo, Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

                fbo.begin();
                viewport.apply();
                render.render(camera);
                compassGizmo.update();
                compassGizmo.render(batch);
                fbo.end();
                texture = fbo.getColorBufferTexture();

            } else {
                viewport.apply();
                render.render(camera);
                compassGizmo.update();
                compassGizmo.render(batch);
            }


            initialFrame = true;
            requestRender = false;
        }


        if(renderToTexture){
            spriteBatch.begin();
            spriteBatch.draw(getFboTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            spriteBatch.end();
        }
    }

    @FunctionalInterface
    public interface RenderViewport {
        void render(Camera cam);
    }

    public Matrix4 viewingRectangleTransformationMatrix() {
        Matrix4 matrix = new Matrix4();
        float gdxHeight = Gdx.graphics.getHeight();
        float gdxWidth = Gdx.graphics.getWidth();

        float scaleX = gdxWidth / (viewport.getScreenWidth() + MathUtils.round(temp.x));
        float scaleY = gdxHeight / (viewport.getScreenHeight() + MathUtils.round(Gdx.graphics.getHeight() - temp.y));

        return matrix.setToScaling(scaleX, scaleY, 1);
    }

    public void setUseFbo(boolean useFbo) {
        this.useFbo = useFbo;
    }

    public void setRenderToTexture(boolean renderToTexture,SpriteBatch batch) {
        this.renderToTexture = renderToTexture;
        this.spriteBatch = batch;
    }

    public void setRequestRender(boolean requestRender) {
        this.requestRender = requestRender;
    }

    public TextureRegion getFboTexture(){
        if(!useFbo) throw new IllegalStateException("Fbo is not enabled");
        TextureRegion region = new TextureRegion(texture);
        region.flip(false,true);
        return region;
    }

}
