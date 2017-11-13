package com.example.mylibrary.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by name on 2017/10/9.
 */

public class GsonUtil { private static Gson simpleGson;
    private static Gson NullGson;

    private GsonUtil() {
    }

    public synchronized static Gson creatSipmleGson() {
        if (simpleGson == null) {
            simpleGson = new Gson();
        }
        return simpleGson;
    }

    public synchronized static Gson creatserializeNullsGson() {
        if (NullGson == null) {
            NullGson = new GsonBuilder().serializeNulls().create();
        }
        return NullGson;
    }

}
