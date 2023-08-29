package org.yunghegel.gdx.utils.ui.fields;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import java.util.function.Supplier;

public class Vector3Displayer extends ValueDisplayer<Vector3>{

    private FloatDisplayer xDisplayer=new FloatDisplayer(0f);
    private FloatDisplayer yDisplayer=new FloatDisplayer(0f);
    private FloatDisplayer zDisplayer=new FloatDisplayer(0f);



    public Vector3Displayer(Vector3 value, Supplier<Vector3> valueSupplier) {
        super(value, valueSupplier);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(valueSupplier!=null){
            xDisplayer.value=valueSupplier.get().x;
            yDisplayer.value=valueSupplier.get().y;
            zDisplayer.value=valueSupplier.get().z;
        }
    }

    @Override
    protected void buildWidget() {
        xDisplayer=new FloatDisplayer(value.x,()->value.x);
        yDisplayer=new FloatDisplayer(value.y,()->value.y);
        zDisplayer=new FloatDisplayer(value.z,()->value.z);
        HorizontalGroup horizontalGroup=new HorizontalGroup();
        horizontalGroup.space(5);
        horizontalGroup.addActor(xDisplayer);
        horizontalGroup.addActor(yDisplayer);
        horizontalGroup.addActor(zDisplayer);
        add(horizontalGroup);
    }
}
