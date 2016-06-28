package com.ajeet_meena.movieon.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ajeet_meena.movieon.Activities.MainActivity;
import com.ajeet_meena.movieon.BaseClasses.BaseRecyclerAdapter;
import com.ajeet_meena.movieon.Models.Movie;
import com.ajeet_meena.movieon.R;
import com.ajeet_meena.movieon.Utils.Constants;
import com.ajeet_meena.movieon.Utils.DisplayUtil;

import java.util.ArrayList;

/**
 * Created by Ajeet Kumar Meena on 18-06-2016.
 */
public class MovieRecyclerAdapter extends BaseRecyclerAdapter {

    private ArrayList<Movie> movies = new ArrayList<>();
    private boolean isSimilarAdapter;

    public MovieRecyclerAdapter(Context context, ArrayList<Movie> movies, boolean isSimilarAdapter) {
        super(context);
        this.movies = movies;
        this.isSimilarAdapter = isSimilarAdapter;
        if (!isSimilarAdapter) {
            mViewType = RECYCLER_VIEW_TYPE_HEADER;

        }
    }


    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    protected ItemHolder getItemHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    protected HeaderHolder getHeaderHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    protected FooterHolder getFooterHolder(View view) {
        return null;
    }

    @Override
    public void onBindViewItemHolder(final ItemHolder holder, final int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.setupViews(movies.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).attachEditNoteFragment(movies.get(position));
            }
        });
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
        return R.layout.row_movie;
    }

    @Override
    protected int getHeaderLayoutId() {
        return R.layout.empty_header;
    }

    @Override
    protected int getFooterLayoutId() {
        return 0;
    }

    private class HeaderViewHolder extends HeaderHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class ItemViewHolder extends ItemHolder {

        private ImageView poster;
        private TextView title, rating, releaseDate;
        private CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            title = (TextView) itemView.findViewById(R.id.title);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            rating = (TextView) itemView.findViewById(R.id.rating);
            releaseDate = (TextView) itemView.findViewById(R.id.release_date);
        }

        public void setupViews(Movie movie) {
            if (isSimilarAdapter) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                    cardView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            cardView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            cardView.getLayoutParams().width = DisplayUtil.dpToPx(mContext, 200);
                            cardView.requestLayout();
                        }
                    });
                }
            }
            rating.setText(movie.getVoteAverage());
            Picasso.with(mContext).load(Constants.BASE_IMAGE_URL + movie.getPosterPath())
                    .into(poster);
            title.setText(movie.getTitle());
            releaseDate.setText(movie.getReleaseDate());
        }

    }
}
