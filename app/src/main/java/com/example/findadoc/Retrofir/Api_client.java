package com.example.findadoc.Retrofir;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_client {
   // public static final String BASE_URL = "http://itdevelopmentservices.com/finddoctor/api/";
    public static final String BASE_URL = "http://itdevelopmentservices.com/finddoctortest/api/";
    //public static final String BASE_URL = "http://itdevelopmentservices.com/finddoctortest/api/";
    public static final String BASE_IMAGE="http://itdevelopmentservices.com/finddoctortest/upload/images/";
    public static final String PAYMENT = "https://testom.orange.bf:9008/";
//    private ServiceHelper() {
//        httpClient = new OkHttpClient.Builder();
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClient.interceptors().add(interceptor);
//        Retrofit retrofit = createAdapter().build();
//        service = retrofit.create(IService.class);
//    }

    private static Retrofit retrofit = null;
    private static Api api;
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Api getClient() {
        if (api == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            Api api = retrofit.create(Api.class);
            return api;
        } else
            return api;


    }


}

