package com.pay.platform.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * json 解析
 */
public class JsonUtil {

    public static Gson gson = new Gson();

    /*
     * json字符串转为对象
     */
    public static <T> T parseToObject(String jsonStr, Class<T> clazz) {
        T obj = null;
        try {
            obj = gson.fromJson(jsonStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /*
     * json转mapJsonUtil.parseToObject(columnList , ColumnModel.class);
     */
    public static Map<String, Object> parseToMap(String jsonStr) {
        Map<String, Object> map = null;
        try {
            map = gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /*
     * json转mapJsonUtil.parseToObject(columnList , ColumnModel.class);
     */
    public static Map<String, String> parseToMapString(String jsonStr) {
        Map<String, String> map = null;
        try {
            map = gson.fromJson(jsonStr, new TypeToken<Map<String, String>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将jsons字符串转为List
     *
     * @param clazz
     * @return list
     */
    public static <T> List<T> parseToArray(String jsonStr, Class<T[]> clazz) {
        List<T> list = null;
        try {
            list = Arrays.asList(gson.fromJson(jsonStr, clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /*
     * 对象转为json字符串
     */
    public static String parseToJsonStr(Object o) {
        String json = "";
        try {
            Gson gson = new Gson();
            json = gson.toJson(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /*
     * 对象转Map
     */
    public static Map<String, Object> parseToMap(Object obj) {

        //1,先转成json字符串
        String jsonStr = parseToJsonStr(obj);

        //2,再转成map
        return parseToMap(jsonStr);

    }


}
