package org.yunghegel.gdx.utils.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisTable;

public class HorizontalTable extends VisTable {

    public HorizontalGroup group;

    public HorizontalTable() {
        group = new HorizontalGroup();
        add(group);
        align(Align.left);
        group.align(Align.left);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void setActors(Actor ... actors) {
        group.clear();
        for (Actor actor : actors) {
            group.addActor(actor);
        }


    }



}
