package com.alfred.framework.module.model.user_edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.model.Common_Bean;
import com.alfred.framework.module.model.Project_Bean;
import com.alfred.framework.module.model.Province_Bean;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.GsonUtils;
import com.alfred.framework.widget.AddressPopWindow;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
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

public class AddProjectActivity extends BaseActivity {

    @BindView(R.id.add_projectname_title)
    TextView addProjectnameTitle;
    @BindView(R.id.add_projectname)
    EditText addProjectname;
    @BindView(R.id.add_company_title)
    TextView addCompanyTitle;
    @BindView(R.id.add_company)
    EditText addCompany;
    @BindView(R.id.add_duty_title)
    TextView addDutyTitle;
    @BindView(R.id.add_duty)
    TextView addDuty;
    @BindView(R.id.add_duty_arrow)
    ImageView addDutyArrow;
    @BindView(R.id.add_location_title)
    TextView addLocationTitle;
    @BindView(R.id.add_location)
    TextView addLocation;
    @BindView(R.id.add_location_arrow)
    ImageView addLocationArrow;
    @BindView(R.id.add_type_title)
    TextView addTypeTitle;
    @BindView(R.id.add_type)
    EditText addType;
    @BindView(R.id.add_area_title)
    TextView addAreaTitle;
    @BindView(R.id.add_area)
    EditText addArea;
    @BindView(R.id.add_area_centiare)
    TextView addAreaCentiare;
    @BindView(R.id.add_scale_title)
    TextView addScaleTitle;
    @BindView(R.id.add_uplayer_count)
    EditText addUplayerCount;
    @BindView(R.id.add_uplayer)
    TextView addUplayer;
    @BindView(R.id.add_downlayer_count)
    EditText addDownlayerCount;
    @BindView(R.id.add_downlayer)
    TextView addDownlayer;
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
    @BindView(R.id.add_description_title)
    TextView addDescriptionTitle;
    @BindView(R.id.add_description)
    EditText addDescription;
    @BindView(R.id.add_photo_title)
    TextView addPhotoTitle;
    @BindView(R.id.add_photo)
    ImageView addPhoto;
    @BindView(R.id.add_photo_linearlayout)
    LinearLayout addPhotoLinearlayout;
    @BindView(R.id.delete_project)
    TextView deleteProject;
    private List<Common_Bean.CommonList> dutyList;
    private List<Province_Bean> province_bean;
    private Project_Bean project_bean = new Project_Bean();
    private String address;
    private int position = -1;
    private List<String> pictures = new ArrayList<>();
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
        setContentView(R.layout.activity_add_project);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {
        /**
         * 判断project是否为空
         */
        position = getIntent().getIntExtra("position", -1);
        if (position != -1){
            project_bean = editUser.project.get(position);
            deleteProject.setVisibility(View.VISIBLE);
            addProjectname.setText(project_bean.name);
            addCompany.setText(project_bean.company);
            addDuty.setText(project_bean.roleName);
            addLocation.setText(project_bean.location);
            addType.setText(project_bean.type);
            addArea.setText(project_bean.area);
            addUplayerCount.setText(project_bean.floorOverground);
            addDownlayerCount.setText(project_bean.floorUnderground);
            addStarttime.setText(project_bean.startTime);
            addEndtime.setText(project_bean.endTime);
            addDescription.setText(project_bean.introduction);
            if (project_bean.pictures.size()!= 0)
                Glide.with(AddProjectActivity.this).load(project_bean.pictures.get(0)).into(addPhoto);
            else
                addPhoto.setImageResource(R.drawable.shangchuanzhaopian);
        }
        //获取职责/角色字典
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        RequestBody requestBody = OkHttpManager.appendPOST(OkHttpManager.JSON, map);
        OkHttpManager.httpPost("http://47.92.48.125/dict/listDictProjectRole", requestBody, new OkHttpManager.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<BaseResponse<Common_Bean>>() {
                }.getType();
                BaseResponse<Common_Bean> response = (BaseResponse<Common_Bean>) GsonUtils.fromJson(result, type);
                dutyList = response.getData().list;
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        //选择职责/角色
        addDuty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dutyList != null) {
                    String[] list = new String[]{dutyList.get(0).name, dutyList.get(1).name, dutyList.get(2).name};
                    OptionPicker dutyPicker = new OptionPicker(AddProjectActivity.this, list);
                    dutyPicker.setOffset(2);
                    dutyPicker.setSelectedIndex(0);
                    dutyPicker.setTextSize(11);
                    dutyPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(String option) {
                            project_bean.roleName = option;
                            for (int i = 0; i < dutyList.size(); i++)
                                if (option == dutyList.get(i).name)
                                    project_bean.role = i + 1;
                            addDuty.setText(option);
                        }
                    });
                    dutyPicker.show();
                }
            }
        });
        //选择项目地点
        addLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    InputStream inputStream = getResources().getAssets().open("province.json");
                    int lenght = inputStream.available();
                    byte[] buffer = new byte[lenght];
                    inputStream.read(buffer);
                    address = new String(buffer, "utf8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Type type = new TypeToken<List<Province_Bean>>() {
                }.getType();
                province_bean = (List<Province_Bean>) GsonUtils.fromJson(address, type);
                AddressPopWindow addressPopWindow = new AddressPopWindow(AddProjectActivity.this, province_bean);
                addressPopWindow.showPopupWindow(v);
                addressPopWindow.setOnSureClickListener(new AddressPopWindow.OnSureClickListener() {
                    @Override
                    public void OnSureClick(String city) {
                        project_bean.location = city;
                        addLocation.setText(city);
                    }
                });
            }
        });
        //选择开始时间
        addStarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(AddProjectActivity.this);
                datePicker.setRange(1990, 2018);
                datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        project_bean.startTime = year + "-" + month + "-" + day;
                        addStarttime.setText(year + "-" + month + "-" + day);
                    }
                });
                datePicker.show();
            }
        });

        //选择结束时间时间
        addEndtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker1 = new DatePicker(AddProjectActivity.this);
                datePicker1.setRange(1990, 2018);
                datePicker1.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        project_bean.endTime =  year + "-" + month + "-" + day;
                        addEndtime.setText(year + "-" + month + "-" + day);
                    }
                });
                datePicker1.show();
            }
        });

        //删除
        deleteProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser.project.remove(position);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("项目经历", View.VISIBLE);
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
                        project_bean.name = addProjectname.getText().toString();
                        project_bean.company = addCompany.getText().toString();
                        project_bean.type = addType.getText().toString();
                        project_bean.area = addArea.getText().toString();
                        project_bean.floorOverground = addUplayerCount.getText().toString();
                        project_bean.floorUnderground = addDownlayerCount.getText().toString();
                        project_bean.introduction = addDescription.getText().toString();
                        if (project_bean.pictures.size() == 0)
                            project_bean.pictures = pictures;
                        if (position != -1){
                            editUser.project.remove(position);
                            editUser.project.add(position, project_bean);
                        }else
                            editUser.project.add(project_bean);
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
