package com.alfred.framework.module.model.user_edit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.model.Common_Bean;
import com.alfred.framework.module.model.Education_Bean;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import okhttp3.RequestBody;

import static com.alfred.framework.base.config.AppConfig.editUser;

/**
 * Created by asus on 2018/3/24.
 */

public class AddEducationActivity extends BaseActivity {


    @BindView(R.id.add_school_title)
    TextView addSchoolTitle;
    @BindView(R.id.add_school)
    EditText addSchool;
    @BindView(R.id.add_line)
    ImageView addLine;
    @BindView(R.id.add_major_title)
    TextView addMajorTitle;
    @BindView(R.id.add_major)
    EditText addMajor;
    @BindView(R.id.add_line1)
    ImageView addLine1;
    @BindView(R.id.add_degree_title)
    TextView addDegreeTitle;
    @BindView(R.id.add_degree)
    TextView addDegree;
    @BindView(R.id.add_degree_arrow)
    ImageView addDegreeArrow;
    @BindView(R.id.add_line2)
    ImageView addLine2;
    @BindView(R.id.add_starttime_title)
    TextView addStarttimeTitle;
    @BindView(R.id.add_starttime)
    TextView addStarttime;
    @BindView(R.id.add_starttime_arrow)
    ImageView addStarttimeArrow;
    @BindView(R.id.add_line3)
    ImageView addLine3;
    @BindView(R.id.add_endtime_title)
    TextView addEndtimeTitle;
    @BindView(R.id.add_endtime)
    TextView addEndtime;
    @BindView(R.id.add_endtime_arrow)
    ImageView addEndtimeArrow;
    @BindView(R.id.add_line4)
    ImageView addLine4;
    @BindView(R.id.delete_education)
    TextView deleteEducation;
    private List<Common_Bean.CommonList> degreeList;
    private Education_Bean education_bean = new Education_Bean();
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
        setContentView(R.layout.activity_add_education);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {
        /**
         * 判断education是否为空
         */
        position = getIntent().getIntExtra("position", -1);
        if (position != -1) {
            education_bean = editUser.education.get(position);
            deleteEducation.setVisibility(View.VISIBLE);
            addSchool.setText(education_bean.schoolName);
            addMajor.setText(education_bean.major);
            addDegree.setText(education_bean.degreeName);
            addStarttime.setText(education_bean.startTime);
            addEndtime.setText(education_bean.endTime);
        }
        //选择学历
        addDegree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("", "");
                final RequestBody requestBody = OkHttpManager.appendPOST(OkHttpManager.JSON, map);
                OkHttpManager.httpPost("http://47.92.48.125/dict/listDictDegree", requestBody, new OkHttpManager.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        Type type = new TypeToken<BaseResponse<Common_Bean>>() {
                        }.getType();
                        BaseResponse<Common_Bean> response = (BaseResponse<Common_Bean>) GsonUtils.fromJson(result, type);
                        degreeList = response.getData().list;
                        handler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }
        });

        //选择开始时间
        addStarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(AddEducationActivity.this);
                datePicker.setRange(1990, 2018);
                datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        education_bean.startTime = year + "-" + month + "-" + day;
                        addStarttime.setText(education_bean.startTime);
                    }
                });
                datePicker.show();
            }
        });

        //选择结束时间时间
        addEndtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker1 = new DatePicker(AddEducationActivity.this);
                datePicker1.setRange(1990, 2018);
                datePicker1.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        education_bean.endTime = year + "-" + month + "-" + day;
                        addEndtime.setText(education_bean.endTime);
                    }
                });
                datePicker1.show();
            }
        });

        //删除
        deleteEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser.education.remove(position);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String[] list = new String[]{degreeList.get(0).name, degreeList.get(1).name, degreeList.get(2).name,
                            degreeList.get(3).name, degreeList.get(4).name};
                    OptionPicker educationPicker = new OptionPicker(AddEducationActivity.this, list);
                    educationPicker.setOffset(2);
                    educationPicker.setSelectedIndex(0);
                    educationPicker.setTextSize(11);
                    educationPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(String option) {
                            education_bean.degreeName = option;
                            for (int i = 0; i < degreeList.size(); i++)
                                if (option == degreeList.get(i).name)
                                    education_bean.degree = i + 1;
                            addDegree.setText(option);
                        }
                    });
                    educationPicker.show();
                    break;
            }
        }
    };

    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("教育经历", View.VISIBLE);
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
                        education_bean.schoolName = addSchool.getText().toString();
                        education_bean.major = addMajor.getText().toString();
                        if (position != -1) {
                            editUser.education.remove(position);
                            editUser.education.add(position, education_bean);
                        }else
                            editUser.education.add(education_bean);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
