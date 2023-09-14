package org.yunghegel.gdx.utils.ui.fields;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;
import org.yunghegel.gdx.utils.ui.fields.values.ValueField;

public abstract class LabeledValueDisplayer<T> extends ValueDisplayer<T> implements ValueField<T> {

    public T value;

    public VisLabel nameLabel;
    public VisTextField valueLabel;
    public HorizontalGroup group;

    public LabeledValueDisplayer(String name, T value) {
        super(value);
        align(Align.left);
        this.value = value;
        group = new HorizontalGroup();
        nameLabel = new VisLabel(name);
        valueLabel = new VisTextField(value.toString());
        valueLabel.setReadOnly(true);
        buildWidget();
    }

    public void buildWidget(){
        add(nameLabel).pad(5).left().spaceRight(2);

        add(valueLabel).pad(5).left().maxWidth(40);
//        add(group).pad(5).width(30);
//        group.space(10);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        valueLabel.setText(value.toString());
    }
}
