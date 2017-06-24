package com.uzi.populermovie.ui.main;

import android.content.Context;
import android.view.View;

import com.uzi.populermovie.R;
import com.uzi.populermovie.data.model.Movie;
import com.uzi.populermovie.ui.base.BaseRecyclerViewAdapter;

/**
 * Created by uzi on 24/06/17.
 * Email : fauzisholichin@gmail.com
 */

public class MainAdapter extends BaseRecyclerViewAdapter<Movie, MovieListViewHolder> {

    private MainListener mainListener;

    public MainAdapter(Context context, MainListener mainListener) {
        super(context);
        this.mainListener = mainListener;
    }

    @Override
    protected MovieListViewHolder initViewHolder(View view) {
        return new MovieListViewHolder(mContext, view, mainListener);
    }

    @Override
    protected int setItemView(int viewType) {
        return R.layout.item_movie;
    }

    public interface MainListener {
        void onClickMovie(Movie item);
    }
}
