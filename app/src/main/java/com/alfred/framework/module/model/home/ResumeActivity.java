package com.alfred.framework.module.model.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.model.Dynamic_Bean;
import com.alfred.framework.module.model.Resume_Bean;
import com.alfred.framework.module.model.home.viewbinder.ResumeWorkExperienceViewBinder;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.GsonUtils;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.RequestBody;

/**
 * Created by asus on 2018/3/18.
 */

public class ResumeActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.resume_head)
    ImageView resumeHead;
    @BindView(R.id.resume_sex)
    ImageView resumeSex;
    @BindView(R.id.resume_arrow)
    ImageView resumeArrow;
    @BindView(R.id.resume_personal_detail)
    TextView resumePersonalDetail;
    @BindView(R.id.resume_nature)
    TextView resumeNature;
    @BindView(R.id.resume_expected_job)
    TextView resumeExpectedJob;
    @BindView(R.id.resume_expected_job_detail)
    TextView resumeExpectedJobDetail;
    @BindView(R.id.resume_workexperience)
    TextView resumeWorkexperience;
    @BindView(R.id.resume_recyclerview)
    RecyclerView resumeRecyclerview;
    @BindView(R.id.resume_education)
    TextView resumeEducation;
    @BindView(R.id.resume_education_image)
    ImageView resumeEducationImage;
    @BindView(R.id.resume_education_line)
    ImageView resumeEducationLine;
    @BindView(R.id.resume_education_time)
    TextView resumeEducationTime;
    @BindView(R.id.rsume_education_school)
    TextView rsumeEducationSchool;
    @BindView(R.id.resume_education_major)
    TextView resumeEducationMajor;
    @BindView(R.id.resume_selfdescription)
    TextView resumeSelfdescription;
    @BindView(R.id.resume_selfdescription_detail)
    TextView resumeSelfdescriptionDetail;
    @BindView(R.id.resume_basicinformation)
    TextView resumeBasicinformation;
    @BindView(R.id.resume_basicinformation_birthday)
    TextView resumeBasicinformationBirthday;
    @BindView(R.id.resume_chat)
    TextView resumeChat;
    @BindView(R.id.resume_name)
    TextView resumeName;
    private Resume_Bean resume_bean;
    private int userId;
    private MultiTypeAdapter resumeWorkExperienceAdapter;
    @Override
    public void reload() {

    }

    @Override
    public void ShowNetWort(boolean isshow) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_resume);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {
        userId = getIntent().getIntExtra("userId", 0);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("userId", userId);
        RequestBody requestBody = OkHttpManager.appendTokenPOST(OkHttpManager.JSON, map);
        OkHttpManager.httpPost("http://47.92.48.125/resume/detail", requestBody, new OkHttpManager.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d("resume获取的数据", result);
                Type type = new TypeToken<BaseResponse<Resume_Bean>>() {
                }.getType();
                BaseResponse<Resume_Bean> response = (BaseResponse<Resume_Bean>) GsonUtils.fromJson(result, type);
                resume_bean = response.getData();
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("resume获取的数据", e.toString());
            }
        });
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //简历用户头像
                    Glide.with(ResumeActivity.this).load(resume_bean.avatar).into(resumeHead);
                    //简历用户姓名
                    resumeName.setText(resume_bean.name);
                    //性别 1代表男性 2代表女性
                    if (resume_bean.gender == 1)
                        resumeSex.setImageResource(R.drawable.xingbie_nan);
                    else resumeSex.setImageResource(R.drawable.xingbie_nv);
                    //个人工作情况
                    resumePersonalDetail.setText(resume_bean.city + "  |  " + resume_bean.education.get(0).degreeName + "  |  " + resume_bean.workYearsName);
                    //一句话介绍
                    resumeNature.setText(resume_bean.brief);
                    //期望工作
                    resumeExpectedJobDetail.setText("期望职位：" + resume_bean.expectedPosition +"\n"
                    + "期望月薪：" + resume_bean.expectedSalaryName +"\n"
                    + "期望城市：" + resume_bean.desiredCity +"\n"
                    + "职位性质：" + resume_bean.positionTypeName +"\n"
                    + "到岗时间：" + resume_bean.arrivalTimeName +"\n"
                    + "职能专业：" + resume_bean.professionName +"\n"
                    + "细分行业：" + resume_bean.industryName +"\n"
                    + "求职状态：" + resume_bean.jobStatusName);
                    //工作经历
                    resumeWorkExperienceAdapter = new MultiTypeAdapter();
                    resumeWorkExperienceAdapter.register(Resume_Bean.WorkExperience.class , new ResumeWorkExperienceViewBinder());
                    resumeRecyclerview.setAdapter(resumeWorkExperienceAdapter);
                    resumeRecyclerview.setLayoutManager(new LinearLayoutManager(ResumeActivity.this));
                    resumeWorkExperienceAdapter.setItems(resume_bean.workExperience);
                    resumeWorkExperienceAdapter.notifyDataSetChanged();
                    //教育经历
                    resumeEducationTime.setText(resume_bean.education.get(0).startTime + "-" + resume_bean.education.get(0).endTime);
                    rsumeEducationSchool.setText(resume_bean.education.get(0).schoolName);
                    resumeEducationMajor.setText(resume_bean.education.get(0).degreeName + "    " + resume_bean.education.get(0).major);
                    //自我描述
                    resumeSelfdescriptionDetail.setText(resume_bean.introduction);
                    //基本信息
                    resumeBasicinformationBirthday.setText("生日：" + resume_bean.birthday + "\n"
                    + "电话：" + resume_bean.phoneNumber + "\n"
                    + "邮箱：" + resume_bean.email);
                    break;
            }
        }
    };

    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("简历详情", View.VISIBLE);
        barProxy.getToolbar().setNavigationIcon(R.drawable.back_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //toolbar设置菜单方式
        barProxy.getToolbar().inflateMenu(R.menu.activity_menu_share);
        //设置菜单及其点击监听
        barProxy.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain"); // 分享发送的数据类型
                        intent.putExtra(Intent.EXTRA_TEXT, resume_bean.shareUrl); // 分享的内容
                        startActivity(Intent.createChooser(intent, ""));// 目标应用选择对话框的标题;
                        break;
                }
                return true;
            }
        });
    }

}
