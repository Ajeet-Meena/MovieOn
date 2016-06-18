package com.upgrad.movieon.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.upgrad.movieon.Activities.MainActivity;
import com.upgrad.movieon.Adapters.NotesRecyclerAdapter;
import com.upgrad.movieon.BaseClasses.BaseActivity;
import com.upgrad.movieon.Listeners.AppBarObserver;
import com.upgrad.movieon.Models.Movie;
import com.upgrad.movieon.R;

import java.util.ArrayList;

/**
 * Created by Ajeet Kumar Meena on 10-06-2016.
 */
public class GridFragment extends Fragment implements AppBarObserver.OnOffsetChangeListener {

    public static final String TAG = GridFragment.class.getSimpleName();
    private View rootView;
    private RecyclerView recyclerView;
    private NotesRecyclerAdapter notesRecyclerAdapter;
    private FloatingActionButton floatingActionButton;
    private AppBarObserver appBarObserver;
    private LinearLayout linearLayout;
    private ArrayList<Movie> movies = new ArrayList<>();

    public GridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_notes, container, false);
            initView();
            setupRecyclerView();
            return rootView;
        }
        return rootView;
    }

    private void initView() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.holder);
        AppBarLayout appBarLayout = (AppBarLayout) getActivity()
                .findViewById(R.id.app_bar_layout);
        if (appBarLayout != null) {
            appBarObserver = AppBarObserver.observe(appBarLayout);
            appBarObserver.addOffsetChangeListener(this);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class).putExtra(MainActivity.EXTRA_ATTACH_FRAGMENT_NO, 1));
            }
        });
    }

    private void setupRecyclerView() {
        if (movies.size() == 0) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
        notesRecyclerAdapter = new NotesRecyclerAdapter(getActivity(), movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(notesRecyclerAdapter);
    }

    @Override
    public void onOffsetChange(int offset, int dOffset) {
        floatingActionButton.setTranslationY(-offset);
    }


    @Override
    public void onResume() {
        super.onResume();
        movies.clear();
        setupRecyclerView();
        ((MainActivity) getActivity()).getToolbarActionImageView().setVisibility(View.GONE);
        ((MainActivity) getActivity()).getToolbarActionImageView().setOnClickListener(null);
        ((MainActivity) getActivity()).getToolbar().setTitle("MovieOn");
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((BaseActivity) getActivity()).getContentView().getWindowToken(), 0);
    }
}
