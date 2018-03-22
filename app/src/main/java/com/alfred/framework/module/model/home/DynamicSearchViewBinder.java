package com.alfred.framework.module.model.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.alfred.framework.myframework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/11.
 */

public class DynamicSearchViewBinder extends ItemViewBinder<DynamicSearch, DynamicSearchViewBinder.DynamicSearchViewHolder> {



    @NonNull
    @Override
    protected DynamicSearchViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DynamicSearchViewHolder(inflater.inflate(R.layout.item_dynamic_search, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull DynamicSearchViewHolder holder, @NonNull DynamicSearch item) {
    }

    static class DynamicSearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_dynamic_search)
        public SearchView itemDynamicSearch;


        public DynamicSearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
