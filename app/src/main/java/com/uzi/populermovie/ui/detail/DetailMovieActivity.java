package com.uzi.populermovie.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.uzi.populermovie.R;
import com.uzi.populermovie.data.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by uzi on 24/06/17.
 * Email : fauzisholichin@gmail.com
 */

public class DetailMovieActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.img_backdrop)
    ImageView imageView;
    @BindView(R.id.poster)
    ImageView poster;
    private Movie movie;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_rating)
    TextView tvRanting;
    @BindView(R.id.tv_overview)
    TextView tvOverview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        initData();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(movie.getTitle());
                    isShow = true;
                    poster.setVisibility(View.GONE);
                } else if (isShow) {
                    poster.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void initData() {
        movie = new Gson().fromJson(getIntent().getStringExtra("movie"), Movie.class);
        showImage(movie.getBackdrop_path());
        showImagePoster(movie.getPoster_path());
        tvTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getFormattedDate());
        tvRanting.setText("Rating");
        tvOverview.setText(movie.getOverview());

    }

    private void showImagePoster(String poster_path) {
        final String finalUrl = "http://image.tmdb.org/t/p/w342/" + poster_path;
        Picasso.with(this)
                .load(finalUrl)
                .placeholder(R.drawable.ic_error_outline_grey_900_48dp)
                .error(R.drawable.ic_error_outline_grey_900_48dp)
                .into(poster);
    }

    private void showImage(String backdrop_path) {
        final String finalUrl = "http://image.tmdb.org/t/p/w342/" + backdrop_path;
        Picasso.with(this)
                .load(finalUrl)
                .placeholder(R.drawable.ic_error_outline_grey_900_48dp)
                .error(R.drawable.ic_error_outline_grey_900_48dp)
                .into(imageView);
    }
}
