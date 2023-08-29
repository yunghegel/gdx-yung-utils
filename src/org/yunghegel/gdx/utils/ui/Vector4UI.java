package org.yunghegel.gdx.utils.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisTable;
import org.yunghegel.gdx.utils.ui.widgets.VerticalTable;

public class Vector4UI extends VerticalTable
{
    private Array<Slider> sliders = new Array<Slider>();
    public Color value;
    public Vector4UI(Skin skin, final Color value) {

        this.value = value;
        for(int i=0 ; i<4 ; i++){
            final int t = i;
            final FloatUI slider = new FloatUI(skin, get(t), name(i), 0, 1, new FloatUI.FloatUIListener() {
                @Override
                public void onChange(float value) {
                    set(t, value);
                }
            });
            addRow(slider);

            final int index = i;


        }
    }
    private String name(int i) {
        switch(i){
            case 0: return "x";
            case 1: return "y";
            case 2: return "z";
            case 3: return "w";
        }
        return null;
    }
    private void set(int i, float value) {
        switch(i){
            case 0: this.value.r = value; break;
            case 1: this.value.g = value; break;
            case 2: this.value.b = value; break;
            case 3: this.value.a = value; break;
        }
    }
    public float get(int i) {
        switch(i){
            case 0: return value.r;
            case 1: return value.g;
            case 2: return value.b;
            case 3: return value.a;
        }
        return 0;
    }

    public Color getValue() {
        return value;
    }

    public void set(Color value) {
        this.value.set(value);
        sliders.get(0).setValue(value.r);
        sliders.get(1).setValue(value.g);
        sliders.get(2).setValue(value.b);
        sliders.get(3).setValue(value.a);
    }

    public void set(float r, float g, float b, float a) {
        this.value.set(r,g,b,a);
        sliders.get(0).setValue(r);
        sliders.get(1).setValue(g);
        sliders.get(2).setValue(b);
        sliders.get(3).setValue(a);
    }
}
