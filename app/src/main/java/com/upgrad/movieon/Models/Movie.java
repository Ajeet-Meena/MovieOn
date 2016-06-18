package com.upgrad.movieon.Models;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by Ajeet Kumar Meena on 10-06-2016.
 */
public class Movie {

    private int id;
    private String title;
    private String text;
    private Date date;

    public Movie() {
    }

    public Movie(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = Calendar.getInstance().getTime();
    }

    public Movie(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        Movie movie = (Movie) o;
        return this.getId() == movie.getId()
                && this.getText().equals(movie.getText())
                && this.getTitle().equals(movie.getTitle());
    }
}
