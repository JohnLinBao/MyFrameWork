package com.alfred.framework.module.model.user_edit.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alfred.framework.module.model.Common_Bean;
import com.alfred.framework.module.model.home.DynamicViewBinder;
import com.alfred.framework.myframework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/24.
 */

public class IndustryViewBinder extends ItemViewBinder<Common_Bean.CommonList, IndustryViewBinder.IndustryViewHolder> {


    private Context context;
    private OnChildClickListener listener;
    public void setOnChildClickListener(OnChildClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    protected IndustryViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_select_industry, parent, false);
        return new IndustryViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull final IndustryViewHolder holder, @NonNull final Common_Bean.CommonList item) {
        holder.selectIndustryContent.setText(item.name);
        holder.selectIndustryContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(v, getPosition(holder), item);
            }
        });
    }

    static class IndustryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.select_industry_content)
        public TextView selectIndustryContent;
        public IndustryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnChildClickListener{
        public void OnChildClick(View v, int position, Common_Bean.CommonList data);
    }
}

