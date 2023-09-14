package org.yunghegel.gdx.utils.graphics;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class CameraConfig {
    private static final Vector3 vec3 = new Vector3();
    public float fieldOfView = 67, near = 0.01f, far = 200f;
    public Vector3 position = new Vector3();
    public Vector3 target = new Vector3(0,0,-1);
    public Vector3 up = new Vector3(Vector3.Y);
    public PerspectiveCamera configure(PerspectiveCamera camera){
        camera.fieldOfView = fieldOfView;
        camera.near = near;
        camera.far = far;
        camera.position.set(position);
        camera.up.set(up);
        camera.lookAt(target);
        camera.update();
        return camera;
    }
    public CameraConfig set(PerspectiveCamera camera, Vector3 target) {
        fieldOfView = camera.fieldOfView;
        near = camera.near;
        far = camera.far;
        position.set(camera.position);
        up.set(camera.up);
        this.target.set(target);
        return this;
    }
    public CameraConfig set(CameraConfig o) {
        fieldOfView = o.fieldOfView;
        near = o.near;
        far = o.far;
        position.set(o.position);
        up.set(o.up);
        target.set(o.target);
        return this;
    }
    public CameraConfig lerp(CameraConfig o, float t) {
        fieldOfView = MathUtils.lerp(fieldOfView, o.fieldOfView, t);
        near = MathUtils.lerp(near, o.near, t);
        far = MathUtils.lerp(far, o.far, t);
        position.lerp(o.position, t);
        up.lerp(o.up, t);
        target.lerp(o.target, t);
        return this;
    }
    public CameraConfig cpy() {
        return new CameraConfig().set(this);
    }

    public static void lerp(PerspectiveCamera camera, CameraConfig src, CameraConfig dst, float alpha){
        camera.position.set(src.position).lerp(dst.position, alpha);
        camera.up.set(src.up).lerp(dst.up, alpha);
        camera.lookAt(vec3.set(src.target).lerp(dst.target, alpha));
        camera.near = MathUtils.lerp(src.near, dst.near, alpha);
        camera.far = MathUtils.lerp(src.far, dst.far, alpha);
        camera.fieldOfView = MathUtils.lerp(src.fieldOfView, dst.fieldOfView, alpha);
        camera.update();
    }

}
