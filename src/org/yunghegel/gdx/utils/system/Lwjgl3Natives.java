package org.yunghegel.gdx.utils.system;

public interface Lwjgl3Natives {

    void setPosition(int x, int y);

    void setFullscreen();

    void setIconified();

    void setWindowedMode(int width, int height);

    void resizeWindow(int width, int height);

    void restoreWindow();

    void dragWindow(int x, int y);

    int getWindowX();

    int getWindowY();

}
