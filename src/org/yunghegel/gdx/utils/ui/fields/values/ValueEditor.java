package org.yunghegel.gdx.utils.ui.fields.values;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;

import javax.swing.event.ChangeEvent;

public abstract class ValueEditor<T> extends VisTable implements ValueField<T> {


    protected T value;
    protected ValueConsumer<T> consumer;
    protected ValueSupplier<T> supplier;
    protected boolean valueModified;
    protected String name;
    protected VisLabel nameLabel;
    protected VisLabel valueLabel;
    protected Actor valueEditor;
    public HorizontalGroup group;

    public ChangeListener changeListener = new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            consumer.consume(value);
        }
    };

    public ValueEditor(String name, T value, ValueConsumer<T> consumer) {
        this.name = name;
        this.value = value;
        this.consumer = consumer;
        nameLabel = new VisLabel(name);
        valueLabel = new VisLabel(value.toString());
    }

    public void setConsumer(ValueConsumer<T> consumer) {
        this.consumer = consumer;
    }


    @Override
    public T getValue() {
        return value;
    }


    public abstract void valueChanged();

    public abstract void buildWidget();


    @Override
    public void act(float delta) {
        super.act(delta);
        valueLabel.setText(value.toString());
    }
}
