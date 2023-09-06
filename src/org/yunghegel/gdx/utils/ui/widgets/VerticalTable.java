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
        verticalGroup.wrap();
        verticalGroup.columnAlign(Align.left);

        addSeparator().space(5);
        align(Align.topLeft);
        verticalGroup.columnLeft();
        verticalGroup.space(5);
    }

    public void addRow(VisTable table) {


        table.align(Align.left);
        add(table).grow().fill().row();
    }

    public void addRow(VisTable table, float pad) {
        table.align(Align.left);
        add(table).grow().fill().spaceBottom(5).spaceTop(5).row();
    }

    public void setActors(VisTable ... tables) {
        verticalGroup.clear();
        for (VisTable table : tables) {
            addRow(table);
        }
    }



}
