package org.yunghegel.gdx.utils.graphics.instancing;

import com.badlogic.gdx.math.Matrix4;

public interface InstanceDataSupplier {

    Matrix4 get(int index);

}
