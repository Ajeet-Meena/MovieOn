package com.upgrad.movieon.Api.ResponseBody;

import com.google.gson.annotations.SerializedName;
import com.upgrad.movieon.Api.ApiResponse;
import com.upgrad.movieon.Models.Movie;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ajeet Kumar Meena on 19-06-2016.
 */

public class DiscoverResponse extends ApiResponse implements Serializable {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private ArrayList<Movie> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }
}
