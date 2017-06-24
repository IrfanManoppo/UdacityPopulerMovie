package com.uzi.populermovie.data.network.repository;

import com.uzi.populermovie.data.config.MovieConfig;
import com.uzi.populermovie.data.network.TheMovieDbServices;
import com.uzi.populermovie.data.model.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by uzi on 24/06/17.
 * Email : fauzisholichin@gmail.com
 */


public class MovieRepositoryImpl implements MovieRepository {

    private TheMovieDbServices services;

    public MovieRepositoryImpl(TheMovieDbServices services) {
        this.services = services;
    }

    @Override
    public Observable<List<Movie>> getPopularMovie(int page) {
        return services.getPopularMovie(MovieConfig.THEMOVIEDB_KEY, page).flatMap(movieResponse -> Observable.just(movieResponse.results));
    }


    @Override
    public Observable<List<Movie>> getTopRatedMovie(int page) {
        return services.getTopRatedMovie(MovieConfig.THEMOVIEDB_KEY, page).flatMap(movieResponse -> Observable.just(movieResponse.results));
    }
}
