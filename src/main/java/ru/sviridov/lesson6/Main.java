package ru.sviridov.lesson6;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .followRedirects(true)
                .retryOnConnectionFailure(true)
                .build();

        Request request = new Request.Builder()
                .url("https://geekbrains.ru")
                .build();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("geekbrains.ru")
//                .addPathSegment("courses")
//                .addQueryParameter("count", "5")
//                .addQueryParameter("filename", "1.txt")
                .build();

        Response response = client.newCall(request).execute();

        String body = response.body().string();

        System.out.println(response.code());
        System.out.println(response.headers());
        System.out.println(response.isSuccessful());
        System.out.println(response.isRedirect());
        System.out.println(response.protocol());
        System.out.println(response.receivedResponseAtMillis());


    }
}
