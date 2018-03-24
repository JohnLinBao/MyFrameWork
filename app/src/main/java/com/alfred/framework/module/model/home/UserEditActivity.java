package com.alfred.framework.module.model.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.base.config.AppConfig;
import com.alfred.framework.module.model.user_edit.AddEducationActivity;
import com.alfred.framework.module.model.user_edit.AddProjectActivity;
import com.alfred.framework.module.model.user_edit.AddWorkexperienceActivity;
import com.alfred.framework.myframework.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;

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
    @BindView(R.id.useredit_workexperience_add)
    TextView usereditWorkexperienceAdd;
    @BindView(R.id.useredit_workexperience_addimage)
    ImageView usereditWorkexperienceAddimage;
    @BindView(R.id.useredit_workexperience_relativelayout)
    RelativeLayout usereditWorkexperienceRelativelayout;
    @BindView(R.id.useredit_education_title)
    TextView usereditEducationTitle;
    @BindView(R.id.useredit_education_add)
    TextView usereditEducationAdd;
    @BindView(R.id.useredit_education_addimage)
    ImageView usereditEducationAddimage;
    @BindView(R.id.useredit_education_relativelayout)
    RelativeLayout usereditEducationRelativelayout;
    @BindView(R.id.useredit_project_title)
    TextView usereditProjectTitle;
    @BindView(R.id.useredit_project_add)
    TextView usereditProjectAdd;
    @BindView(R.id.useredit_project_addimage)
    ImageView usereditProjectAddimage;
    @BindView(R.id.useredit_project_relativelayout)
    RelativeLayout usereditProjectRelativelayout;
    @BindView(R.id.useredit_button)
    TextView usereditButton;
    private String city = "";
    private String company = "";
    private String position = "";
    boolean editFlag = false;
    final int WORK = 1;
    final int EDUCATION = 2;
    final int PROJECT = 3;

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
        /*头像*/
        Glide.with(this).load(AppConfig.user.avatar).into(usereditHead);
        /*姓名*/
        usereditName.setText(AppConfig.user.name);
        /*性别*/
        if (AppConfig.user.gender == 1)
            usereditSex.setImageResource(R.drawable.xingbie_nan);
        else if (AppConfig.user.gender == 2)
            usereditSex.setImageResource(R.drawable.xingbie_nv);
        /*城市、公司、职位*/
        if (AppConfig.user.city != null)
            city = AppConfig.user.city + "  ";
        if (AppConfig.user.company != null)
            company = AppConfig.user.company + "  ";
        if (AppConfig.user.position != null)
            position = AppConfig.user.position + "  ";
        usereditPosition.setText(city + company + position);
        /*电话*/
        if (AppConfig.user.phoneNumber != null){
            usereditPhoneImage.setVisibility(View.VISIBLE);
            usereditPhone.setText(AppConfig.user.phoneNumber);
        }
        /*邮箱*/
        if (AppConfig.user.email != null){
            usereditEmailImage.setVisibility(View.VISIBLE);
            usereditEmail.setText(AppConfig.user.email);
        }
        /*一句话介绍*/
        usereditBrife.setText(AppConfig.user.brief);
        /*公司logo*/
        Glide.with(this).load(AppConfig.user.companyLogo).into(usereditCompanylogo);
        /*编辑用户资料*/
        usereditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editFlag){
                    editFlag = true;
                    usereditButton.setText("完成");
                    usereditBaseedit.setVisibility(View.VISIBLE);
                    usereditWorkexperienceTitle.setVisibility(View.VISIBLE);
                    usereditWorkexperienceRelativelayout.setVisibility(View.VISIBLE);
                    //添加工作经历
                    usereditWorkexperienceRelativelayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent workIntent = new Intent(UserEditActivity.this, AddWorkexperienceActivity.class);
                            startActivityForResult(workIntent, WORK);
                        }
                    });
                    usereditEducationTitle.setVisibility(View.VISIBLE);
                    usereditEducationRelativelayout.setVisibility(View.VISIBLE);
                    //添加教育经历
                    usereditEducationRelativelayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent educationIntent = new Intent(UserEditActivity.this, AddEducationActivity.class);
                            startActivityForResult(educationIntent, EDUCATION);
                        }
                    });
                    usereditProjectTitle.setVisibility(View.VISIBLE);
                    usereditProjectRelativelayout.setVisibility(View.VISIBLE);
                    //添加项目经历
                    usereditProjectRelativelayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent projectIntent = new Intent(UserEditActivity.this, AddProjectActivity.class);
                            startActivityForResult(projectIntent, PROJECT);
                        }
                    });
                }else {
                    editFlag = false;
                    usereditButton.setText("编辑资料");
                    usereditBaseedit.setVisibility(View.GONE);
                    usereditWorkexperienceTitle.setVisibility(View.GONE);
                    usereditWorkexperienceRelativelayout.setVisibility(View.GONE);
                    usereditEducationTitle.setVisibility(View.GONE);
                    usereditEducationRelativelayout.setVisibility(View.GONE);
                    usereditProjectTitle.setVisibility(View.GONE);
                    usereditProjectRelativelayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void hanldeToolbar() {
        barProxy.setTitle(AppConfig.user.name, View.VISIBLE);
        barProxy.getToolbar().setNavigationIcon(R.drawable.back_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
