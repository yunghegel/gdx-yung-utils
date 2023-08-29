package org.yunghegel.gdx.utils.math;

import com.badlogic.gdx.math.FloatCounter;
import com.badlogic.gdx.utils.Array;

/** An Array that will automatically remove oldest items when beyond max size */
public class RollingFloatArray {

    public FloatCounter floatCounter;

    public RollingFloatArray() {

    }

    public RollingFloatArray(int maxSize) {
        this.maxSize = maxSize;
        floatCounter = new FloatCounter(maxSize);
    }

    Array<Float> items = new Array<>();
    public int maxSize = 60;

    public void clear() {
        items.clear();
    }

    public Array<Float> getItems() {
        return items;
    }

    public void add(float item) {
        items.add(item);
        floatCounter.put(item);
        if (items.size > maxSize) {
            items.removeIndex(0);
        }
    }

    public float getAverage() {
        float total = 0;
        for (float item : items) {
            total += item;
        }
        return total / items.size;
    }
}
