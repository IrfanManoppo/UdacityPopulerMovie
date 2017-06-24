package com.uzi.populermovie.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
/**
 * Created by uzi on 24/06/17.
 * Email : fauzisholichin@gmail.com
 */


public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(T item);
}
