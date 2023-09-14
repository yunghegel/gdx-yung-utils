package org.yunghegel.gdx.utils.ui.fields;

import java.util.function.Supplier;

public class LabeledIntField extends LabeledValueDisplayer<Integer> {

    Supplier<Integer> valueSupplier;

    public LabeledIntField(String name, Integer value) {
        super(name, value);
    }

    public LabeledIntField(String name, Integer value, Supplier<Integer> valueSupplier) {

        super(name,value);
        this.valueSupplier=valueSupplier;

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(valueSupplier!=null){
            valueLabel.setText( ""+valueSupplier.get());
        }
    }


}
