package org.yunghegel.gdx.utils.graphics.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import org.yunghegel.gdx.utils.graphics.model.suppliers.*;

public class PrimitiveSupplier {

    public static ModelInstance getRandomPrimitive() {
        return null;
    }

    public static ModelInstance quickPrimitive(Primitive primitive, Color color) {
        switch (primitive){
            case Box:
                return new BoxSupplier(1,1,1,color).build();
            case Cube:
                return new CubeSupplier(1,color).build();
            case Cone:
                return new ConeSupplier(1f,1f,1f,15,color).build();
            case Cylinder:
                return new CylinderSupplier(1f,1f,1f,15,color).build();
            case Sphere:
                return new SphereSupplier(1f,30,30,color).build();
            case Capsule:
                return new CapsuleSupplier(1f,2f,15,color).build();
            default:
                return null;
        }
    }

    public static ModelInstance quickPrimitive(Primitive primitive) {
        return quickPrimitive(primitive, BuilderUtils.getRandomColor());
    }

    public static ModelInstance randomPrimitive() {
        return quickPrimitive(randomPrimitiveType());
    }

    private static Primitive randomPrimitiveType() {
        return Primitive.values()[(int)(Math.random()*Primitive.values().length)];
    }



}
