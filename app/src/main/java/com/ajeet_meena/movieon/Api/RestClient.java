package com.ajeet_meena.movieon.Api;


import com.ajeet_meena.movieon.MyApplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ajeet Kumar Meena on 18-06-2016.
 */
public class RestClient {

    private static final String BASE_URL = "http://api.themoviedb.org/";
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
