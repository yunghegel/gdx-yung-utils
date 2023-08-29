package org.yunghegel.gdx.utils.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.Separator;
import com.kotcrab.vis.ui.widget.VisTable;

public class VerticalTable extends VisTable {
    private VerticalGroup verticalGroup;

    public VerticalTable() {
        super();
        verticalGroup = new VerticalGroup();
        add(verticalGroup).grow().fill().row();

        addSeparator().space(5);
        align(Align.topLeft);
        verticalGroup.columnLeft();
        verticalGroup.space(5);
    }

    public void addRow(VisTable table) {
        verticalGroup.addActor(table);
    }

    public void addRow(VisTable table, float pad) {
        verticalGroup.addActor(table);
        verticalGroup.space(pad);
    }

    public void setActors(VisTable ... tables) {
        verticalGroup.clear();
        for (VisTable table : tables) {
            verticalGroup.addActor(table);
        }
    }



}
