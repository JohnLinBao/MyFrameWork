package com.alfred.framework.module.model.share;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfred.framework.myframework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/23.
 */

public class ShareDialogViewBinder extends ItemViewBinder<ShareDialogType, ShareDialogViewBinder.ShareDialogViewHolder> {



    @NonNull
    @Override
    protected ShareDialogViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_sharedialog, parent, false);
        return new ShareDialogViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ShareDialogViewHolder holder, @NonNull ShareDialogType item) {
        holder.sharedialogImage.setImageResource(item.image);
        holder.sharedialogTitle.setText(item.title);
    }

    static class ShareDialogViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sharedialog_image)
        public ImageView sharedialogImage;
        @BindView(R.id.sharedialog_title)
        public TextView sharedialogTitle;
        public ShareDialogViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
