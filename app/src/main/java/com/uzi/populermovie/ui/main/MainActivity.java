package com.uzi.populermovie.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uzi.populermovie.R;
import com.uzi.populermovie.data.model.Movie;
import com.uzi.populermovie.data.network.TheMovieDbServices;
import com.uzi.populermovie.data.network.repository.MovieRepository;
import com.uzi.populermovie.data.network.repository.MovieRepositoryImpl;
import com.uzi.populermovie.ui.detail.DetailMovieActivity;
import com.uzi.populermovie.utils.EndlessRecyclerViewScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by uzi on 24/06/17.
 * Email : fauzisholichin@gmail.com
 */

public class MainActivity extends AppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener, MainAdapter.MainListener {

    public static final int POPULAR = 0;
    public static final int TOP_RATED = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_list_movies)
    RecyclerView rvListMovies;
    @BindView(R.id.tv_error)
    TextView errorText;
    @BindView(R.id.loading)
    ProgressBar loading;


    private MovieRepository movieRepository;
    private TheMovieDbServices movieDbServices;
    private EndlessRecyclerViewScrollListener scrollListener;

    private MainPresenter presenter;
    private int selectedSort = 1;
    private MainAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initRecyclerView();
        initService();
    }

    private void initService() {
        movieDbServices = TheMovieDbServices.Creator.instance();
        movieRepository = new MovieRepositoryImpl(movieDbServices);
        presenter = new MainPresenter(movieRepository, this);
        presenter.getMovie(POPULAR, 1);
    }

    private void initRecyclerView() {
        final int columnCount = getResources().getInteger(R.integer.grid_count);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, columnCount);
        rvListMovies.setLayoutManager(layoutManager);
        mAdapter = new MainAdapter(this, this);
        rvListMovies.setAdapter(mAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (page == 1) {
                    page = 2;
                } else {
                    page++;
                }
                presenter.getMovie(POPULAR, page);
            }
        };
        rvListMovies.addOnScrollListener(scrollListener);

        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);
            presenter.getMovie(POPULAR, 1);
        });
    }

    @Override
    public void showLoading(boolean show) {
        errorText.setVisibility(View.GONE);
        if (show) {
            rvListMovies.setVisibility(View.VISIBLE);
            loading.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        } else {
            refreshLayout.setRefreshing(false);
            loading.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayMovies(List<Movie> results, int page) {
        if (page == 1 && results.isEmpty()) {
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
        } else if (page == 1 && !results.isEmpty()) {
            mAdapter.addAll(results);
            mAdapter.notifyDataSetChanged();
        } else {
            for (Movie movie : results) {
                mAdapter.add(movie);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        errorText.setVisibility(View.VISIBLE);
        rvListMovies.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
        presenter.getMovie(selectedSort, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort_popular:
                selectedSort = POPULAR;
                presenter.getMovie(POPULAR, 1);
                break;
            case R.id.menu_sort_toprated:
                selectedSort = TOP_RATED;
                presenter.getMovie(TOP_RATED, 1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickMovie(Movie item) {
        Toast.makeText(this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, DetailMovieActivity.class);
        intent.putExtra("movie", new Gson().toJson(item));
        startActivity(intent);
    }
}
