package org.yunghegel.gdx.utils.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Profiler {

    private static final IntBuffer intBuffer = BufferUtils.newIntBuffer(16);

    public static final String GL_NVX_gpu_memory_info_ext = "GL_NVX_gpu_memory_info";
    public static final int GL_GPU_MEM_INFO_TOTAL_AVAILABLE_MEM_NVX = 0x9048;
    public static final int GL_GPU_MEM_INFO_CURRENT_AVAILABLE_MEM_NVX = 0x9049;

    public static int DRAW_CALLS = 0;
    public static int RENDER_CALLS = 0;
    public static int SHADER_SWITCHES = 0;
    public static int TEXTURE_BINDINGS = 0;
    public static float VERTEX_COUNT = 0;


    public static final long JAVA_TOTAL_MEMORY = Runtime.getRuntime().totalMemory();
    public static final long JAVA_FREE_MEMORY = Runtime.getRuntime().freeMemory();
    public static final long JAVA_MAX_MEMORY = Runtime.getRuntime().maxMemory();
    public static final String MEMORY_INFO = "Total: " + JAVA_TOTAL_MEMORY + " Free: " + JAVA_FREE_MEMORY + " Max: " + JAVA_MAX_MEMORY;
    public static final String VERSION = "Java version: " + System.getProperty("java.version") + " " + System.getProperty("java.vendor");



    public static GLProfiler glProfiler;
    Runtime runtime;

    FloatBuffer floatBuffer;


    public Profiler(){
        glProfiler = new GLProfiler(Gdx.graphics);
        runtime = Runtime.getRuntime();
        glProfiler.enable();
    }

    public static int getGLMaxMemoryKB(){
        intBuffer.clear();
        Gdx.gl.glGetIntegerv(GL_GPU_MEM_INFO_TOTAL_AVAILABLE_MEM_NVX, intBuffer);
        return intBuffer.get();
    }

    public static int getGLAvailableMemoryKB(){
        intBuffer.clear();
        Gdx.gl.glGetIntegerv(GL_GPU_MEM_INFO_CURRENT_AVAILABLE_MEM_NVX, intBuffer);
        return intBuffer.get();
    }

    public static int getGLUsedMemoryKB(){
        return getGLMaxMemoryKB() - getGLAvailableMemoryKB();
    }

    public static boolean hasMemoryInfo(){
        return Gdx.graphics.supportsExtension(GL_NVX_gpu_memory_info_ext);
    }

    public static int getDrawCalls(){
        return glProfiler.getDrawCalls();
    }

    public static int getRenderCalls(){
        return glProfiler.getCalls();
    }

    public static int getShaderSwitches(){
        return glProfiler.getShaderSwitches();
    }

    public static int getTextureBindings(){
        return glProfiler.getTextureBindings();
    }

    public static float getVertexCount(){
        return glProfiler.getVertexCount().total;
    }

    public static void reset(){
        glProfiler.reset();
    }

    public static String getGLAvailableMemoryGB(){
        return String.format("%.2f", (float) getGLAvailableMemoryKB() / 1024) + " MB";
    }

    public static String getGLMaxMemoryGB(){
        return String.format("%.2f", (float) getGLMaxMemoryKB() / 1024) + " MB";
    }

    public static String getGLUsedMemoryGB(){
        return String.format("%.2f", (float) (getGLUsedMemoryKB()) / 1024) + " MB";
    }


    public static String getJavaUsedMemoryGB(){
        return String.format("%.2f", (float) (JAVA_TOTAL_MEMORY - JAVA_FREE_MEMORY) / 1024 / 1024) + " MB";
    }

    public static String getJavaMaxMemoryGB(){
        return String.format("%.2f", (float) JAVA_MAX_MEMORY / 1024 / 1024) + " MB";
    }

    public static String getJavaAvailableMemoryGB(){
        return String.format("%.2f", (float) (JAVA_MAX_MEMORY - (JAVA_TOTAL_MEMORY - JAVA_FREE_MEMORY)) / 1024 / 1024) + " MB";
    }

    //To string method

    public static String toStringGLMemory(){
        return "[ GL MEM: " + getGLUsedMemoryGB() + " / " + getGLMaxMemoryGB() + " (" + getGLAvailableMemoryGB() + ") ]";
    }

    public static String toStringJavaMemory(){
        return "[ JVM MEM: " + getJavaUsedMemoryGB() + " / " + getJavaMaxMemoryGB() + " (" + getJavaAvailableMemoryGB() + ") ]";
    }

    public static String toStringProfiler(){
        return "[ DRAW CALLS: " + getDrawCalls() + " | RENDER CALLS: " + getRenderCalls() + " | SHADER SWITCHES: " + getShaderSwitches() + " | TEX BINDS: " + getTextureBindings() + " | VERT COUNT: " + getVertexCount()+ " ]";
    }

    public static String toStringAll(){
        return toStringGLMemory() + " | " + toStringJavaMemory() + " " + toStringProfiler();
    }










}
