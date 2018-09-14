package com.lakala.appcomponent.updatelib.util;

public class Utils {

    public static String getMethod(Method method) {
        if (Method.POST == method) {
            return "POST";
        } else if (Method.DELETE == method) {
            return "DELETE";
        } else if (Method.PUT == method) {
            return "PUT";
        } else {
            return "GET";
        }
    }

}
