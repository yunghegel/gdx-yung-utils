package org.yunghegel.gdx.utils.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CameraController extends InputAdapter {


    public final IntIntMap keys = new IntIntMap();
    public PerspectiveCamera camera;
    public Vector3 target = new Vector3();
    public float translateUnits = 30f;
    public float rotateAngle = 360f;
    public int rotateButton = 0;
    public int translateButton = 1;
    boolean rotateButtonPressed = Gdx.input.isButtonPressed(rotateButton);
    boolean translateButtonPressed = Gdx.input.isButtonPressed(translateButton);


    private float startX, startY;

    private final Vector3 tmpV0 = new Vector3();
    private final Vector3 tmpV1 = new Vector3();
    private final Vector3 tmpV2 = new Vector3();

    private Vector3 oldCameraDir = new Vector3();
    private Vector3 oldCameraPos = new Vector3();

    private Viewport viewport;


    public CameraController(PerspectiveCamera cam,int rotateButton, int translateButton,Vector3 target) {
        this.camera = cam;
        this.rotateButton = rotateButton;
        this.translateButton = translateButton;
        this.target = target;
    }

    public CameraController(PerspectiveCamera cam,int rotateButton, int translateButton) {
        this.camera = cam;
        this.rotateButton = rotateButton;
        this.translateButton = translateButton;

    }

    public CameraController(PerspectiveCamera cam) {
        this.camera = cam;
    }

    public void setCamera(PerspectiveCamera camera){
        this.camera = camera;
        if(viewport!=null){
            cameraRayXZPlaneIntersection(camera.position, camera.direction, viewport);
        }

    }

    public void setViewport(Viewport viewport){
        this.viewport = viewport;
    }

    public void disableTranslation(){
        translateButton = -1;
    }

    public void disableRotation(){
        rotateButton = -1;
    }

    public void setTarget(Vector3 target){
        this.target = target;

    }

    public void setTranslateButton(int translateButton){
        this.translateButton = translateButton;
    }

    public void setRotateButton(int rotateButton){
        this.rotateButton = rotateButton;
    }

    public void enableTranslation(){
        translateButton = 1;
    }

    public void enableRotation(){
        rotateButton = 0;
    }

    @Override
    public boolean keyDown(int keycode) {
        keys.put(keycode, keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keys.remove(keycode, 0);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        startX = screenX;
        startY = screenY;
        oldCameraDir.set(camera.direction);
        oldCameraPos.set(camera.position);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        final float deltaX = (screenX - startX) / Gdx.graphics.getWidth();
        final float deltaY = (startY - screenY) / Gdx.graphics.getHeight();
        startX = screenX;
        startY = screenY;
        oldCameraDir.set(camera.direction);
        oldCameraPos.set(camera.position);

        if (Gdx.input.isButtonPressed(rotateButton)) {
            if(target==null){
                if(viewport!=null){
                    cameraRayXZPlaneIntersection(camera.position, camera.direction, viewport);
                }
            }
            tmpV1.set(camera.direction).crs(camera.up).y = 0f;
            tmpV1.set(camera.direction).crs(camera.up).y = 0f;
            camera.rotateAround(target, tmpV1.nor(), deltaY * rotateAngle);
            camera.rotateAround(target, Vector3.Y, deltaX * -rotateAngle);
        } else if (Gdx.input.isButtonPressed(translateButton)) {
            camera.translate(tmpV1.set(camera.direction).crs(camera.up).nor().scl(-deltaX * translateUnits));
            camera.translate(tmpV2.set(camera.up).scl(-deltaY * translateUnits));
            target.add(tmpV1).add(tmpV2);
            target.y=0;
        }


        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {

        float sign = Math.copySign(1f, amountY);
        final float delta = Gdx.graphics.getDeltaTime()*-sign;
        camera.translate(tmpV1.set(camera.direction).scl(delta * translateUnits));

        //interpolate camera position
        return super.scrolled(amountX, amountY);
    }

    public void cameraRayXZPlaneIntersection(Vector3 cameraPosition, Vector3 cameraDirection, Viewport viewport) {
//        Plane level on horizon
        Plane plane = new Plane(new Vector3(0, 1, 0), 0);
        Vector3 intersection = new Vector3();
//        pick ray from center of screen
        Ray ray = camera.getPickRay(viewport.getScreenWidth() / 2, viewport.getScreenHeight() / 2);
//        intersect ray with plane
        if (Intersector.intersectRayPlane(ray, plane, intersection)) {
            target.set(intersection);
        } else {
            target.set(camera.position.x,0,camera.position.z);
        }

    }

    final Vector3 tmpVec = new Vector3();


}
