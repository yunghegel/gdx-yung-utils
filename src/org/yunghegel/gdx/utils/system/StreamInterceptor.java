package org.yunghegel.gdx.utils.system;

import com.badlogic.gdx.physics.bullet.collision._btMprSimplex_t;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public interface StreamInterceptor<T extends OutputStream,R extends InputStream> {

    void intercept(T out);

    }




