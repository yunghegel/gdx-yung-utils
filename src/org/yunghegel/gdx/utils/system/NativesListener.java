package org.yunghegel.gdx.utils.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowListener;
import org.lwjgl.glfw.GLFW;

public class NativesListener implements Lwjgl3WindowListener,Lwjgl3Natives{

    boolean isMaximized = false;

    @Override
    public void created(Lwjgl3Window window) {

    }

    @Override
    public void iconified(boolean isIconified) {

    }

    @Override
    public void maximized(boolean isMaximized) {

    }

    @Override
    public void focusLost() {

    }

    @Override
    public void focusGained() {

    }

    @Override
    public boolean closeRequested() {
        return false;
    }

    @Override
    public void filesDropped(String[] files) {

    }

    @Override
    public void refreshRequested() {

    }

    @Override
    public void setPosition(int x, int y) {
        Lwjgl3Window window = ((Lwjgl3Graphics) Gdx.graphics).getWindow();
        GLFW.glfwSetWindowPos(window.getWindowHandle(), x, y);
    }

    @Override
    public void setFullscreen() {

        if(isMaximized){
            Lwjgl3Window window = ((Lwjgl3Graphics) Gdx.graphics).getWindow();
            GLFW.glfwRestoreWindow(window.getWindowHandle());
            isMaximized = false;
        }
        else {
            Lwjgl3Window window = ((Lwjgl3Graphics) Gdx.graphics).getWindow();
            GLFW.glfwMaximizeWindow(window.getWindowHandle());
            isMaximized = true;
        }


    }

    @Override
    public void setIconified() {
        Lwjgl3Window window = ((Lwjgl3Graphics) Gdx.graphics).getWindow();
        GLFW.glfwIconifyWindow(window.getWindowHandle());
    }

    @Override
    public void setWindowedMode(int width, int height) {
        Gdx.graphics.setWindowedMode(width, height);
    }

    @Override
    public void resizeWindow(int width, int height) {
        Lwjgl3Window window = ((Lwjgl3Graphics) Gdx.graphics).getWindow();
        GLFW.glfwSetWindowSize(window.getWindowHandle(), width, height);
    }

    @Override
    public void restoreWindow() {

    }

    @Override
    public void dragWindow(int x, int y) {
        Lwjgl3Window window = ((Lwjgl3Graphics) Gdx.graphics).getWindow();
        window.setPosition(x, y);
    }

    @Override
    public int getWindowX() {
        return ((Lwjgl3Graphics) Gdx.graphics).getWindow().getPositionX();

    }

    @Override
    public int getWindowY() {
        return ((Lwjgl3Graphics) Gdx.graphics).getWindow().getPositionY();
    }
}
