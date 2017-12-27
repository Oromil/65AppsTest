package com.oromil.a65appstest.util;

/**
 * Created by Oromil on 21.12.2017.
 */

public class StringUtils {

    public static String format(String s){
        return s.substring(0,1).toUpperCase()+s.substring(1).toLowerCase();
    }
}
