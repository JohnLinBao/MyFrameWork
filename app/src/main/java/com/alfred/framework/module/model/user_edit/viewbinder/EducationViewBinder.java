package com.alfred.framework.module.model.user_edit.viewbinder;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alfred.framework.module.model.Education_Bean;

import com.alfred.framework.myframework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/26.
 */

public class EducationViewBinder extends ItemViewBinder<Education_Bean, EducationViewBinder.EducationViewHolder> {


    public void setEdit(boolean edit) {
        isEdit = edit;
    }
    private boolean isEdit = false;
    private Context context;

    public void setOnChildClickListener(OnChildClickListener listener) {
        this.listener = listener;
    }

    private OnChildClickListener listener;

    @NonNull
    @Override
    protected EducationViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        context = parent.getContext();
        View v = inflater.inflate(R.layout.item_education, parent, false);
        return new EducationViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull final EducationViewHolder holder, @NonNull Education_Bean item) {
        if (isEdit)
            holder.itemEducationEdit.setVisibility(View.VISIBLE);
        else
            holder.itemEducationEdit.setVisibility(View.GONE);
        holder.itemEducationDate.setText(item.startTime + "-" + item.endTime);
        holder.itemEducationSchool.setText(item.schoolName);
        holder.itemEducationDegree.setText(item.degreeName + "    "+ item.major);
        holder.itemEducationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(getPosition(holder));
            }
        });
    }

    static class EducationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_education_edit)
        public TextView itemEducationEdit;
        @BindView(R.id.item_education_date)
        public TextView itemEducationDate;
        @BindView(R.id.item_education_school)
        public TextView itemEducationSchool;
        @BindView(R.id.item_education_degree)
        public TextView itemEducationDegree;
        public EducationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnChildClickListener {

        void OnChildClick(int position);
    }
}
