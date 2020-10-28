package pers.ryan.nio;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyClient {
    public static void main(String[] args) throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://127.0.0.1:8801").build();
        Response response = okHttpClient.newCall(request).execute();
        System.out.println(response.body().string());
    }
}
