package org.yunghegel.gdx.utils.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.Pixmap;

public class EnvUtil {

    public static Cubemap createCubemapXYZFormat(String prefix) {

        if (!prefix.endsWith("/")) prefix += "/";

        String dir = prefix;
        return createCubemapFromPaths(dir + "px.png" , dir + "nx.png" , dir + "py.png" , dir + "ny.png" , dir + "pz.png" , dir + "nz.png");
    }

    private static Cubemap createCubemapFromPaths(String path1 , String path2 , String path3 , String path4 , String path5 , String path6) {
        return new Cubemap(new Pixmap(Gdx.files.internal(path1)) , new Pixmap(Gdx.files.internal(path2)) , new Pixmap(Gdx.files.internal(path3)) , new Pixmap(Gdx.files.internal(path4)) , new Pixmap(Gdx.files.internal(path5)) , new Pixmap(Gdx.files.internal(path6)));
    }

    public static Cubemap createCubemapDirectionFormat(String prefix) {

        if (!prefix.endsWith("/")) prefix += "/";

        String dir = prefix;
        return createCubemapFromPaths(dir + "right.png" , dir + "left.png" , dir + "up.png" , dir + "down.png" , dir + "front.png" , dir + "back.png");
    }




}
