package com.alfred.framework.module.model.user_edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.module.model.WorkExperience_Bean;
import com.alfred.framework.myframework.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.DatePicker;
import static com.alfred.framework.base.config.AppConfig.editUser;

/**
 * Created by asus on 2018/3/24.
 */

public class AddWorkexperienceActivity extends BaseActivity {


    @BindView(R.id.add_company_title)
    TextView addCompanyTitle;
    @BindView(R.id.add_company)
    EditText addCompany;
    @BindView(R.id.add_companylogo_title)
    TextView addCompanylogoTitle;
    @BindView(R.id.add_companylogo)
    ImageView addCompanylogo;
    @BindView(R.id.add_position_title)
    TextView addPositionTitle;
    @BindView(R.id.add_position)
    EditText addPosition;
    @BindView(R.id.add_industry_title)
    TextView addIndustryTitle;
    @BindView(R.id.add_industry)
    TextView addIndustry;
    @BindView(R.id.add_industry_arrow)
    ImageView addIndustryArrow;
    @BindView(R.id.add_profession_title)
    TextView addProfessionTitle;
    @BindView(R.id.add_profession)
    TextView addProfession;
    @BindView(R.id.add_profession_arrow)
    ImageView addProfessionArrow;
    @BindView(R.id.add_starttime_title)
    TextView addStarttimeTitle;
    @BindView(R.id.add_starttime)
    TextView addStarttime;
    @BindView(R.id.add_starttime_arrow)
    ImageView addStarttimeArrow;
    @BindView(R.id.add_endtime_title)
    TextView addEndtimeTitle;
    @BindView(R.id.add_endtime)
    TextView addEndtime;
    @BindView(R.id.add_endtime_arrow)
    ImageView addEndtimeArrow;
    @BindView(R.id.add_workdetail_title)
    TextView addWorkdetailTitle;
    @BindView(R.id.add_workdetail)
    EditText addWorkdetail;
    final int INDUSTRY = 1;
    final int PROFESSION = 2;
    @BindView(R.id.delete_workexperience)
    TextView deleteWorkexperience;
    private WorkExperience_Bean workExperience_bean = new WorkExperience_Bean();
    private int position = -1;

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
        setContentView(R.layout.activity_add_workexperience);

    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {
        /**
         * 判断workexperience是否为空
         */
        position = getIntent().getIntExtra("position", -1);
        if (position != -1) {
            deleteWorkexperience.setVisibility(View.VISIBLE);
            workExperience_bean = editUser.workExperience.get(position);
            addCompany.setText(workExperience_bean.company);
            if (workExperience_bean.companyLogo != "")
                Glide.with(AddWorkexperienceActivity.this).load(workExperience_bean.companyLogo).into(addCompanylogo);
            else
                addCompanylogo.setImageResource(R.drawable.shangchuanzhaopian);
            addPosition.setText(workExperience_bean.position);
            addIndustry.setText(workExperience_bean.industryName);
            addProfession.setText(workExperience_bean.professionName);
            addStarttime.setText(workExperience_bean.startTime);
            addEndtime.setText(workExperience_bean.endTime);
            addWorkdetail.setText(workExperience_bean.introduction);
        }
        //选择细分行业
        addIndustry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent industryIntent = new Intent(AddWorkexperienceActivity.this, SelectIndustryActivity.class);
                startActivityForResult(industryIntent, INDUSTRY);
            }
        });

        //选择职能/专业
        addProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent professionIntent = new Intent(AddWorkexperienceActivity.this, SelectProfessionActivity.class);
                startActivityForResult(professionIntent, PROFESSION);
            }
        });

        //选择开始时间
        addStarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(AddWorkexperienceActivity.this);
                datePicker.setRange(1990, 2018);
                datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        workExperience_bean.startTime = year + "-" + month + "-" + day;
                        addStarttime.setText(workExperience_bean.startTime);
                    }
                });
                datePicker.show();
            }
        });

        //选择结束时间时间
        addEndtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker1 = new DatePicker(AddWorkexperienceActivity.this);
                datePicker1.setRange(1990, 2018);
                datePicker1.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        workExperience_bean.endTime = year + "-" + month + "-" + day;
                        addEndtime.setText(workExperience_bean.endTime);
                    }
                });
                datePicker1.show();
            }
        });

        //删除
        deleteWorkexperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser.workExperience.remove(position);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("工作经历", View.VISIBLE);
        barProxy.getToolbar().setNavigationIcon(R.drawable.guanbi_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        barProxy.getToolbar().inflateMenu(R.menu.activity_menu_save);
        barProxy.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        Intent intent = new Intent();
                        workExperience_bean.company = addCompany.getText().toString();
                        workExperience_bean.position = addPosition.getText().toString();
                        workExperience_bean.introduction = addWorkdetail.getText().toString();
                        if (position != -1){
                            editUser.workExperience.remove(position);
                            editUser.workExperience.add(position, workExperience_bean);
                        }else
                            editUser.workExperience.add(workExperience_bean);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case INDUSTRY:
                if (resultCode == RESULT_OK) {
                    workExperience_bean.industryName = intent.getStringExtra("industryName");
                    workExperience_bean.industry = intent.getIntExtra("industry", 0);
                    addIndustry.setText(workExperience_bean.industryName);
                }
                break;
            case PROFESSION:
                if (resultCode == RESULT_OK) {
                    workExperience_bean.professionName = intent.getStringExtra("professionName");
                    workExperience_bean.profession = intent.getIntExtra("profession", 0);
                    addProfession.setText(workExperience_bean.professionName);
                }
                break;
            default:
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
