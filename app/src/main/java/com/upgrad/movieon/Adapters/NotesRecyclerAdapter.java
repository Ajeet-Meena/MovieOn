package com.upgrad.movieon.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.upgrad.movieon.Activities.MainActivity;
import com.upgrad.movieon.BaseClasses.BaseRecyclerAdapter;
import com.upgrad.movieon.Fragments.EditFragment;
import com.upgrad.movieon.Models.Movie;
import com.upgrad.movieon.R;

import java.util.ArrayList;

/**
 * Created by Ajeet Kumar Meena on 10-06-2016.
 */
public class NotesRecyclerAdapter extends BaseRecyclerAdapter {

    private ArrayList<Movie> movies = new ArrayList<>();

    public NotesRecyclerAdapter(Context context, ArrayList<Movie> movies) {
        super(context);
        this.movies = movies;
    }

    @Override
    protected ItemHolder getItemHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    protected HeaderHolder getHeaderHolder(View view) {
        return null;
    }

    @Override
    protected FooterHolder getFooterHolder(View view) {
        return null;
    }

    @Override
    public void onBindViewItemHolder(final ItemHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
    }

    @Override
    public void onBindViewHeaderHolder(HeaderHolder holder, int position) {

    }

    @Override
    public void onBindViewFooterHolder(FooterHolder holder, int position) {

    }

    @Override
    public int getItemsCount() {
        return movies.size();
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.row_notes;
    }

    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getFooterLayoutId() {
        return 0;
    }

    private class ItemViewHolder extends ItemHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
