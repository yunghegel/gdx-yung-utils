package org.yunghegel.gdx.utils.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ScreenUtil {

    public static void clear(){
        clearColor(new Color(0.2f,0.2f,0.2f,1));
        clearDepth();
    }

    public static void clear(Color col) {
        clearColor(col);
        clearDepth();
    }

    public static void clearColor(Color col){
        Gdx.gl.glClearColor(col.r, col.g, col.b, col.a);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
    }

    public static void clearDepth(){
        Gdx.gl.glClear(Gdx.gl.GL_DEPTH_BUFFER_BIT);
    }

    public static void enableDepth(){
        Gdx.gl.glEnable(Gdx.gl.GL_DEPTH_TEST);
    }

    public static void disableDepth(){
        Gdx.gl.glDisable(Gdx.gl.GL_DEPTH_TEST);
    }

    public static void enableBlending(){
        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
    }

    public static void disableBlending(){
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
    }

    public static void enableCullFace(){
        Gdx.gl.glEnable(Gdx.gl.GL_CULL_FACE);
    }

    public static void disableCullFace(){
        Gdx.gl.glDisable(Gdx.gl.GL_CULL_FACE);
    }

    public static void enableScissor(){
        Gdx.gl.glEnable(Gdx.gl.GL_SCISSOR_TEST);
    }

    public static void disableScissor(){
        Gdx.gl.glDisable(Gdx.gl.GL_SCISSOR_TEST);
    }

    public static void scissor(int x, int y, int width, int height){
        Gdx.gl.glScissor(x, y, width, height);
    }

    public static void scissor(Rectangle rect){
        Gdx.gl.glScissor((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
    }

    public static void viewport(int x, int y, int width, int height){
        Gdx.gl.glViewport(x, y, width, height);
    }

    public static void viewport(Rectangle rect){
        Gdx.gl.glViewport((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
    }

    public static void viewport(int width, int height){
        Gdx.gl.glViewport(0, 0, width, height);
    }

    public static void viewport(){
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public static Vector2 toOpenGLCoords(Vector2 pixelXY, Vector2 widthHeight){

        float x = pixelXY.x;
        float y = pixelXY.y;
        float w = widthHeight.x;
        float h = widthHeight.y;

        x = (x / w) * 2 - 1;
        y = (y / h) * 2 - 1;

        return new Vector2(x,y);
    }

    public static Vector2 toOpenGLCoords(Vector2 pixelXY){
        return toOpenGLCoords(pixelXY, new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    public static Vector2 toOpenGLCoords(float x, float y){
        return toOpenGLCoords(new Vector2(x,y));
    }

    public static Vector2 toOpenGLCoords(float x, float y, float w, float h){
        return toOpenGLCoords(new Vector2(x,y), new Vector2(w,h));
    }

    public static Vector2 toOpenGLCoords(int x, int y){
        return toOpenGLCoords(new Vector2(x,y));
    }

    public static Vector2 toOpenGLCoords(int x, int y, int w, int h){
        return toOpenGLCoords(new Vector2(x,y), new Vector2(w,h));
    }





}
