package com.alfred.framework.module.model.user_edit.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alfred.framework.module.model.Province_Bean;
import com.alfred.framework.myframework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/27.
 */

public class ProvinceViewBinder extends ItemViewBinder<Province_Bean, ProvinceViewBinder.ProvinceViewHolder> {



    private Context context;
    private OnChildClickListener listener;

    public void setOnChildClickListener(OnChildClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ProvinceViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_address, parent, false);
        return new ProvinceViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ProvinceViewHolder holder, @NonNull final Province_Bean item) {
        holder.itemAddressContent.setText(item.name);
        holder.itemAddressContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(v, getPosition(holder), item);
            }
        });
    }


    static class ProvinceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_address_content)
        public TextView itemAddressContent;

        public ProvinceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnChildClickListener {
         void OnChildClick(View v, int position, Province_Bean data);
    }
}
