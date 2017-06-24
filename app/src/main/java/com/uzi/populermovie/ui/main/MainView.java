package com.uzi.populermovie.ui.main;

import com.uzi.populermovie.data.model.Movie;

import java.util.List;
/**
 * Created by uzi on 24/06/17.
 * Email : fauzisholichin@gmail.com
 */


public interface MainView {
    void showLoading(boolean show);

    void displayMovies(List<Movie> results, int page);

    void showError(String message);
}
