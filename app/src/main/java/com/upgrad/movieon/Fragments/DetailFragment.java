package com.upgrad.movieon.Fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.upgrad.movieon.Adapters.MovieRecyclerAdapter;
import com.upgrad.movieon.Api.APIService;
import com.upgrad.movieon.Api.ResponseBody.DiscoverResponse;
import com.upgrad.movieon.Api.ResponseBody.MovieImageResponse;
import com.upgrad.movieon.Api.ResponseBody.MovieVideoResponse;
import com.upgrad.movieon.BaseClasses.BaseActivity;
import com.upgrad.movieon.Models.Image;
import com.upgrad.movieon.Models.Movie;
import com.upgrad.movieon.Models.Video;
import com.upgrad.movieon.MyApplication;
import com.upgrad.movieon.R;
import com.upgrad.movieon.Utils.Constants;
import com.upgrad.movieon.Utils.CoreGsonUtils;
import com.upgrad.movieon.Views.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Ajeet Kumar Meena on 18-06-2016.
 */
public class DetailFragment extends Fragment {


    private View rootView;
    private TextView title, releaseDate, rating, description;
    private Movie movie;
    private ProgressBar progressBar, progressBarSimilar;
    private SliderLayout sliderLayout;
    private FloatingActionButton floatingActionButton;
    private static final String EXTRA_MOVIE_OBJECT = "extra_movie_object";
    public static final String TAG = MyApplication.class.getSimpleName();
    private String youtubeId;
    private RecyclerView recyclerView;
    private TextView noImageFound, noSimilarMoviesFound;

    private void initViews() {
        title = (TextView) rootView.findViewById(R.id.title);
        releaseDate = (TextView) rootView.findViewById(R.id.release_date);
        rating = (TextView) rootView.findViewById(R.id.rating);
        description = (TextView) rootView.findViewById(R.id.description);
        title.setText(movie.getTitle());
        releaseDate.setText(movie.getReleaseDate());
        rating.setText(movie.getVoteAverage());
        if(movie.getOverview() == null || movie.getOverview().isEmpty()) {
            description.setText("No overview found.");
        }else {
            description.setText(movie.getOverview());
        }
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        sliderLayout = (SliderLayout) rootView.findViewById(R.id.slider);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        floatingActionButton.setVisibility(View.GONE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (youtubeId != null && !youtubeId.isEmpty()) {
                    watchOnYoutube(youtubeId);
                }
            }
        });
        progressBarSimilar = (ProgressBar) rootView.findViewById(R.id.progress_bar_similar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        noImageFound = (TextView) rootView.findViewById(R.id.no_images);
        noSimilarMoviesFound = (TextView) rootView.findViewById(R.id.no_similar_movies);
    }


    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment getInstance(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MOVIE_OBJECT, CoreGsonUtils.toJson(movie));
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            initVariables();
            rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            initViews();
            getImages();
            getYouTubeId();
            getSimilar();
        }
        return rootView;
    }

    private void getYouTubeId() {
        MyApplication.getAPIService().getYoutubeId(Integer.parseInt(movie.getId()), Constants.API_KEY).enqueue(
                new Callback<MovieVideoResponse>() {
                    @Override
                    public void onResponse(Call<MovieVideoResponse> call, Response<MovieVideoResponse> response) {
                        if (response.body() != null) {
                            boolean haveYouTubeVideo = false;
                            for (Video video : response.body().getResults()) {
                                if (video.getSite().equals("YouTube")) {
                                    youtubeId = video.getKey();
                                    haveYouTubeVideo = true;
                                }
                            }
                            if (haveYouTubeVideo) {
                                floatingActionButton.setVisibility(View.VISIBLE);
                            } else {
                                floatingActionButton.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieVideoResponse> call, Throwable t) {
                        floatingActionButton.setVisibility(View.GONE);
                    }
                }
        );
    }

    private void initVariables() {
        this.movie = CoreGsonUtils.fromJson(getArguments().getString(EXTRA_MOVIE_OBJECT), Movie.class);
    }


    @Override
    public void onResume() {
        super.onResume();
        ((BaseActivity) getActivity()).getToolbar().setTitle(movie.getTitle());
    }

    private void getImages() {
        MyApplication.getAPIService().getImages(Integer.parseInt(movie.getId()), Constants.API_KEY)
                .enqueue(new Callback<MovieImageResponse>() {
                    @Override
                    public void onResponse(Call<MovieImageResponse> call, Response<MovieImageResponse> response) {
                        if (response.body() != null) {
                            if (response.body().getBackdrops().isEmpty()) {
                                noImageFound.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            } else {
                                HashMap<String, String> url_maps = new HashMap<>();
                                for (Image image : response.body().getBackdrops()) {
                                    url_maps.put("Up Voated By: " + image.getVoteCount(), Constants.BASE_BACKDROP_IMAGE_URL + image.getFilePath());
                                }
                                setupImageSlider(url_maps);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<MovieImageResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void setupImageSlider(HashMap<String, String> url_maps) {
        sliderLayout.setVisibility(View.INVISIBLE);
        sliderLayout.stopAutoCycle();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                sliderLayout.setVisibility(View.VISIBLE);
            }
        },1200);
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
    }

    private void watchOnYoutube(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }

    private void setupRecyclerView(ArrayList<Movie> movies) {
        if (getActivity() != null) {
            MovieRecyclerAdapter movieRecyclerAdapter = new MovieRecyclerAdapter(getActivity(), movies, true);
            recyclerView.addItemDecoration(new SpaceItemDecoration(0, 8, this.getActivity().getApplicationContext()));
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setItemViewCacheSize(20);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.setAdapter(movieRecyclerAdapter);
        }
    }

    private void getSimilar() {
        MyApplication.getAPIService().getSimilar(Integer.parseInt(movie.getId()), Constants.API_KEY).enqueue(
                new Callback<DiscoverResponse>() {
                    @Override
                    public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
                        progressBarSimilar.setVisibility(View.GONE);
                        if (response.body() != null) {
                            if (response.body().getResults().isEmpty()) {
                                noSimilarMoviesFound.setVisibility(View.VISIBLE);
                            } else {
                                setupRecyclerView(response.body().getResults());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DiscoverResponse> call, Throwable t) {
                        progressBarSimilar.setVisibility(View.GONE);

                    }
                }
        );
    }
}
