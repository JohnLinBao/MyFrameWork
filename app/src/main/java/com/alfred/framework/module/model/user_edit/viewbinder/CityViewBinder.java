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

public class CityViewBinder extends ItemViewBinder<Province_Bean.City, CityViewBinder.CityViewHolder> {


    private Context context;
    private OnChildClickListener listener;

    public void setOnChildClickListener(OnChildClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected CityViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_address, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(final @NonNull CityViewHolder holder, final @NonNull Province_Bean.City item) {
        holder.itemAddressContent.setText(item.name);
        holder.itemAddressContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(v, getPosition(holder), item);
            }
        });
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_address_content)
        public TextView itemAddressContent;

        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnChildClickListener{
        void OnChildClick(View v, int position, Province_Bean.City data);
    }

}
