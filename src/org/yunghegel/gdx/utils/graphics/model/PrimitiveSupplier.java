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

    public static ModelInstance getBox(float width, float height, float depth, Color color) {
        return new BoxSupplier(width, height, depth, color).build();
    }

    public static ModelInstance getBox(float width, float height, float depth) {
        return getBox(width, height, depth, BuilderUtils.getRandomColor());
    }

    public static ModelInstance getBox(float size, Color color) {
        return getBox(size, size, size, color);
    }

    public static ModelInstance getBox(float size) {
        return getBox(size, BuilderUtils.getRandomColor());
    }

    public static ModelInstance getCube(float size, Color color) {
        return new CubeSupplier(size, color).build();
    }

    public static ModelInstance getSphere(float radius, int divisionsU, int divisionsV, Color color) {
        return new SphereSupplier(radius, divisionsU, divisionsV, color).build();
    }

    public static ModelInstance getSphere(float radius, int divisionsU, int divisionsV) {
        return getSphere(radius, divisionsU, divisionsV, BuilderUtils.getRandomColor());
    }

    public static ModelInstance getSphere(float radius, Color color) {
        return getSphere(radius, 30, 30, color);
    }

    public static ModelInstance getSphere(float radius) {
        return getSphere(radius, BuilderUtils.getRandomColor());
    }

    public static ModelInstance getCone(float radius, float height, int divisions, Color color) {
        return new ConeSupplier(radius, height, divisions,20, color).build();
    }

    public static ModelInstance getCone(float radius, float height, int divisions) {
        return getCone(radius, height, divisions, BuilderUtils.getRandomColor());
    }

    public static ModelInstance getCone(float radius, float height, Color color) {
        return getCone(radius, height, 15, color);
    }

    public static ModelInstance getCone(float radius, float height) {
        return getCone(radius, height, BuilderUtils.getRandomColor());
    }

    public static ModelInstance getCylinder(float radius, float height,float depth,int divisions, Color color) {
        return new CylinderSupplier(radius, height, depth,divisions, color).build();
    }

    public static ModelInstance getCylinder(float radius, float height, int divisions) {
        return getCylinder(radius, height, divisions,15, BuilderUtils.getRandomColor());
    }

    public static ModelInstance getCylinder(float radius, float height, Color color) {
        return getCylinder(radius, height, height/2, 15, color);
    }

    public static ModelInstance getCylinder(float radius, float height) {
        return getCylinder(radius, height, BuilderUtils.getRandomColor());
    }

    public static ModelInstance getCapsule(float radius, float height, int divisions, Color color) {
        return new CapsuleSupplier(radius, height, divisions, color).build();
    }

    public static ModelInstance getCapsule(float radius, float height, int divisions) {
        return getCapsule(radius, height, divisions, BuilderUtils.getRandomColor());
    }

    public static ModelInstance getCapsule(float radius, float height, Color color) {
        return getCapsule(radius, height, 15, color);
    }

    public static ModelInstance getCapsule(float radius, float height) {
        return getCapsule(radius, height, BuilderUtils.getRandomColor());
    }

    public static ModelInstance getCube(float size) {
        return new CubeSupplier(size, BuilderUtils.getRandomColor()).build();
    }

    public static ModelInstance getPlane(float scale, Color color) {
        return new PlaneSupplier(scale, color).build();
    }

    public static ModelInstance getPlane(float scale) {
        return getPlane(scale, BuilderUtils.getRandomColor());

    }






    }
