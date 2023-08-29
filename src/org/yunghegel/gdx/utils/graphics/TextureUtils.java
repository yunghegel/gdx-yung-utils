package org.yunghegel.gdx.utils.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class TextureUtils {

    public static Texture createWhitePixel(){
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        Texture texture = new Texture(pixmap);
        return texture;
    }

    public static void setActive(int unit) {
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0 + unit);
    }

    public static Texture loadMipmapTexture(FileHandle fileHandle, boolean tilable) {
        Texture texture = new Texture(fileHandle, true);
        texture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);

        if (tilable) {
            texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        }

        return texture;
    }
}
