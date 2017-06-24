package com.uzi.populermovie.ui.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.uzi.populermovie.R;
import com.uzi.populermovie.data.model.Movie;
import com.uzi.populermovie.ui.base.BaseViewHolder;

/**
 * Created by uzi on 24/06/17.
 * Email : fauzisholichin@gmail.com
 */

public class MovieListViewHolder extends BaseViewHolder<Movie> {

    private final Context mContext;
    private final View view;
    private final ImageView ivMovie;
    private final MainAdapter.MainListener mListener;

    public MovieListViewHolder(Context mContext, View v, MainAdapter.MainListener mainListener) {
        super(v);
        this.mContext = mContext;
        this.view = v;
        this.mListener = mainListener;
        ivMovie = (ImageView) v.findViewById(R.id.ivMovie);
    }

    @Override
    public void bind(Movie item) {
        ivMovie.setOnClickListener(v -> mListener.onClickMovie(item));
        showImage(item.getPoster_path());
    }

    private void showImage(String poster_path) {
        final String finalUrl = "http://image.tmdb.org/t/p/w342/" + poster_path;
        Picasso.with(view.getContext())
                .load(finalUrl)
                .placeholder(R.drawable.ic_error_outline_grey_900_48dp)
                .error(R.drawable.ic_error_outline_grey_900_48dp)
                .into(ivMovie);
    }
}
