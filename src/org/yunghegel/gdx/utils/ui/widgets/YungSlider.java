package org.yunghegel.gdx.utils.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

public class YungSlider extends Slider {

    private float prefWidth = 50;
    private float prefHeight = 50;

    public YungSlider(float min, float max, float stepSize, boolean vertical, Skin skin) {
        super(min, max, stepSize, vertical, skin);
    }

    @Override
    public void act(float delta) {
        super.act(delta);


    }

    @Override
    public float getPrefWidth() {
        if(isVertical()) {
            return super.getPrefWidth();
        }
        return super.getPrefWidth();
    }

    @Override
    public float getPrefHeight() {
        if(isVertical()) {
            return prefHeight;
        }
        return super.getPrefHeight();
    }

    public void setPrefWidth(float prefWidth) {
        this.prefWidth = prefWidth;
    }

    public void setPrefHeight(float prefHeight) {
        this.prefHeight = prefHeight;
    }




}
