package org.yunghegel.gdx.utils.format;

import com.badlogic.gdx.math.Matrix4;

import static org.yunghegel.gdx.utils.StringUtils.trimFloat;

public class StringFormatUtils {

    /**
     * Convert a camel case string to underscored capitalized string.
     * eg. thisIsStandardCamelCaseString is converted to THIS_IS_STANDARD_CAMEL_CASE_STRING
     * @param camelCase a camel case string : only letters without consecutive uppercase letters.
     * @return the transformed string or the same if not camel case.
     */
    public static String camelCaseToUnderScoreUpperCase(String camelCase)
    {
        String result = "";
        boolean prevUpperCase = false;
        for(int i=0 ; i<camelCase.length() ; i++){
            char c = camelCase.charAt(i);
            if(!Character.isLetter(c)) return camelCase;
            if(Character.isUpperCase(c)){
                if(prevUpperCase) return camelCase;
                result += "_" + c;
                prevUpperCase = true;
            }else{
                result += Character.toUpperCase(c);
                prevUpperCase = false;
            }
        }
        return result;
    }

    /**
     * Add spaces to a camel case string to make it more readable.
     */
    public static String camelCaseToReadableFormat(String camelCase)
    {
        String result = "";
        boolean prevUpperCase = false;
        for(int i=0 ; i<camelCase.length() ; i++){
            char c = camelCase.charAt(i);
            if(!Character.isLetter(c)) return camelCase;
            if(Character.isUpperCase(c) && !prevUpperCase){
                if(i>0) result += " ";
                result += c;
                prevUpperCase = true;
            }else{
                result += c;
                prevUpperCase = false;
            }
        }
        return result;
    }

    /**
     * Format a double value representing bytes to a human readable string
     * @param value - the value in bytes
     */
    public static String humanBytes(double value) {
        String suffix = " B";
        if(value > 1024){
            value /= 1024;
            suffix = " KB";
        }
        if(value > 1024){
            value /= 1024;
            suffix = " MB";
        }
        if(value > 1024){
            value /= 1024;
            suffix = " GB";
        }
        if(value > 1024){
            value /= 1024;
            suffix = " TB";
        }
        return String.valueOf(Math.round(value * 100.0) / 100.0) + suffix;
    }

    public static String formatMatrix4(Matrix4 matrix4){
        //format matrix 4 with trimmed floats

        float[] values = matrix4.getValues();
        String[] val = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            val[i] = trimFloat(values[i]);
        }
        //now format into 4 rows of 4 columns
        return "[" + val[0] + "|" + val[1] + "|" + val[2] + "|" + val[3] + "]\n" //
                + "[" + val[4] + "|" + val[5] + "|" + val[6] + "|" + val[7] + "]\n" //
                + "[" + val[8] + "|" + val[9] + "|" + val[10] + "|" + val[11] + "]\n" //
                + "[" + val[12] + "|" + val[13] + "|" + val[14] + "|" + val[15] + "]\n";
    }

    public static String floatToString (float d) {
        //round to four decimal places //TODO: editor setting for choosing rounding precession
        d = Math.round(d * 10000);
        d = d / 10000;
        String s = String.valueOf(d);

        return s;
    }

    //float to string with n decimal places
    public static String floatToString (float d, int n) {
        //check if n is valid
        if(n<0) throw new IllegalArgumentException("n must be greater than 0");

        d = Math.round(d * (float)Math.pow(10, n));
        d = d / (float)Math.pow(10, n);
        String s = String.valueOf(d);

        return s;
    }

    public static void main(String[] args) {
        System.out.println(camelCaseToUnderScoreUpperCase("thisIsStandardCamelCaseString"));
        System.out.println(camelCaseToReadableFormat("thisIsStandardCamelCaseString"));

    }


}
