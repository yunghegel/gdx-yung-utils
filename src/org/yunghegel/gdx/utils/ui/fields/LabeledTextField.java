package org.yunghegel.gdx.utils.ui.fields;

import java.util.function.Supplier;

public class LabeledTextField extends LabeledValueDisplayer<String> {

    Supplier<String> stringSupplier;

    public LabeledTextField(String name, String value) {
        super(name, value);
    }

    public LabeledTextField(String name, String value, Supplier<String> stringSupplier) {
        super(name, value);
        this.stringSupplier = stringSupplier;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (stringSupplier != null) {
            value = stringSupplier.get();
        }
    }

}
