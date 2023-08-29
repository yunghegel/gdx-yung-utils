package org.yunghegel.gdx.utils.ui.fields;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import org.yunghegel.gdx.utils.ui.fields.values.ValueConsumer;
import org.yunghegel.gdx.utils.ui.fields.values.ValueEditor;
import org.yunghegel.gdx.utils.ui.fields.values.ValueSupplier;

public class TextInputField extends ValueEditor<String> {

    VisTextField field;
    VisTextButton button;

    public TextInputField(String name, String value, ValueConsumer<String> consumer) {
        super(name, value, consumer);
        field = new VisTextField(value);
        button = new VisTextButton("Clear");
    }

    @Override
    public void valueChanged() {
        consumer.consume(field.getText());
    }

    @Override
    public void buildWidget() {
        group.addActor(nameLabel);
        group.addActor(field);
        group.addActor(button);
        add(group);
    }



}
