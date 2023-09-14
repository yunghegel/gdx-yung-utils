package org.yunghegel.gdx.utils.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTable;
import org.yunghegel.gdx.utils.ui.widgets.HorizontalTable;
import org.yunghegel.gdx.utils.ui.widgets.YungSlider;
import org.yunghegel.gdx.utils.ui.widgets.YungTextField;

public class FloatUI extends HorizontalTable
{
    private Slider slider;
    public float width=50;
    YungTextField tf;
    public FloatUI(Skin skin, final float value, String name, float min, float max,FloatUIListener listener) {
        setSkin(skin);
        slider = new YungSlider(min, max, .01f, false,skin);
        align(Align.left);

        add(name).padRight(10).minWidth(50).align(Align.left);
        tf = new YungTextField(String.valueOf(value),(s)->{

        },true);

        tf.setReadOnly(true);
        add(tf).padRight(10);

        slider.setValue(value);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.onChange(slider.getValue());
                String formatted = String.format("%.2f", slider.getValue());
                tf.setText(formatted);
            }
        });
        add(slider).fillX().expandX();
    }





    public float getValue(){
        return slider.getValue();
    }

    interface FloatUIListener{
        void onChange(float value);
    }
}