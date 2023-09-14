package org.yunghegel.gdx.utils.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.layout.FlowGroup;
import com.kotcrab.vis.ui.widget.Separator;
import com.kotcrab.vis.ui.widget.VisTable;

public class HeaderContentFooterWidget extends FlowGroup {

    VisTable header;
    VisTable content;
    VisTable footer;

    HorizontalGroup headerGroup;
    HorizontalGroup footerGroup;

    boolean addSeparators = false;
    Separator seperator = new Separator();

    public HeaderContentFooterWidget() {
        super(true);
        buildWidgets();
    }

    public HeaderContentFooterWidget(boolean addSeparators) {
        super(true);
        this.addSeparators = addSeparators;
        buildWidgets();
    }

    protected void buildWidgets(){
        Drawable background = VisUI.getSkin().getDrawable("window-bg");

        header = new VisTable();
        header.background(background);
        content = new VisTable();
        content.background(background);
        footer = new VisTable();
        footer.background(background);

        headerGroup = new HorizontalGroup();
        headerGroup.space(5);;
        footerGroup = new HorizontalGroup();
        footerGroup.space(5);

        header.add(headerGroup).growX().fillX();
        footer.add(footerGroup).growX().fillX();

        addActor(header);
        addActor(content);
        addActor(footer);
    }

    public void addHeaderActor(Actor actor){
        headerGroup.addActor(actor);
    }

    public void addFooterActor(Actor actor){
        footerGroup.addActor(actor);
    }

    public void setContentTable(VisTable table){
        content.add(table).grow().fill();
    }

}
