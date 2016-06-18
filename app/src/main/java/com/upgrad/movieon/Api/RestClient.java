package com.upgrad.movieon.Api;


import com.upgrad.movieon.MyApplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static final String BASE_URL = "https://theboxprod.herokuapp.com/";
    private APIService apiService;

    public RestClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(MyApplication.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);

    }

    public APIService getApiService() {
        return apiService;
    }
}
