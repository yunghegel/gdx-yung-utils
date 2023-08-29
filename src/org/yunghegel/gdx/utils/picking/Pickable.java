package org.yunghegel.gdx.utils.picking;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

public interface Pickable {

    int getID();

    Material getMaterial();

    void renderPick(ModelBatch batch);

    default void encode(){
        PickerIDAttribute attr=PickerColorEncoder.encodeRaypickColorId(getID());
        getMaterial().set(attr);
    }

}
