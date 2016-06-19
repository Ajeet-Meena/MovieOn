package com.upgrad.movieon.Api.ResponseBody;

import com.upgrad.movieon.Api.ApiResponse;
import com.upgrad.movieon.Models.Image;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ajeet Kumar Meena on 19-06-2016.
 */

public class MovieImageResponse extends ApiResponse implements Serializable {
    private int id;
    private ArrayList<Image> posters;
    private ArrayList<Image> backdrops;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Image> getPosters() {
        return posters;
    }

    public void setPosters(ArrayList<Image> posters) {
        this.posters = posters;
    }

    public ArrayList<Image> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(ArrayList<Image> backdrops) {
        this.backdrops = backdrops;
    }
}
