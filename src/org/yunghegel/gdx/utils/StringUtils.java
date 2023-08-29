package org.yunghegel.gdx.utils;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class StringUtils {

    public static String trimVector3(Vector3 v) {
        return trimFloat(v.x) + ", " + trimFloat(v.y) + ", " + trimFloat(v.z);
    }

    //null safe trim float to 2 decimal places
    public static String trimFloat(float f) {
        if (f == (int) f) return String.valueOf((int) f);
//        String string = String.format("%(-2.2f" , f);
        //String.format is not GWT compatible
        String string = String.valueOf(f);
        int dotIndex = string.indexOf(".");
        if (dotIndex == -1) return string;
        string = string.substring(0, dotIndex + 3);

        //ensure 5 digits
        if (string.length() <= 5) string += "0";
        return string;
    }

    public static boolean matchesSuffix(String string , String suffix) {
        int suffixLength = suffix.length();
        int stringLength = string.length();
        String stringSuffix = string.substring(stringLength - suffixLength);
        return stringSuffix.equals(suffix);

    }

    public static String extractFileType(String string){
        int lastDot = string.lastIndexOf(".");
        if(lastDot==-1) return "";
        return string.substring(lastDot+1);
    }

}
