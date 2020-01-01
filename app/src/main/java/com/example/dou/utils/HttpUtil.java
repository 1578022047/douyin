package com.example.dou.utils;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    public static void sendGetHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);

    }
//可以以map的形式传入多个参数
    public static void sendPostHttpRequest(String address, Map<String,String> mapData, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder requestBody = new FormBody.Builder();
        for (String key:mapData.keySet()){
            requestBody.add(key,mapData.get(key));
        }
        Request request  =new Request.Builder().url(address)
                .post(requestBody.build())
                .build();
        client.newCall(request).enqueue(callback);
    }

}
