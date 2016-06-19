package com.upgrad.movieon.Fragments;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.upgrad.movieon.Activities.MainActivity;
import com.upgrad.movieon.Adapters.MovieRecyclerAdapter;
import com.upgrad.movieon.Api.ResponseBody.DiscoverResponse;
import com.upgrad.movieon.Listeners.AppBarObserver;
import com.upgrad.movieon.Models.Movie;
import com.upgrad.movieon.MyApplication;
import com.upgrad.movieon.R;
import com.upgrad.movieon.Utils.Constants;
import com.upgrad.movieon.Views.SpaceItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ajeet Kumar Meena on 18-06-2016.
 */
public class GridFragment extends Fragment implements AppBarObserver.OnOffsetChangeListener {

    public static final String TAG = GridFragment.class.getSimpleName();
    private View rootView;
    private RecyclerView recyclerView;
    private MovieRecyclerAdapter movieRecyclerAdapter;
    private FloatingActionButton floatingActionButton;
    private AppBarObserver appBarObserver;
    private LinearLayout linearLayoutHolder;
    private ProgressBar progressBar;
    private int pageNo = 1;
    private ArrayList<Movie> movies = new ArrayList<>();
    private boolean shouldLoadMore = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private StaggeredGridLayoutManager layoutManager;
    private int sortKey = 1;

    public GridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
            initView();
            discover(sortKey);
            setupRecyclerView();
            return rootView;
        }
        return rootView;
    }

    private void discover(int sortKey) {
        MyApplication.getAPIService().discover(Constants.API_KEY, pageNo, Constants.FILTERS_KEY[sortKey])
                .enqueue(
                        new Callback<DiscoverResponse>() {
                            @Override
                            public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
                                linearLayoutHolder.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                if (response.body() != null) {
                                    shouldLoadMore = true;
                                    addMovies(response.body().getResults());
                                }
                            }

                            @Override
                            public void onFailure(Call<DiscoverResponse> call, Throwable t) {
                                linearLayoutHolder.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                        }
                );
    }

    private void initView() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        linearLayoutHolder = (LinearLayout) rootView.findViewById(R.id.holder);
        AppBarLayout appBarLayout = (AppBarLayout) getActivity()
                .findViewById(R.id.app_bar_layout);
        if (appBarLayout != null) {
            appBarObserver = AppBarObserver.observe(appBarLayout);
            appBarObserver.addOffsetChangeListener(this);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                        .title("Select Filter")
                        .items(Constants.FILTERS)
                        .itemsCallbackSingleChoice(sortKey, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                progressBar.setVisibility(View.VISIBLE);
                                movieRecyclerAdapter.getMovies().clear();
                                movieRecyclerAdapter.notifyDataSetChanged();
                                sortKey = which;
                                discover(sortKey);
                                return true;
                            }
                        })
                        .positiveText("Filter")
                        .build();
                materialDialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;
                materialDialog.show();
            }
        });
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        linearLayoutHolder.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        movieRecyclerAdapter = new MovieRecyclerAdapter(getActivity(), movies, false);
        layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.addItemDecoration(new SpaceItemDecoration(6, 6, this.getActivity().getApplicationContext()));
        recyclerView.setAdapter(movieRecyclerAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPositions(null)[0];

                    if (shouldLoadMore) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            shouldLoadMore = false;
                            progressBar.setVisibility(View.VISIBLE);
                            pageNo++;
                            discover(sortKey);
                        }
                    }
                }
            }
        });
    }

    private void addMovies(ArrayList<Movie> movies) {
        ArrayList<Movie> tempMovies = movies;
        movieRecyclerAdapter.getMovies().addAll(tempMovies);
        movieRecyclerAdapter.notifyItemRangeInserted(movieRecyclerAdapter.getMovies().size(),movieRecyclerAdapter.getMovies().size() + tempMovies.size());
        movieRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onOffsetChange(int offset, int dOffset) {
        floatingActionButton.setTranslationY(-offset);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getToolbarActionImageView().setVisibility(View.GONE);
        ((MainActivity) getActivity()).getToolbarActionImageView().setOnClickListener(null);
        ((MainActivity) getActivity()).getToolbar().setTitle("MovieOn");
    }
}
