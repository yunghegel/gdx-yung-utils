package org.yunghegel.gdx.utils.graphics.terrain;

import com.badlogic.gdx.math.Interpolation;

import java.util.Random;

public class PerlinTerrainDataSupplier {

    private final Random rand = new Random();

    private long seed = 0;
    // number of noise functions
    private int octaves = 1;
    // decrease of amplitude per octave
    private float roughness = 0;

    protected float minHeight = 0;
    protected float maxHeight = 50;

    public PerlinTerrainDataSupplier seed(long seed) {
        this.seed = seed;
        return this;
    }

    public PerlinTerrainDataSupplier octaves(int octaves) {
        this.octaves = octaves;
        return this;
    }

    public PerlinTerrainDataSupplier roughness(float roughness) {
        this.roughness = roughness;
        return this;
    }

    public PerlinTerrainDataSupplier minHeight(float minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    public PerlinTerrainDataSupplier maxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    //rand seed
    public void randomSeed() {
        this.seed = rand.nextLong();
    }



    public void terraform(Terrain terrain) {
        randomSeed();

        // final float d = (float) Math.pow(2, this.octaves);

        for (int i = 0; i < terrain.field.data.length; i++) {
            int x = i % terrain.field.vertexResolution;
            int z = (int) Math.floor((double) i / terrain.field.vertexResolution);

            float height = Interpolation.linear.apply(minHeight, maxHeight, getInterpolatedNoise(x / 4f, z / 4f));
            height += Interpolation.linear.apply(minHeight / 3f, maxHeight / 3f, getInterpolatedNoise(x / 2f, z / 2f));

            terrain.field.data[z * terrain.field.vertexResolution + x] = height;
        }

        terrain.field.update();

    }

    private float interpolate(float a, float b, float blend) {
        double theta = blend * Math.PI;
        float f = (float) (1f - Math.cos(theta)) * 0.5f;
        return a * (1f - f) + b * f;
    }

    private float getInterpolatedNoise(float x, float z) {
        int intX = (int) x;
        int intZ = (int) z;
        float fracX = x - intX;
        float fracZ = z - intZ;

        float v1 = getSmoothNoise(intX, intZ);
        float v2 = getSmoothNoise(intX + 1, intZ);
        float v3 = getSmoothNoise(intX, intZ + 1);
        float v4 = getSmoothNoise(intX + 1, intZ + 1);
        float i1 = interpolate(v1, v2, fracX);
        float i2 = interpolate(v3, v4, fracX);
        return interpolate(i1, i2, fracZ);
    }

    private float getNoise(int x, int z) {
        rand.setSeed(x * 49632 + z * 325176 + seed);
        return rand.nextFloat();
    }

    private float getSmoothNoise(int x, int z) {
        // corner noise
        float corners = getNoise(x + 1, z - 1) + getNoise(x + 1, z - 1) + getNoise(x - 1, z + 1)
                + getNoise(x + 1, z + 1);
        corners /= 16f;
        // side noise
        float sides = getNoise(x - 1, z) + getNoise(x + 1, z) + getNoise(x, z - 1) + getNoise(x, z + 1);
        sides /= 8f;
        // center noise
        float center = getNoise(x, z) / 4f;

        return corners + sides + center;
    }
}
