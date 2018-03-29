package com.alfred.framework.module.model.user_edit.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfred.framework.module.model.WorkExperience_Bean;
import com.alfred.framework.myframework.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/25.
 */

public class WorkExperienceViewBinder extends ItemViewBinder<WorkExperience_Bean, WorkExperienceViewBinder.WorkExperienceViewHolder> {



    private Context context;
    private OnChildClickListener listener;

    public void setOnChildClickListener(OnChildClickListener listener) {
        this.listener = listener;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    private boolean isEdit;
    @NonNull
    @Override
    protected WorkExperienceViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        context = parent.getContext();
        View v = inflater.inflate(R.layout.item_workexperience, parent, false);
        return new WorkExperienceViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull final WorkExperienceViewHolder holder, @NonNull WorkExperience_Bean item) {
        holder.itemWorkexperienceDate.setText(item.startTime + "-" + item.endTime);
        Glide.with(context).load(item.companyLogo).into(holder.itemWorkexperienceCompanylogo);
        holder.itemWorkexperienceCompany.setText(item.company);
        holder.itemWorkexperiencePosition.setText(item.position);
        holder.itemWorkexperienceDuty.setText(item.industryName + "    " + item.professionName);
        holder.itemWorkexperienceIntroducation.setText(item.introduction);
        if (isEdit)
            holder.itemWorkexperienceEdit.setVisibility(View.VISIBLE);
        else
            holder.itemWorkexperienceEdit.setVisibility(View.GONE);
        holder.itemWorkexperienceEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(getPosition(holder));
            }
        });
    }

    static class WorkExperienceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_workexperience_image)
        public ImageView itemWorkexperienceImage;
        @BindView(R.id.item_workexperience_line)
        public ImageView itemWorkexperienceLine;
        @BindView(R.id.item_workexperience_date)
        public TextView itemWorkexperienceDate;
        @BindView(R.id.item_workexperience_time)
        public TextView itemWorkexperienceTime;
        @BindView(R.id.item_workexperience_companylogo)
        public ImageView itemWorkexperienceCompanylogo;
        @BindView(R.id.item_workexperience_company)
        public TextView itemWorkexperienceCompany;
        @BindView(R.id.item_workexperience_position)
        public TextView itemWorkexperiencePosition;
        @BindView(R.id.item_workexperience_duty)
        public TextView itemWorkexperienceDuty;
        @BindView(R.id.item_workexperience_introducation)
        public TextView itemWorkexperienceIntroducation;
        @BindView(R.id.item_workexperience_edit)
        public TextView itemWorkexperienceEdit;

        public WorkExperienceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnChildClickListener{
        void OnChildClick(int position);
    }
}
