package com.alfred.framework.module.model.user_edit.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alfred.framework.module.model.Profession_Bean;
import com.alfred.framework.myframework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/25.
 */

public class ProfessionViewBinder extends ItemViewBinder<Profession_Bean.ProfessionList, ProfessionViewBinder.ProfessionViewHolder> {


    private Context context;
    private OnChildClickListener listener;

    public void setOnChildClickListener(OnChildClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ProfessionViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        context = parent.getContext();
        View v = inflater.inflate(R.layout.item_select_profession, parent, false);
        return new ProfessionViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ProfessionViewHolder holder, @NonNull final Profession_Bean.ProfessionList item) {

        holder.selectProfessionContent.setText(item.name);
        holder.selectProfessionOne.setText(item.list.get(0).name);
        holder.selectProfessionTwo.setText(item.list.get(1).name);
        holder.selectProfessionThree.setText(item.list.get(2).name);
        holder.selectProfessionFour.setText(item.list.get(3).name);
        if (item.list.size() > 4)
            holder.selectProfessionFive.setText(item.list.get(4).name);
        if (item.list.size() > 6) {
            holder.selectProfessionSix.setVisibility(View.VISIBLE);
        }
        holder.selectProfessionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(v, getPosition(holder), item);
            }
        });
        holder.selectProfessionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(v, getPosition(holder), item);
            }
        });
        holder.selectProfessionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(v, getPosition(holder), item);
            }
        });

        holder.selectProfessionFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(v, getPosition(holder), item);
            }
        });
        holder.selectProfessionFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(v, getPosition(holder), item);
            }
        });
    }

    static class ProfessionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.select_profession_content)
        public TextView selectProfessionContent;
        @BindView(R.id.select_profession_one)
        public TextView selectProfessionOne;
        @BindView(R.id.select_profession_two)
        public TextView selectProfessionTwo;
        @BindView(R.id.select_profession_three)
        public TextView selectProfessionThree;
        @BindView(R.id.select_profession_four)
        public TextView selectProfessionFour;
        @BindView(R.id.select_profession_five)
        public TextView selectProfessionFive;
        @BindView(R.id.select_profession_six)
        public TextView selectProfessionSix;

        public ProfessionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnChildClickListener {
        public void OnChildClick(View v, int position, Profession_Bean.ProfessionList data);
    }
}
