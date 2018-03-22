package com.alfred.framework.module.model.home;

import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alfred.framework.myframework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/15.
 */

public class DynamicHeaderViewBinder extends ItemViewBinder<String, DynamicHeaderViewBinder.DynamicHeaderViewHolder> {
    private View headerView;
    @NonNull
    @Override
    protected DynamicHeaderViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DynamicHeaderViewHolder(headerView);
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }

    @Override
    protected void onBindViewHolder(@NonNull DynamicHeaderViewHolder holder, @NonNull String item) {

    }

    static class DynamicHeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_dynamic_header)
        public ImageView itemDynamicHeader;
        public DynamicHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
