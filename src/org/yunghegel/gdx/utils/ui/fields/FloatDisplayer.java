package org.yunghegel.gdx.utils.ui.fields;

import com.badlogic.gdx.utils.Align;

import java.util.function.Supplier;

public class FloatDisplayer extends ValueDisplayer<Float>{

    public FloatDisplayer(Float value, Supplier<Float> valueSupplier) {
        super(value, valueSupplier);
    }

    @Override
    protected void buildWidget() {
        valueLabelCell= add(valueLabel);
    }

    public FloatDisplayer(Float value) {
        super(value);
    }


}
