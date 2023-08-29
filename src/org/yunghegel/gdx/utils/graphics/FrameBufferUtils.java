package org.yunghegel.gdx.utils.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFormat;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.function.Consumer;

public class FrameBufferUtils {

    private static Texture whitePixel = null;

    public static FrameBuffer create(GLFormat format, int width, int height, boolean depth) {
        GLFrameBuffer.FrameBufferBuilder b = new GLFrameBuffer.FrameBufferBuilder(width, height);
        b.addColorTextureAttachment(format.internalFormat, format.format, format.type);
        if(depth) b.addDepthRenderBuffer(GL30.GL_DEPTH_COMPONENT24);
        return b.build();
    }

    public static FrameBuffer create(Pixmap.Format format, int width, int height, boolean depth) {
        GLFrameBuffer.FrameBufferBuilder b = new GLFrameBuffer.FrameBufferBuilder(width, height);
        b.addBasicColorTextureAttachment(format);
        if(depth) b.addDepthRenderBuffer(GL30.GL_DEPTH_COMPONENT24);
        return b.build();
    }

    public static FrameBuffer create(GLFormat format, boolean depth){
        return create(format, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), depth);
    }

    public static FrameBuffer ensureSize(FrameBuffer fbo, GLFormat format, int width, int height, boolean depth, Consumer<FrameBuffer> init) {
        if(fbo == null || fbo.getWidth() != width || fbo.getHeight() != height){
            if(fbo != null) fbo.dispose();
            fbo = create(format, width, height, depth);
            if(init != null) init.accept(fbo);
        }
        return fbo;
    }

    public static FrameBuffer ensureSize(FrameBuffer fbo, Pixmap.Format format, int width, int height, boolean depth, Consumer<FrameBuffer> init) {
        if(fbo == null || fbo.getWidth() != width || fbo.getHeight() != height){
            if(fbo != null) fbo.dispose();
            fbo = create(format, width, height, depth);
            if(init != null) init.accept(fbo);
        }
        return fbo;
    }

    public static FrameBuffer ensureSize(FrameBuffer fbo, GLFormat format, FrameBuffer match) {
        return ensureSize(fbo, format, match.getWidth(), match.getHeight());
    }

    public static FrameBuffer ensureSize(FrameBuffer fbo, GLFormat format, GLTexture texture) {
        return ensureSize(fbo, format, texture.getWidth(), texture.getHeight());
    }

    public static FrameBuffer ensureSize(FrameBuffer fbo, GLFormat format, int width, int height) {
        return ensureSize(fbo, format, width, height, false);
    }

    public static FrameBuffer ensureSize(FrameBuffer fbo, GLFormat format, int width, int height, boolean depth) {
        return ensureSize(fbo, format, width, height, depth, null);
    }

    public static FrameBuffer ensureScreenSize(FrameBuffer fbo, GLFormat format) {
        return ensureSize(fbo, format, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
    }

    public static FrameBuffer ensureScreenSize(FrameBuffer fbo, GLFormat format, boolean depth) {
        return ensureSize(fbo, format, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), depth);
    }


    public static FrameBuffer ensureSize(FrameBuffer fbo, Pixmap.Format format, FrameBuffer match) {
        return ensureSize(fbo, format, match.getWidth(), match.getHeight());
    }

    public static FrameBuffer ensureSize(FrameBuffer fbo, Pixmap.Format format, GLTexture texture) {
        return ensureSize(fbo, format, texture.getWidth(), texture.getHeight());
    }

    public static FrameBuffer ensureSize(FrameBuffer fbo, Pixmap.Format format, int width, int height) {
        return ensureSize(fbo, format, width, height, false);
    }

    public static FrameBuffer ensureSize(FrameBuffer fbo, Pixmap.Format format, int width, int height, boolean depth) {
        return ensureSize(fbo, format, width, height, depth, null);
    }

    public static FrameBuffer ensureScreenSize(FrameBuffer fbo, Pixmap.Format format) {
        return ensureSize(fbo, format, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
    }

    public static FrameBuffer ensureScreenSize(FrameBuffer fbo, Pixmap.Format format, boolean depth) {
        return ensureSize(fbo, format, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), depth);
    }



    public static void blit(SpriteBatch batch, Texture input, FrameBuffer output) {
        output.begin();
        blit(batch, input);
        output.end();
    }

    public static void blit(SpriteBatch batch, Texture input) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 1, 1);
        batch.begin();
        batch.draw(input, 0, 0, 1, 1, 0, 0, 1, 1);
        batch.end();
    }

    public static void blit(SpriteBatch batch, Texture input, FrameBuffer output, ShaderProgram shader) {
        batch.setShader(shader);
        blit(batch, input, output);
        batch.setShader(null);
    }

    public static void blit(SpriteBatch batch, Texture input, ShaderProgram shader) {
        batch.setShader(shader);
        blit(batch, input);
        batch.setShader(null);
    }

    public static void blit(SpriteBatch batch, ShaderProgram shader, FrameBuffer output) {
        if(whitePixel == null) whitePixel = TextureUtils.createWhitePixel();
        batch.setShader(shader);
        blit(batch, whitePixel, output);
        batch.setShader(null);
    }

    public static void blit(SpriteBatch batch, Texture input, float srcX, float srcY, float srcW, float srcH) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 1, 1);
        batch.begin();
        batch.draw(input, 0, 0, 1, 1, srcX, srcY, srcX + srcW, srcY + srcH);
        batch.end();
    }

    public static void subBlit(SpriteBatch batch, Texture input, float dstX, float dstY, float dstW, float dstH) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 1, 1);
        batch.begin();
        batch.draw(input, dstX, dstY, dstW, dstH, 0, 0, 1, 1);
        batch.end();
    }



}
