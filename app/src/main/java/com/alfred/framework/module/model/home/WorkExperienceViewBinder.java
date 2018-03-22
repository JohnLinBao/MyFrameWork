package com.alfred.framework.module.model.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfred.framework.module.model.User_Bean;
import com.alfred.framework.myframework.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/18.
 */

public class WorkExperienceViewBinder extends ItemViewBinder<User_Bean.WorkExperience, WorkExperienceViewBinder.WorkExperienceViewHolder> {


    private Context context;

    @NonNull
    @Override
    protected WorkExperienceViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        context = parent.getContext();
        View view = inflater.inflate(R.layout.item_workexperience, parent, false);
        return new WorkExperienceViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull WorkExperienceViewHolder holder, @NonNull User_Bean.WorkExperience item) {
        holder.itemWorkexperienceDate.setText(item.startTime + "-" + item.endTime);
        Glide.with(context).load(item.companyLogo).into(holder.itemWorkexperienceCompanylogo);
        holder.itemWorkexperienceCompany.setText(item.company);
        holder.itemWorkexperiencePosition.setText(item.position);
        holder.itemWorkexperienceDuty.setText(item.industryName + "    " + item.professionName);
        holder.itemWorkexperienceIntroducation.setText(item.introduction);
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

        public WorkExperienceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
