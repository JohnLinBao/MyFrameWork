package com.alfred.framework.module.model.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alfred.framework.module.model.ReleaseDynamicPicture;
import com.alfred.framework.myframework.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/10.
 */

public class ReleaseDynamicViewBinder extends ItemViewBinder<ReleaseDynamicPicture, ReleaseDynamicViewBinder.ReleaseDynamicViewHolder> {

    public Context context;

    @NonNull
    @Override
    protected ReleaseDynamicViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        this.context = parent.getContext();
        return new ReleaseDynamicViewHolder(inflater.inflate(R.layout.item_dynamic_release_dynamic_picture, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ReleaseDynamicViewHolder holder, @NonNull ReleaseDynamicPicture item) {
        Glide.with(context).load(item.picture).into(holder.itemDynamicReleaseDynamicImage);
    }

    static class ReleaseDynamicViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_dynamic_release_dynamic_image)
        public ImageView itemDynamicReleaseDynamicImage;

        public ReleaseDynamicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
