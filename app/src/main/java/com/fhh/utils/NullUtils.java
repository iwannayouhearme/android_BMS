package com.fhh.utils;

import java.util.List;
import java.util.Map;

/**
 * @author: nicai
 * @email : nicaicai
 * @blog : nicaicaicai
 * @time : 2018/4/4
 * @desc :
 */

public class NullUtils {

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(String str) {
        if (str == null || "".equals(str) || "null".equalsIgnoreCase(str)) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNull(Map map) {
        return map == null || map.isEmpty();
    }


    public static String filterNull(String str, String _default, String tail) {
        if (str == null || "".equals(str) || "null".equalsIgnoreCase(str)) {
            return _default;
        } else {
            if (tail != null) {
                str += tail;
            }
            return str;
        }
    }

    public static String filterNull(String str, String _default) {
        return filterNull(str, _default, null);
    }

    public static String filterNull(String str) {
        return filterNull(str, "", null);
    }

    public static String filterEmpty(String str, String _default, String tail) {
        if (str == null || "".equals(str)) {
            return _default;
        } else {
            if (tail != null) {
                str += tail;
            }
            return str;
        }
    }

    public static String filterEmpty(String str, String _default) {
        return filterEmpty(str, _default, null);
    }

    public static String filterEmpty(String str) {
        return filterEmpty(str, "", null);
    }


}
