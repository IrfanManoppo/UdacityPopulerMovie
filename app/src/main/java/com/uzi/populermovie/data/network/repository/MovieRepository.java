package com.uzi.populermovie.data.network.repository;

import com.uzi.populermovie.data.model.Movie;

import java.util.List;

import io.reactivex.Observable;
/**
 * Created by uzi on 24/06/17.
 * Email : fauzisholichin@gmail.com
 */


public interface MovieRepository {
    Observable<List<Movie>> getPopularMovie(int page);

    Observable<List<Movie>> getTopRatedMovie(int page);
}
