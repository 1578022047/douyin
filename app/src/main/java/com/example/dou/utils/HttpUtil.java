package com.example.dou.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    public static String host = "http://192.168.43.129/";
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

    //上传视频
    public static void uploadVideoHttp(String address,String videoPath, String videoName,String imagePath,String imageName,String videoJson, okhttp3.Callback callback){
        System.out.println(imageName);
        System.out.println(imagePath);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("videoFile", videoName,
                        RequestBody.create(MediaType.parse("multipart/form-data"), new File(videoPath)))
                .addFormDataPart("imageFile", imageName,
                        RequestBody.create(MediaType.parse("multipart/form-data"), new File(imagePath)))
                .addFormDataPart("video",videoJson)
                .build();
        Request request  =new Request.Builder().url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }



    //获取五个视频
    public static void getFiveVideoHttp(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder requestBody = new FormBody.Builder();
        Request request  =new Request.Builder().url(address)
                .post(requestBody.build())
                .build();
        client.newCall(request).enqueue(callback);
    }

    //获取用户视频
    public static void getUserVideoHttp(String address,String userId,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder requestBody = new FormBody.Builder();
        requestBody.add("userId",userId);
        Request request  =new Request.Builder().url(address)
                .post(requestBody.build())
                .build();
        client.newCall(request).enqueue(callback);
    }



}
