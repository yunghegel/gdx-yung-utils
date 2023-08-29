package org.yunghegel.gdx.utils.ui.fields;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import org.yunghegel.gdx.utils.ui.fields.values.ValueConsumer;
import org.yunghegel.gdx.utils.ui.fields.values.ValueEditor;
import org.yunghegel.gdx.utils.ui.fields.values.ValueSupplier;

public class BooleanField extends ValueEditor<Boolean> {

    boolean value;

    protected VisCheckBox checkBox;

    public BooleanField(String name, boolean value, ValueConsumer<Boolean> consumer) {
        super(name, value, consumer);
        this.consumer = consumer;
    }


    @Override
    public void valueChanged() {
        consumer.consume(value);
    }

    @Override
    public void buildWidget() {
        checkBox = new VisCheckBox("",value);
        group.addActor(nameLabel);
        group.addActor(checkBox);
        group.addActor(valueLabel);
        group.space(10);

        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                consumer.consume(checkBox.isChecked());
            }
        });
    }
}
