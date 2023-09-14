package org.yunghegel.gdx.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class ColorUtils {

    /**
     * Returns a color from HSV values.
     */

    public static Color hsvToColor (Color color, float h, float s, float v) {
        float x = (h/60f + 6) % 6;
        int i = MathUtils.floorPositive(x);
        float f = x - i;
        float p = v * (1 - s);
        float q = v * (1 - s * f);
        float t = v * (1 - s * (1 - f));
        switch (i) {
            case 0:
                color.r = v;
                color.g = t;
                color.b = p;
                break;
            case 1:
                color.r = q;
                color.g = v;
                color.b = p;
                break;
            case 2:
                color.r = p;
                color.g = v;
                color.b = t;
                break;
            case 3:
                color.r = p;
                color.g = q;
                color.b = v;
                break;
            case 4:
                color.r = t;
                color.g = p;
                color.b = v;
                break;
            default:
                color.r = v;
                color.g = p;
                color.b = q;
        }

        return color;
    }


    /**
      * Returns a color from HSV values.
     */
    public static Color hsvToColor(Color c, float[] hsv) {
        return hsvToColor(c, hsv[0], hsv[1], hsv[2]);
    }

    public static Color randomColor(float lo, float hi) {
        Color col = new Color();
        col.r = MathUtils.random(lo, hi);
        col.g = MathUtils.random(lo, hi);
        col.b = MathUtils.random(lo, hi);
        col.a = 1f;
        return col;
    }

    public static Color randomColor() {
        return randomColor(0f, 1f);
    }

    public static Color rand(){
        Color saturatedColor = Color.WHITE.cpy();
        Color color = hsvToColor(saturatedColor, MathUtils.random()*360f, 1f, 1f);
        return color;

    }



}
