package org.yunghegel.gdx.utils.graphics.gl;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GLUtils {

    public static final IntBuffer buffer1i = BufferUtils.newIntBuffer(1);
    public static final FloatBuffer buffer16f = BufferUtils.newFloatBuffer(16);
    private static IntBuffer buffer16i = BufferUtils.newIntBuffer(16);
    private static final IntBuffer INT_BUFF = ByteBuffer
            .allocateDirect(64).order(ByteOrder.nativeOrder())
            .asIntBuffer();

    private static boolean complete = false;

    private static int textureMaxSize;
    private static int textureMinSize;

    private static void ensureComplete(){
        if(!complete){

            textureMinSize = 4;
            textureMaxSize = getInt(GL20.GL_MAX_TEXTURE_SIZE);

            complete = true;
        }
    }

    /**
     * @return the maximum texture size allowed by the device.
     */

    public static int getTextureMaxSize(){
        ensureComplete();
        return textureMaxSize;
    }

    /**
     * @return the minimum texture size allowed by the device.
     */

    public static int getTextureMinSize() {
        ensureComplete();
        return textureMinSize;
    }

    public static String getGLVersionString() {
        String version = "";
        if (GLUtils.isGL3()) {
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                version = "#version 130\n" + "#define GLSL3\n";
            } else if (Gdx.app.getType() == Application.ApplicationType.Android ||
                    Gdx.app.getType() == Application.ApplicationType.iOS ||
                    Gdx.app.getType() == Application.ApplicationType.WebGL) {
                version = "#version 300 es\n" + "#define GLSL3\n";
            }
        }
        return version;
    }


    /**
     * Returns the value of a selected integer parameter.
     * See {@link GL20} for a list of possible parameters.
     * @param pname
     * @return
     */

    public static int getInt(int pname){
        buffer16i.clear();
        Gdx.gl.glGetIntegerv(pname, buffer16i);
        return buffer16i.get(0);
    }

    public static int getMaxSamples() {
        return getInt(GL30.GL_MAX_SAMPLES);
    }
    public static int getMaxSamplesPoT() {
        int max = getInt(GL30.GL_MAX_SAMPLES);
        return max > 1 ? 32 - Integer.numberOfLeadingZeros(max - 1) : 0;
    }

    public static void clearScreen(Color color) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(color.r, color.g, color.b, 1);
    }

    //clear depth
    public static void clearDepth() {
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
    }

    //clear color
    public static void clearColor() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    //clear color and depth
    public static void clearColorAndDepth(Color color) {
        clearColor();
        clearDepth();
    }

    public static boolean isGL3(){
        return Gdx.graphics.getGLVersion().isVersionEqualToOrHigher(3, 0);
    }

    public static synchronized int getBoundFboHandle() {
        IntBuffer intBuf = INT_BUFF;
        Gdx.gl.glGetIntegerv(GL20.GL_FRAMEBUFFER_BINDING, intBuf);
        return intBuf.get(0);
    }

    public static synchronized int[] getViewport() {
        IntBuffer intBuf = INT_BUFF;
        Gdx.gl.glGetIntegerv(GL20.GL_VIEWPORT, intBuf);

        return new int[] { intBuf.get(0), intBuf.get(1), intBuf.get(2),
                intBuf.get(3) };
    }



}
