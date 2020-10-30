package com.example.muproject.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://reqres.in/api/";
    private static final String XTK_URL = "http://www.xinghengedu.com/";
    public static MuService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(MuService.class);
    }

    public static MuService getXtkApiService(){
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(XTK_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit.create(MuService.class);
    }
}
