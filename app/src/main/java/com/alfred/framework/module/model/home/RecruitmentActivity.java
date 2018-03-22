package com.alfred.framework.module.model.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.RegisterActivityOne;
import com.alfred.framework.module.model.Dynamic_Bean;
import com.alfred.framework.module.model.Recruitment_Bean;
import com.alfred.framework.module.model.User_Bean;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.GsonUtils;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogRecord;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;

/**
 * Created by asus on 2018/3/18.
 */

public class RecruitmentActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recruitment_position)
    TextView recruitmentPosition;
    @BindView(R.id.recruitment_salary)
    TextView recruitmentSalary;
    @BindView(R.id.recruitment_location_image)
    ImageView recruitmentLocationImage;
    @BindView(R.id.recruitment_location)
    TextView recruitmentLocation;
    @BindView(R.id.recruitment_workyear_image)
    ImageView recruitmentWorkyearImage;
    @BindView(R.id.recruitment_workyear)
    TextView recruitmentWorkyear;
    @BindView(R.id.recruitment_degree_image)
    ImageView recruitmentDegreeImage;
    @BindView(R.id.recruitment_degree)
    TextView recruitmentDegree;
    @BindView(R.id.recruitment_duration_image)
    ImageView recruitmentDurationImage;
    @BindView(R.id.recruitment_duration)
    TextView recruitmentDuration;
    @BindView(R.id.recruitment_workintroduce_image)
    ImageView recruitmentWorkintroduceImage;
    @BindView(R.id.recruitment_workintroduce)
    TextView recruitmentWorkintroduce;
    @BindView(R.id.recruitment_xifenjob_image)
    ImageView recruitmentXifenjobImage;
    @BindView(R.id.recruitment_xifenjob)
    TextView recruitmentXifenjob;
    @BindView(R.id.recruitment_line)
    ImageView recruitmentLine;
    @BindView(R.id.recruitment_companylogo)
    ImageView recruitmentCompanylogo;
    @BindView(R.id.recruitment_company)
    TextView recruitmentCompany;
    @BindView(R.id.recruitment_companyprofile)
    TextView recruitmentCompanyprofile;
    @BindView(R.id.recruitment_main_business)
    TextView recruitmentMainBusiness;
    @BindView(R.id.recruitment_arrow)
    ImageView recruitmentArrow;
    @BindView(R.id.recruitment_line1)
    ImageView recruitmentLine1;
    @BindView(R.id.recruitment_jobprofile_image)
    ImageView recruitmentJobprofileImage;
    @BindView(R.id.recruitment_jobprofile)
    TextView recruitmentJobprofile;
    @BindView(R.id.recruitment_line2)
    ImageView recruitmentLine2;
    @BindView(R.id.recruitment_jobprofile_detail)
    TextView recruitmentJobprofileDetail;
    @BindView(R.id.recruitment_line3)
    ImageView recruitmentLine3;
    @BindView(R.id.recruitment_joblocation_image)
    ImageView recruitmentJoblocationImage;
    @BindView(R.id.recruitment_joblocation)
    TextView recruitmentJoblocation;
    @BindView(R.id.recruitment_line4)
    ImageView recruitmentLine4;
    @BindView(R.id.recruitment_joblocation_detail)
    TextView recruitmentJoblocationDetail;
    @BindView(R.id.recruitment_line5)
    ImageView recruitmentLine5;
    @BindView(R.id.recruitment_jobreleaser_image)
    ImageView recruitmentJobreleaserImage;
    @BindView(R.id.recruitment_jobreleaser)
    TextView recruitmentJobreleaser;
    @BindView(R.id.recruitment_line6)
    ImageView recruitmentLine6;
    @BindView(R.id.recruitment_jobreleaser_head)
    ImageView recruitmentJobreleaserHead;
    @BindView(R.id.recruitment_jobreleaser_name)
    TextView recruitmentJobreleaserName;
    @BindView(R.id.recruitment_jobreleaser_position)
    TextView recruitmentJobreleaserPosition;
    @BindView(R.id.recruitment_releaser_arrow)
    ImageView recruitmentReleaserArrow;
    public int id;
    public Recruitment_Bean recruitment_bean;
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
        setContentView(R.layout.activity_recruitment);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {
        id = getIntent().getIntExtra("Id", 0);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("id", id);
        RequestBody requestBody = OkHttpManager.appendTokenPOST(OkHttpManager.JSON, map);
        OkHttpManager.httpPost("http://47.92.48.125/recruitment/detail", requestBody, new OkHttpManager.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d("recruitment获取的数据", result);
                Type type = new TypeToken<BaseResponse<Recruitment_Bean>>() {
                }.getType();
                BaseResponse<Recruitment_Bean> response = (BaseResponse<Recruitment_Bean>) GsonUtils.fromJson(result, type);
                recruitment_bean = response.getData();
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("recruitment获取的数据", e.toString());
            }
        });
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //职位
                    recruitmentPosition.setText(recruitment_bean.positionName);
                    //工资
                    recruitmentSalary.setText("【基本工资" + recruitment_bean.salaryMin + "-" + recruitment_bean.salaryMax + "+提成】");
                    //地点
                    recruitmentLocation.setText(recruitment_bean.location);
                    //工作年限要求
                    recruitmentWorkyear.setText(recruitment_bean.workYearsName);
                    //学历要求
                    recruitmentDegree.setText(recruitment_bean.degreeName);
                    //全职或半职
                    recruitmentDuration.setText(recruitment_bean.positionTypeName);
                    //工作要求
                    recruitmentWorkintroduce.setText(recruitment_bean.professionCategoryName + "-" + recruitment_bean.professionName);
                    //细分行业
                    recruitmentXifenjob.setText(recruitment_bean.titleName);
                    //公司logo
                    Glide.with(RecruitmentActivity.this).load(recruitment_bean.company.logo).into(recruitmentCompanylogo);
                    //公司短名
                    recruitmentCompany.setText(recruitment_bean.company.shortName);
                    //公司简介
                    recruitmentCompanyprofile.setText(recruitment_bean.company.typeName + "  |  " + recruitment_bean.company.sizeName);
                    //公司工作职责
                    recruitmentMainBusiness.setText(recruitment_bean.company.industryName);
                    //职位描述
                    recruitmentJobprofileDetail.setText(recruitment_bean.introduction);
                    //详细工作地点
                    recruitmentJoblocationDetail.setText(recruitment_bean.location + "  |  " + recruitment_bean.address);
                    //职位发布者的头像
                    Glide.with(RecruitmentActivity.this).load(recruitment_bean.user.avatar).into(recruitmentJobreleaserHead);
                    //职位发布者的姓名
                    recruitmentJobreleaserName.setText(recruitment_bean.user.name);
                    //职位发布者的职位
                    recruitmentJobreleaserPosition.setText(recruitment_bean.user.position);
                    break;
            }
        }
    };
    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("职位详情", View.VISIBLE);
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
                        intent.putExtra(Intent.EXTRA_TEXT, recruitment_bean.shareUrl); // 分享的内容
                        startActivity(Intent.createChooser(intent, ""));// 目标应用选择对话框的标题;
                        break;
                }
                return true;
            }
        });
    }


}
