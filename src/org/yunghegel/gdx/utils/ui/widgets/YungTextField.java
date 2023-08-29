package org.yunghegel.gdx.utils.ui.widgets;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.FocusManager;
import com.kotcrab.vis.ui.util.FloatDigitsOnlyFilter;
import com.kotcrab.vis.ui.util.IntDigitsOnlyFilter;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
import org.yunghegel.gdx.utils.UIUtil;

public class YungTextField extends VisValidatableTextField {

    float prefWidth = 40;
    float prefHeight = 16;


    FloatDigitsOnlyFilter filter = new FloatDigitsOnlyFilter(true);
    IntDigitsOnlyFilter intFilter = new IntDigitsOnlyFilter(true);






    public YungTextField(String text,TextFieldListener listener,boolean readOnly) {
        super(text);
        setTextFieldFilter(filter);
        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.onChange(getText());
            }
        });
        setReadOnly(readOnly);
    }

    @Override
    protected void initialize() {

        if(isReadOnly()){
            setDisabled(true);
            getCaptureListeners().forEach((l)->removeCaptureListener(l));
            getListeners().forEach((l)->removeListener(l));
        } else {
            super.initialize();
        }

    }

    @Override
    public float getPrefWidth() {
        return prefWidth;
    }

    @Override
    public float getPrefHeight() {
        return getStyle().font.getCapHeight()+5;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);

        getCaptureListeners().forEach((l)->removeCaptureListener(l));
        getListeners().forEach((l)->removeListener(l));

    }

    public void setPrefWidth(float prefWidth) {
        this.prefWidth = prefWidth;
    }

    public void setPrefHeight(float prefHeight) {
        this.prefHeight = prefHeight;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    public interface TextFieldListener {
        void onChange(String text);
    }




}
