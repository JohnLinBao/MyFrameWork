package com.alfred.framework.module.model.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.model.Dynamic_Bean;
import com.alfred.framework.module.model.ReleaseDynamicPicture;
import com.alfred.framework.module.model.User_Bean;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.GsonUtils;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.RequestBody;
//123456789
/**
 * Created by asus on 2018/3/9.
 */

public class DynamicViewBinder extends ItemViewBinder<Dynamic_Bean.DynamicList, DynamicViewBinder.ViewHolder> {
    public Context context;
    public MultiTypeAdapter imageAdapter;
    public MultiTypeAdapter commentAdapter;
    public Items items;
    public OnChildClickListener listener;


    public void setOnChildClickListener(OnChildClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        context = parent.getContext();
        View view = inflater.inflate(R.layout.item_dynamic, parent, false);
        return new ViewHolder(view);
    }



    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final Dynamic_Bean.DynamicList item) {

        /**
         * 公共部分
         */
        /*获取用户信息*/
        Glide.with(context).load(item.user.avatar).into(holder.itemDynamicHead);
        holder.itemDynamicHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(v, getPosition(holder), item);
            }
        });
        holder.itemDynamicName.setText(item.user.name);
        holder.itemDynamicUserdetail.setText(item.user.city + "|" + item.user.company + "|" + item.user.position);
        holder.itemDynamicCommentCount.setText(item.commentCount + "");
        holder.itemDynamicShareCount.setText(item.shareCount + "");
        holder.itemDynamicLikeCount.setText(item.likeCount + "");
        holder.itemDynamicLikeCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnChildClick(v, getPosition(holder), item);
            }
        });

        commentAdapter = new MultiTypeAdapter();
        commentAdapter.register(Dynamic_Bean.DynamicList.CommentList.class, new DynamicCommentViewBinder());
        holder.itemDynamicCommentRecyclerview.setAdapter(commentAdapter);
        holder.itemDynamicCommentRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        commentAdapter.setItems(item.commentList);
        commentAdapter.notifyDataSetChanged();

        /**
         * 根据type来选择不同的布局
         * type=1代表发布动态
         * type=2代表发布求职简历
         * type=3代表发布招聘职位
         */
        switch (item.topic.type) {
            case 1:
                holder.itemDynamicReleaseDynamicLinearlayout.setVisibility(View.VISIBLE);
                holder.itemDynamicResumeLinearlayout.setVisibility(View.GONE);
                holder.itemDynamicRecruitmentLinearlayout.setVisibility(View.GONE);
                holder.itemDynamicReleaseDynamicText.setText(item.topic.content);
                imageAdapter = new MultiTypeAdapter();
                imageAdapter.register(ReleaseDynamicPicture.class, new ReleaseDynamicViewBinder());
                holder.itemDynamicReleaseDynamicRecyclerview.setAdapter(imageAdapter);
                holder.itemDynamicReleaseDynamicRecyclerview.setLayoutManager(new GridLayoutManager(context, 3));
                items = new Items();
                for (int i = 0; i < item.topic.pictures.size(); i++)
                    items.add(new ReleaseDynamicPicture(item.topic.pictures.get(i)));
                imageAdapter.setItems(items);
                imageAdapter.notifyDataSetChanged();
                break;
            case 2:
                holder.itemDynamicResumeLinearlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.OnChildClick(v, getPosition(holder), item);
                    }
                });
                holder.itemDynamicResumeLinearlayout.setVisibility(View.VISIBLE);
                holder.itemDynamicReleaseDynamicLinearlayout.setVisibility(View.GONE);
                holder.itemDynamicRecruitmentLinearlayout.setVisibility(View.GONE);
                Glide.with(context).load(item.resume.avatar).into(holder.itemDynamicResumeUserhead);
                holder.itemDynamicResumeName.setText(item.resume.name);
                holder.itemDynamicResumeDetail.setText(item.resume.city + "  |  "
                        + item.resume.workYears + "  |  " + item.resume.degreeName);
                Date date = new Date(item.topic.createTime);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                holder.itemDynamicResumeCreatetime.setText(df.format(date));
                holder.itemDynamicResumeWorkexperienceTime.setText(item.resume.lastWorkExperience.startTime + "-至今");
                holder.itemDynamicResumeWorkexperienceCompany.setText(item.resume.lastWorkExperience.company + "  " +
                        item.resume.lastWorkExperience.position);
                break;

            case 3:
                holder.itemDynamicRecruitmentLinearlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.OnChildClick(v, getPosition(holder), item);
                    }
                });
                holder.itemDynamicRecruitmentLinearlayout.setVisibility(View.VISIBLE);
                holder.itemDynamicReleaseDynamicLinearlayout.setVisibility(View.GONE);
                holder.itemDynamicResumeLinearlayout.setVisibility(View.GONE);
                Glide.with(context).load(item.recruitment.company.logo).into(holder.itemDynamicRecruitmentCompanylogo);
                holder.itemDynamicRecruitmentPositionname.setText(item.recruitment.positionName);
                holder.itemDynamicRecruitmentWorkdetail.setText(item.recruitment.location + "  |  " + item.recruitment.workYearsName
                        + "  |  " + item.recruitment.degreeName + "  |  " + item.recruitment.professionName);
                holder.itemDynamicRecruitmentCompanyprofile.setText(item.recruitment.company.shortName + "  |  "
                        + item.recruitment.company.typeName + "  |  " + item.recruitment.company.sizeName);
                holder.itemDynamicRecruitmentWorkrequest.setText(item.recruitment.company.industryName);
                holder.itemDynamicRecruitmentSalary.setText(item.recruitment.salaryMin + "-"
                        + item.recruitment.salaryMax);
                Date date1 = new Date(item.recruitment.createTime);
                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                holder.itemDynamicRecruitmentCreatetime.setText(df1.format(date1));
                break;
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_dynamic_head)
        public ImageView itemDynamicHead;
        @BindView(R.id.item_dynamic_name)
        public TextView itemDynamicName;
        @BindView(R.id.item_dynamic_userdetail)
        public TextView itemDynamicUserdetail;
        @BindView(R.id.item_dynamic_recruitment_companylogo)
        public ImageView itemDynamicRecruitmentCompanylogo;
        @BindView(R.id.item_dynamic_recruitment_positionname)
        public TextView itemDynamicRecruitmentPositionname;
        @BindView(R.id.item_dynamic_recruitment_workdetail)
        public TextView itemDynamicRecruitmentWorkdetail;
        @BindView(R.id.item_dynamic_recruitment_companyprofile)
        public TextView itemDynamicRecruitmentCompanyprofile;
        @BindView(R.id.item_dynamic_recruitment_workrequest)
        public TextView itemDynamicRecruitmentWorkrequest;
        @BindView(R.id.item_dynamic_recruitment_salary)
        public TextView itemDynamicRecruitmentSalary;
        @BindView(R.id.item_dynamic_recruitment_createtime)
        public TextView itemDynamicRecruitmentCreatetime;
        @BindView(R.id.item_dynamic_recruitment_linearlayout)
        public LinearLayout itemDynamicRecruitmentLinearlayout;
        @BindView(R.id.item_dynamic_resume_userhead)
        public ImageView itemDynamicResumeUserhead;
        @BindView(R.id.item_dynamic_resume_name)
        public TextView itemDynamicResumeName;
        @BindView(R.id.item_dynamic_resume_detail)
        public TextView itemDynamicResumeDetail;
        @BindView(R.id.item_dynamic_resume_createtime)
        public TextView itemDynamicResumeCreatetime;
        @BindView(R.id.item_dynamic_resume_imageview)
        public ImageView itemDynamicResumeImageview;
        @BindView(R.id.item_dynamic_resume_workexperience)
        public TextView itemDynamicResumeWorkexperience;
        @BindView(R.id.item_dynamic_resume_workexperience_time)
        public TextView itemDynamicResumeWorkexperienceTime;
        @BindView(R.id.item_dynamic_resume_workexperience_company)
        public TextView itemDynamicResumeWorkexperienceCompany;
        @BindView(R.id.item_dynamic_resume_linearlayout)
        public LinearLayout itemDynamicResumeLinearlayout;
        @BindView(R.id.item_dynamic_release_dynamic_text)
        public TextView itemDynamicReleaseDynamicText;
        @BindView(R.id.item_dynamic_release_dynamic_recyclerview)
        public RecyclerView itemDynamicReleaseDynamicRecyclerview;
        @BindView(R.id.item_dynamic_release_dynamic_linearlayout)
        public LinearLayout itemDynamicReleaseDynamicLinearlayout;
        @BindView(R.id.item_dynamic_share_count)
        public TextView itemDynamicShareCount;
        @BindView(R.id.item_dynamic_share)
        public ImageView itemDynamicShare;
        @BindView(R.id.item_dynamic_comment_count)
        public TextView itemDynamicCommentCount;
        @BindView(R.id.item_dynamic_comment)
        public ImageView itemDynamicComment;
        @BindView(R.id.item_dynamic_like_count)
        public TextView itemDynamicLikeCount;
        @BindView(R.id.item_dynamic_like)
        public ImageView itemDynamicLike;
        @BindView(R.id.item_dynamic_comment_recyclerview)
        public RecyclerView itemDynamicCommentRecyclerview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnChildClickListener {
        void OnChildClick( View view, int postion, Dynamic_Bean.DynamicList data);
    }
}


