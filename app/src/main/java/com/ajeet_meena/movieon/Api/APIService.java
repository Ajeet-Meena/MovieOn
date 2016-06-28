package com.ajeet_meena.movieon.Api;

import com.ajeet_meena.movieon.Api.ResponseBody.DiscoverResponse;
import com.ajeet_meena.movieon.Api.ResponseBody.MovieImageResponse;
import com.ajeet_meena.movieon.Api.ResponseBody.MovieVideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ajeet Kumar Meena on 18-06-2016.
 */
public interface APIService {
    @GET("/3/discover/movie")
    Call<DiscoverResponse> discover(
            @Query("api_key") String apiKey,
            @Query("page") int page,
            @Query("sort_by") String sortBy
    );

    @GET("/3/movie/{id}/images")
    Call<MovieImageResponse> getImages(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

    @GET("/3/movie/{id}/videos")
    Call<MovieVideoResponse> getYoutubeId(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

    @GET("/3/movie/{id}/similar")
    Call<DiscoverResponse> getSimilar(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

}
