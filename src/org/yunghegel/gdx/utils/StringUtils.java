package org.yunghegel.gdx.utils;

import com.badlogic.gdx.math.Vector3;

public class StringUtils {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public enum Ansi {
        BLACK(ANSI_BLACK),
        RED(ANSI_RED),
        GREEN(ANSI_GREEN),
        YELLOW(ANSI_YELLOW),
        BLUE(ANSI_BLUE),
        PURPLE(ANSI_PURPLE),
        CYAN(ANSI_CYAN),
        WHITE(ANSI_WHITE);

        private String ansi;

        Ansi(String ansi) {
            this.ansi = ansi;
        }

        public String getAnsi() {
            return ansi;
        }
    }

    public static String colorize(String string, Ansi color) {
        return color.getAnsi() + string + ANSI_RESET;
    }

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
