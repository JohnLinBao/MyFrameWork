package com.alfred.framework.module.model.home.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfred.framework.module.model.home.DynamicFooter;
import com.alfred.framework.myframework.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/18.
 */

public class DynamicFooterViewBinder extends ItemViewBinder<DynamicFooter, DynamicFooterViewBinder.DynamicFooterViewHolder>{

    public Context context;

    public View getFooterView() {
        return footerView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    public View footerView;
    @NonNull
    @Override
    protected DynamicFooterViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        footerView = getFooterView();
        return new DynamicFooterViewHolder(footerView);
    }

    @Override
    protected void onBindViewHolder(@NonNull DynamicFooterViewHolder holder, @NonNull DynamicFooter item) {

    }


    static class DynamicFooterViewHolder extends RecyclerView.ViewHolder{
        public DynamicFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
