package com.example.mylibrary.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by xingzy on 2016/4/13.
 */
public class Map2Json {
    public static JSONObject map2json(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        if (!map.isEmpty()) {
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                try {
                    jsonObject.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }


    public static String maptojson(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        if (!map.isEmpty()) {
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                try {
                    jsonObject.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject.toString();
    }

    /**
     *
     * @param map
     * @return
     */
    public static String mapTojson(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        if (!map.isEmpty()) {
            Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Object> entry = entries.next();
                try {
                    jsonObject.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject.toString();
    }


}
