package com.alfred.framework.module.model.user_edit.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfred.framework.module.model.Project_Bean;
import com.alfred.framework.myframework.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/26.
 */

public class ProjectViewBinder extends ItemViewBinder<Project_Bean, ProjectViewBinder.ProjectViewHolder> {


    private Context context;
    private boolean isEdit = false;
    private OnChildClickListener listener;

    public void setOnChildClickListener(OnChildClickListener listener) {
        this.listener = listener;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    @NonNull
    @Override
    protected ProjectViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        context = parent.getContext();
        View v = inflater.inflate(R.layout.item_project, parent, false);
        return new ProjectViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ProjectViewHolder holder, @NonNull Project_Bean item) {
        if (isEdit)
            holder.itemProjectEdit.setVisibility(View.VISIBLE);
        else
            holder.itemProjectEdit.setVisibility(View.GONE);
        holder.itemProjectEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(getPosition(holder));
            }
        });
        holder.itemProjectDate.setText(item.startTime + "-" + item.endTime);
        holder.itemProjectCompanyname.setText(item.company + "    " + item.name);
        holder.itemProjectArea.setText(item.type + "    " + item.area + "㎡");
        holder.itemProjectScale.setText("地上层数" + item.floorOverground  + "层" + "    " + "地下层数" + item.floorUnderground + "层");
        holder.itemProjectLocation.setText(item.location + "    " + item.roleName);
        holder.itemProjectIntroduction.setText(item.introduction);
        if (item.pictures.size() != 0)
            Glide.with(context).load(item.pictures.get(0)).into(holder.itemProjectPhoto);
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_project_edit)
        public TextView itemProjectEdit;
        @BindView(R.id.item_project_date)
        public TextView itemProjectDate;
        @BindView(R.id.item_project_companyname)
        public TextView itemProjectCompanyname;
        @BindView(R.id.item_project_area)
        public TextView itemProjectArea;
        @BindView(R.id.item_project_scale)
        public TextView itemProjectScale;
        @BindView(R.id.item_project_location)
        public TextView itemProjectLocation;
        @BindView(R.id.item_project_introduction)
        public TextView itemProjectIntroduction;
        @BindView(R.id.item_project_photo)
        public ImageView itemProjectPhoto;
        public ProjectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnChildClickListener{
        void OnChildClick(int position);
    }
}
