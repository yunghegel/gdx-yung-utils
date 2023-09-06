package org.yunghegel.gdx.utils.ui.fields;

import java.util.function.Supplier;

public class LabeledFloatField extends LabeledValueDisplayer<Float>{

    Supplier<Float> valueSupplier;

    public LabeledFloatField(String name, Float value) {
        super(name, value);
    }

    public LabeledFloatField(String name, Float value, Supplier<Float> valueSupplier) {
        super(name, value);
        this.valueSupplier = valueSupplier;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (valueSupplier != null) {
            valueLabel.setText("" + valueSupplier.get());
        }
    }

}
