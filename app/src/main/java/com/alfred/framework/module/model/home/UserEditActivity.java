package com.alfred.framework.module.model.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.base.config.AppConfig;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.model.Education_Bean;
import com.alfred.framework.module.model.Project_Bean;
import com.alfred.framework.module.model.User_Bean;
import com.alfred.framework.module.model.WorkExperience_Bean;
import com.alfred.framework.module.model.user_edit.AddEducationActivity;
import com.alfred.framework.module.model.user_edit.AddProjectActivity;
import com.alfred.framework.module.model.user_edit.AddWorkexperienceActivity;
import com.alfred.framework.module.model.user_edit.EditBaseUserActivity;
import com.alfred.framework.module.model.user_edit.viewbinder.EducationViewBinder;
import com.alfred.framework.module.model.user_edit.viewbinder.ProjectViewBinder;
import com.alfred.framework.module.model.user_edit.viewbinder.WorkExperienceViewBinder;
import com.alfred.framework.myframework.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.RequestBody;
import static com.alfred.framework.base.config.AppConfig.user;
import static com.alfred.framework.base.config.AppConfig.editUser;

/**
 * Created by asus on 2018/3/22.
 */

public class UserEditActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.useredit_head)
    ImageView usereditHead;
    @BindView(R.id.useredit_name)
    TextView usereditName;
    @BindView(R.id.useredit_sex)
    ImageView usereditSex;
    @BindView(R.id.useredit_baseedit)
    TextView usereditBaseedit;
    @BindView(R.id.useredit_position)
    TextView usereditPosition;
    @BindView(R.id.useredit_registername)
    TextView usereditRegistername;
    @BindView(R.id.useredit_phone_image)
    ImageView usereditPhoneImage;
    @BindView(R.id.useredit_phone)
    TextView usereditPhone;
    @BindView(R.id.useredit_email_image)
    ImageView usereditEmailImage;
    @BindView(R.id.useredit_email)
    TextView usereditEmail;
    @BindView(R.id.useredit_companylogo)
    ImageView usereditCompanylogo;
    @BindView(R.id.useredit_brife)
    TextView usereditBrife;
    @BindView(R.id.useredit_line)
    ImageView usereditLine;
    @BindView(R.id.useredit_releasedanymic_count)
    TextView usereditReleasedanymicCount;
    @BindView(R.id.useredit_releaserecruitment_count)
    TextView usereditReleaserecruitmentCount;
    @BindView(R.id.useredit_dynamic_linearlayout)
    LinearLayout usereditDynamicLinearlayout;
    @BindView(R.id.useredit_workexperience_title)
    TextView usereditWorkexperienceTitle;
    @BindView(R.id.useredit_workexperience_increase)
    TextView usereditWorkexperienceIncrease;
    @BindView(R.id.useredit_workexperience_add)
    TextView usereditWorkexperienceAdd;
    @BindView(R.id.useredit_workexperience_addimage)
    ImageView usereditWorkexperienceAddimage;
    @BindView(R.id.useredit_workexperience_relativelayout)
    RelativeLayout usereditWorkexperienceRelativelayout;
    @BindView(R.id.useredit_workexperience_recyclerview)
    RecyclerView usereditWorkexperienceRecyclerview;
    @BindView(R.id.useredit_education_title)
    TextView usereditEducationTitle;
    @BindView(R.id.useredit_education_increase)
    TextView usereditEducationIncrease;
    @BindView(R.id.useredit_education_add)
    TextView usereditEducationAdd;
    @BindView(R.id.useredit_education_addimage)
    ImageView usereditEducationAddimage;
    @BindView(R.id.useredit_education_relativelayout)
    RelativeLayout usereditEducationRelativelayout;
    @BindView(R.id.useredit_education_recyclerview)
    RecyclerView usereditEducationRecyclerview;
    @BindView(R.id.useredit_project_title)
    TextView usereditProjectTitle;
    @BindView(R.id.useredit_project_increase)
    TextView usereditProjectIncrease;
    @BindView(R.id.useredit_project_add)
    TextView usereditProjectAdd;
    @BindView(R.id.useredit_project_addimage)
    ImageView usereditProjectAddimage;
    @BindView(R.id.useredit_project_relativelayout)
    RelativeLayout usereditProjectRelativelayout;
    @BindView(R.id.useredit_project_recyclerview)
    RecyclerView usereditProjectRecyclerview;
    @BindView(R.id.useredit_button)
    TextView usereditButton;
    private String city = "";
    private String company = "";
    private String position = "";
    boolean editFlag = false;
    final int WORK = 1;
    final int EDUCATION = 2;
    final int PROJECT = 3;
    final int BASEEDIT = 4;
    private List<String> pictures = new ArrayList<>();
    private WorkExperience_Bean workExperience = new WorkExperience_Bean();
    private Education_Bean education = new Education_Bean();
    private Project_Bean project = new Project_Bean();
    private MultiTypeAdapter workAdapter = new MultiTypeAdapter();
    private MultiTypeAdapter educationAdapter = new MultiTypeAdapter();
    private MultiTypeAdapter projectAdapter = new MultiTypeAdapter();
    private WorkExperienceViewBinder workBinder = new WorkExperienceViewBinder();
    private EducationViewBinder educationBinder = new EducationViewBinder();
    private ProjectViewBinder projectBinder = new ProjectViewBinder();

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
        setContentView(R.layout.activity_useredit);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {
        
        /**
         * 进入用户编辑界面自动显示的基本信息
         */
        /*头像*/
        Glide.with(this).load(editUser.avatar).into(usereditHead);
        /*姓名*/
        usereditName.setText(editUser.name);
        /*性别*/
        if (editUser.gender == 1)
            usereditSex.setImageResource(R.drawable.xingbie_nan);
        else if (editUser.gender == 2)
            usereditSex.setImageResource(R.drawable.xingbie_nv);
        /*城市、公司、职位*/
        if (editUser.city != null)
            city = editUser.city + "  ";
        if (editUser.company != null)
            company = editUser.company + "  ";
        if (editUser.position != null)
            position = editUser.position + "  ";
        usereditPosition.setText(city + company + position);
        /*电话*/
        if (editUser.phoneNumber != null) {
            usereditPhoneImage.setVisibility(View.VISIBLE);
            usereditPhone.setText(editUser.phoneNumber);
        }
        /*邮箱*/
        if (editUser.email != null) {
            usereditEmailImage.setVisibility(View.VISIBLE);
            usereditEmail.setText(editUser.email);
        }
        /*一句话介绍*/
        usereditBrife.setText(editUser.brief);

        /*公司logo*/
        Glide.with(this).load(editUser.companyLogo).into(usereditCompanylogo);

        /*编辑用户基本资料*/
        usereditBaseedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserEditActivity.this, EditBaseUserActivity.class);
                startActivityForResult(intent, BASEEDIT);
            }
        });

        /*工作经历*/
        if (editUser.workExperience.size() != 0) {
            usereditWorkexperienceRecyclerview.setVisibility(View.VISIBLE);
            workAdapter.register(WorkExperience_Bean.class, workBinder);
            usereditWorkexperienceRecyclerview.setAdapter(workAdapter);
            usereditWorkexperienceRecyclerview.setLayoutManager(new LinearLayoutManager(UserEditActivity.this, LinearLayoutManager.VERTICAL, false));
            workAdapter.setItems(editUser.workExperience);
            workAdapter.notifyDataSetChanged();
        }
        usereditWorkexperienceRelativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent workIntent = new Intent(UserEditActivity.this, AddWorkexperienceActivity.class);
                startActivityForResult(workIntent, WORK);
            }
        });
        usereditWorkexperienceIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent workIntent = new Intent(UserEditActivity.this, AddWorkexperienceActivity.class);
                startActivityForResult(workIntent, WORK);
            }
        });
        //编辑工作经历
        workBinder.setOnChildClickListener(new WorkExperienceViewBinder.OnChildClickListener() {
            @Override
            public void OnChildClick(int position) {
                Intent workIntent = new Intent(UserEditActivity.this, AddWorkexperienceActivity.class);
                workIntent.putExtra("position", position);
                startActivityForResult(workIntent, WORK);
            }
        });


        /*教育经历*/
        if (editUser.education.size() != 0) {
            usereditEducationRecyclerview.setVisibility(View.VISIBLE);
            educationAdapter.register(Education_Bean.class, educationBinder);
            usereditEducationRecyclerview.setAdapter(educationAdapter);
            usereditEducationRecyclerview.setLayoutManager(new LinearLayoutManager(UserEditActivity.this, LinearLayoutManager.VERTICAL, false));
            educationAdapter.setItems(editUser.education);
            educationAdapter.notifyDataSetChanged();
        }

        usereditEducationRelativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent educationIntent = new Intent(UserEditActivity.this, AddEducationActivity.class);
                startActivityForResult(educationIntent, EDUCATION);
            }
        });
        usereditEducationIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent educationIntent = new Intent(UserEditActivity.this, AddEducationActivity.class);
                startActivityForResult(educationIntent, EDUCATION);
            }
        });
        //编辑教育经历
        educationBinder.setOnChildClickListener(new EducationViewBinder.OnChildClickListener() {
            @Override
            public void OnChildClick(int position) {
                Intent educationIntent = new Intent(UserEditActivity.this, AddEducationActivity.class);
                educationIntent.putExtra("position", position);
                startActivityForResult(educationIntent, EDUCATION);
            }
        });
         /*项目经历*/
        if (editUser.project.size() != 0) {
            usereditProjectRecyclerview.setVisibility(View.VISIBLE);
            projectAdapter.register(Project_Bean.class, projectBinder);
            usereditProjectRecyclerview.setAdapter(projectAdapter);
            usereditProjectRecyclerview.setLayoutManager(new LinearLayoutManager(UserEditActivity.this, LinearLayoutManager.VERTICAL, false));
            projectAdapter.setItems(editUser.project);
            projectAdapter.notifyDataSetChanged();
        }
        usereditProjectRelativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent projectIntent = new Intent(UserEditActivity.this, AddProjectActivity.class);
                startActivityForResult(projectIntent, PROJECT);
            }
        });

        usereditProjectIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent projectIntent = new Intent(UserEditActivity.this, AddProjectActivity.class);
                startActivityForResult(projectIntent, PROJECT);
            }
        });

        //编辑工作经历
        projectBinder.setOnChildClickListener(new ProjectViewBinder.OnChildClickListener() {
            @Override
            public void OnChildClick(int position) {
                Intent projectIntent = new Intent(UserEditActivity.this, AddProjectActivity.class);
                projectIntent.putExtra("position", position);
                startActivityForResult(projectIntent, PROJECT);
            }
        });



        /*编辑用户资料*/
        usereditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //可编辑状态
                if (!editFlag) {
                    editFlag = true;
                    //基础信息编辑
                    usereditButton.setText("完成");
                    usereditBaseedit.setVisibility(View.VISIBLE);

                    //添加工作经历
                    usereditWorkexperienceTitle.setVisibility(View.VISIBLE);
                    if (editUser.workExperience.size() == 0) {
                        usereditWorkexperienceRelativelayout.setVisibility(View.VISIBLE);

                    } else {
                        usereditWorkexperienceIncrease.setVisibility(View.VISIBLE);
                        workBinder.setEdit(true);
                        workAdapter.notifyDataSetChanged();
                    }

                    //添加教育经历
                    usereditEducationTitle.setVisibility(View.VISIBLE);
                    if (editUser.education.size() == 0) {
                        usereditEducationRelativelayout.setVisibility(View.VISIBLE);
                    } else {
                        usereditEducationIncrease.setVisibility(View.VISIBLE);
                        educationBinder.setEdit(true);
                        educationAdapter.notifyDataSetChanged();
                    }

                    //添加项目经历
                    usereditProjectTitle.setVisibility(View.VISIBLE);
                    if (editUser.project.size() == 0) {
                        usereditProjectRelativelayout.setVisibility(View.VISIBLE);

                    } else {
                        usereditProjectIncrease.setVisibility(View.VISIBLE);

                        projectBinder.setEdit(true);
                        projectAdapter.notifyDataSetChanged();
                    }
                } else {
                    //不可编辑状态，且保存编辑信息
                    editFlag = false;

                    //取消基础信息编辑显示
                    usereditButton.setText("编辑资料");
                    usereditBaseedit.setVisibility(View.GONE);

                    //取消工作经历的编辑显示
                    workBinder.setEdit(false);
                    workAdapter.notifyDataSetChanged();
                    usereditWorkexperienceTitle.setVisibility(View.GONE);
                    usereditWorkexperienceRelativelayout.setVisibility(View.GONE);
                    usereditWorkexperienceIncrease.setVisibility(View.GONE);

                    //取消教育经历的编辑显示
                    educationBinder.setEdit(false);
                    educationAdapter.notifyDataSetChanged();
                    usereditEducationIncrease.setVisibility(View.GONE);
                    usereditEducationTitle.setVisibility(View.GONE);
                    usereditEducationRelativelayout.setVisibility(View.GONE);

                    //取消项目经历的编辑显示
                    projectBinder.setEdit(false);
                    projectAdapter.notifyDataSetChanged();
                    usereditProjectTitle.setVisibility(View.GONE);
                    usereditProjectIncrease.setVisibility(View.GONE);
                    usereditProjectRelativelayout.setVisibility(View.GONE);

                    //保存修改的编辑用户信息
//                    if (workexperienceList != null) {
//                        for (int i = 0; i < workexperienceList.size(); i++) {
//                            Map<String, Object> map = new HashMap<String, Object>();
//                            map.put("company", workexperienceList.get(i).company);
//                            map.put("companyLogo", workexperienceList.get(i).companyLogo);
//                            map.put("position", workexperienceList.get(i).position);
//                            map.put("industry", workexperienceList.get(i).industry);
//                            map.put("profession", workexperienceList.get(i).profession);
//                            map.put("startTime", workexperienceList.get(i).startTime);
//                            map.put("endTime", workexperienceList.get(i).endTime);
//                            map.put("introduction", workexperienceList.get(i).introduction);
//                            final RequestBody requestBody = OkHttpManager.appendTokenPOST(OkHttpManager.JSON, map);
//                            OkHttpManager.httpPost("http://47.92.48.125/user/addWorkExperience", requestBody, new OkHttpManager.OnResultListener() {
//                                @Override
//                                public void onSuccess(String result) {
//                                    usereditWorkexperienceRecyclerview.setVisibility(View.VISIBLE);
//                                }
//
//                                @Override
//                                public void onFailure(Exception e) {
//                                    usereditWorkexperienceRecyclerview.setVisibility(View.GONE);
//                                }
//                            });
//                        }
//                    }
                }
            }
        });
    }

    @Override
    public void hanldeToolbar() {
        editUser = user;
        barProxy.setTitle(editUser.name, View.VISIBLE);
        barProxy.getToolbar().setNavigationIcon(R.drawable.back_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case WORK:
                if (resultCode == RESULT_OK) {
                    //显示工作经历
                    if (editUser.workExperience.size() == 0){
                        usereditWorkexperienceIncrease.setVisibility(View.GONE);
                        usereditWorkexperienceRelativelayout.setVisibility(View.VISIBLE);
                        usereditWorkexperienceRecyclerview.setVisibility(View.GONE);
                    }
                    else{
                        usereditWorkexperienceIncrease.setVisibility(View.VISIBLE);
                        usereditWorkexperienceRelativelayout.setVisibility(View.GONE);
                        usereditWorkexperienceRecyclerview.setVisibility(View.VISIBLE);
                        workAdapter.register(WorkExperience_Bean.class, workBinder);
                        usereditWorkexperienceRecyclerview.setAdapter(workAdapter);
                        usereditWorkexperienceRecyclerview.setLayoutManager(new LinearLayoutManager(UserEditActivity.this,
                                LinearLayoutManager.VERTICAL, false));
                        workAdapter.setItems(editUser.workExperience);
                        workBinder.setEdit(true);
                        workAdapter.notifyDataSetChanged();
                    }

                }

                break;
            case EDUCATION:
                if (resultCode == RESULT_OK){
                    //显示教育经历
                    if (editUser.education.size() == 0){
                        usereditEducationIncrease.setVisibility(View.GONE);
                        usereditEducationRelativelayout.setVisibility(View.VISIBLE);
                        usereditEducationRecyclerview.setVisibility(View.GONE);
                    }else{
                        usereditEducationIncrease.setVisibility(View.VISIBLE);
                        usereditEducationRelativelayout.setVisibility(View.GONE);
                        usereditEducationRecyclerview.setVisibility(View.VISIBLE);
                        educationAdapter.register(Education_Bean.class, educationBinder);
                        usereditEducationRecyclerview.setAdapter(educationAdapter);
                        usereditEducationRecyclerview.setLayoutManager(new LinearLayoutManager(UserEditActivity.this,
                                LinearLayoutManager.VERTICAL, false));
                        educationAdapter.setItems(editUser.education);
                        educationBinder.setEdit(true);
                        educationAdapter.notifyDataSetChanged();
                    }


                }

                break;

            case PROJECT:
                if (resultCode == RESULT_OK){
                    //显示项目经历
                    if (editUser.project.size() == 0){
                        usereditProjectIncrease.setVisibility(View.GONE);
                        usereditProjectRelativelayout.setVisibility(View.VISIBLE);
                        usereditProjectRecyclerview.setVisibility(View.GONE);
                    }
                    else {
                        usereditProjectIncrease.setVisibility(View.VISIBLE);
                        usereditProjectRelativelayout.setVisibility(View.GONE);
                        usereditProjectRecyclerview.setVisibility(View.VISIBLE);
                        projectAdapter.register(Project_Bean.class, projectBinder);
                        usereditProjectRecyclerview.setAdapter(projectAdapter);
                        usereditProjectRecyclerview.setLayoutManager(new LinearLayoutManager(UserEditActivity.this,
                                LinearLayoutManager.VERTICAL, false));
                        projectAdapter.setItems(editUser.project);
                        projectBinder.setEdit(true);
                        projectAdapter.notifyDataSetChanged();
                    }

                }
                break;
            case BASEEDIT:

                break;
            default:
        }
    }

}
