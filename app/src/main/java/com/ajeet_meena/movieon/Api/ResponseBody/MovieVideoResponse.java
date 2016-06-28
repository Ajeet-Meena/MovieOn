package com.ajeet_meena.movieon.Api.ResponseBody;

import com.ajeet_meena.movieon.Models.Video;

import java.util.ArrayList;

/**
 * Created by Ajeet Kumar Meena on 19-06-2016.
 */

public class MovieVideoResponse {
    private int id;
    private ArrayList<Video> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Video> getResults() {
        return results;
    }

    public void setResults(ArrayList<Video> results) {
        this.results = results;
    }
}
