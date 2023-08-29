package org.yunghegel.gdx.utils.ui.fields;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;
import org.yunghegel.gdx.utils.ui.fields.values.ValueField;

import java.util.function.Supplier;

public abstract class ValueDisplayer<T> extends VisTable implements ValueField<T> {

    public T value;
    protected Supplier<T> valueSupplier;
    public VisTextField valueLabel;
    public Cell<VisTextField> valueLabelCell;

    public ValueDisplayer(T value) {
        this.value = value;
        valueLabel = new VisTextField(value.toString());
        valueLabel.setReadOnly(true);

        buildWidget();
    }

    public ValueDisplayer(T value, Supplier<T> valueSupplier) {
        this(value);
        this.valueSupplier = valueSupplier;

    }

    abstract protected void buildWidget();

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (valueSupplier != null) {
            value = valueSupplier.get();
        }
        valueLabel.setText(value.toString());
    }


}
