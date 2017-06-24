package com.uzi.populermovie.ui.main;


import com.uzi.populermovie.data.model.Movie;
import com.uzi.populermovie.data.network.repository.MovieRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by uzi on 24/06/17.
 * Email : fauzisholichin@gmail.com
 */


public class MainPresenter {
    private MovieRepository movieRepository;
    private MainView view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int page = 0;

    public MainPresenter(MovieRepository movieRepository, MainView view) {
        this.movieRepository = movieRepository;
        this.view = view;
    }

    public void getMovie(int sort, int page) {
        view.showLoading(true);
        Observable<List<Movie>> observableMovies;
        this.page = page;
        if (sort == MainActivity.POPULAR) {
            observableMovies = movieRepository.getPopularMovie(page);
        } else {
            observableMovies = movieRepository.getTopRatedMovie(page);
        }

        compositeDisposable.add(observableMovies.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    view.showLoading(false);
                    if (movies != null && movies.size() > 0) {
                        view.displayMovies(movies, page);
                    }
                }, throwable -> {
                    view.showLoading(false);
                    view.showError(throwable.getLocalizedMessage());
                }));
    }
}
