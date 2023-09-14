package org.yunghegel.gdx.utils.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.yunghegel.gdx.utils.graphics.CameraController;

public class ViewportWidget extends Widget {

    public Viewport viewport;
    public Stage stage;

    private final static Vector2 temp = new Vector2();

    public interface Renderer {
        void render(float delta);
    }

    public Renderer renderer;

    public ViewportWidget(Viewport viewport) {
        this.viewport = viewport;

    }

    public ViewportWidget(Viewport viewport, Renderer renderer, InputMultiplexer inputs, CameraController camController,Stage stage) {
        this.viewport = viewport;
        this.renderer = renderer;
        this.stage = stage;
        addCaptureListener(new InputListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.input.setInputProcessor(inputs);
                inputs.addProcessor(camController);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                inputs.removeProcessor(camController);


            }
        });
    }

    @Override
    public void act(float delta) {
        temp.set(0, 0);
        localToScreenCoordinates(temp);
        viewport.setScreenPosition(viewportOriginalX + MathUtils.round(temp.x), viewportOriginalY + MathUtils.round(Gdx.graphics.getHeight() - temp.y));
        render(delta);
        stage.getViewport().apply();
    }

    int viewportOriginalX, viewportOriginalY;


    public void render(float delta) {
        if (renderer != null) {
            viewport.apply();
            renderer.render(delta);
        }
    }

    @Override
    public void layout() {
        temp.set(0, 0);
        localToScreenCoordinates(temp);
        viewport.update(MathUtils.round(getWidth()), MathUtils.round(getHeight()));
        viewportOriginalX = viewport.getScreenX();
        viewportOriginalY = viewport.getScreenY();
    }
}