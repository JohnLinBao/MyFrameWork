package com.alfred.framework.module.model.user_edit;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.module.model.BaseUser_Bean;
import com.alfred.framework.myframework.R;
import com.bumptech.glide.Glide;

import static com.alfred.framework.base.config.AppConfig.editUser;
import butterknife.BindView;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.TimePicker;

import android.view.View;
/**
 * Created by asus on 2018/3/28.
 */

public class EditBaseUserActivity extends BaseActivity {
    @BindView(R.id.edit_baseuser_head_title)
    TextView editBaseuserHeadTitle;
    @BindView(R.id.edit_baseuser_head)
    ImageView editBaseuserHead;
    @BindView(R.id.edit_baseuser_name_title)
    TextView editBaseuserNameTitle;
    @BindView(R.id.edit_baseuser_name)
    EditText editBaseuserName;
    @BindView(R.id.edit_baseuser_sex_title)
    TextView editBaseuserSexTitle;
    @BindView(R.id.edit_baseuser_sex)
    TextView editBaseuserSex;
    @BindView(R.id.edit_baseuser_sex_arrow)
    ImageView editBaseuserSexArrow;
    @BindView(R.id.edit_baseuser_birthday_title)
    TextView editBaseuserBirthdayTitle;
    @BindView(R.id.edit_baseuser_birthday)
    TextView editBaseuserBirthday;
    @BindView(R.id.edit_baseuser_birthday_arrow)
    ImageView editBaseuserBirthdayArrow;
    @BindView(R.id.edit_baseuser_hometown_title)
    TextView editBaseuserHometownTitle;
    @BindView(R.id.edit_baseuser_hometown)
    TextView editBaseuserHometown;
    @BindView(R.id.edit_baseuser_hometown_arrow)
    ImageView editBaseuserHometownArrow;
    @BindView(R.id.edit_baseuser_workyearname_title)
    TextView editBaseuserWorkyearnameTitle;
    @BindView(R.id.edit_baseuser_workyearname)
    TextView editBaseuserWorkyearname;
    @BindView(R.id.edit_baseuser_workyearname_arrow)
    ImageView editBaseuserWorkyearnameArrow;
    @BindView(R.id.edit_baseuser_city_title)
    TextView editBaseuserCityTitle;
    @BindView(R.id.edit_baseuser_city)
    TextView editBaseuserCity;
    @BindView(R.id.edit_baseuser_city_arrow)
    ImageView editBaseuserCityArrow;
    @BindView(R.id.edit_baseuser_company_title)
    TextView editBaseuserCompanyTitle;
    @BindView(R.id.edit_baseuser_company)
    EditText editBaseuserCompany;
    @BindView(R.id.edit_baseuser_companyshortname_title)
    TextView editBaseuserCompanyshortnameTitle;
    @BindView(R.id.edit_baseuser_companyshortname)
    EditText editBaseuserCompanyshortname;
    @BindView(R.id.edit_baseuser_companylogo_title)
    TextView editBaseuserCompanylogoTitle;
    @BindView(R.id.edit_baseuser_companylogo)
    ImageView editBaseuserCompanylogo;
    @BindView(R.id.edit_baseuser_position_title)
    TextView editBaseuserPositionTitle;
    @BindView(R.id.edit_baseuser_position)
    EditText editBaseuserPosition;
    @BindView(R.id.edit_baseuser_titlename_title)
    TextView editBaseuserTitlenameTitle;
    @BindView(R.id.edit_baseuser_titlename)
    TextView editBaseuserTitlename;
    @BindView(R.id.edit_baseuser_titlename_arrow)
    ImageView editBaseuserTitlenameArrow;
    @BindView(R.id.edit_baseuser_register_title)
    TextView editBaseuserRegisterTitle;
    @BindView(R.id.edit_baseuser_register)
    EditText editBaseuserRegister;
    @BindView(R.id.edit_baseuser_industryName_title)
    TextView editBaseuserIndustryNameTitle;
    @BindView(R.id.edit_baseuser_industryName)
    TextView editBaseuserIndustryName;
    @BindView(R.id.edit_baseuser_industryName_arrow)
    ImageView editBaseuserIndustryNameArrow;
    @BindView(R.id.edit_baseuser_professionName_title)
    TextView editBaseuserProfessionNameTitle;
    @BindView(R.id.edit_baseuser_professionName)
    TextView editBaseuserProfessionName;
    @BindView(R.id.edit_baseuser_professionName_arrow)
    ImageView editBaseuserProfessionNameArrow;
    @BindView(R.id.edit_baseuser_phoneNumber_title)
    TextView editBaseuserPhoneNumberTitle;
    @BindView(R.id.edit_baseuser_phoneNumber)
    EditText editBaseuserPhoneNumber;
    @BindView(R.id.edit_baseuser_email_title)
    TextView editBaseuserEmailTitle;
    @BindView(R.id.edit_baseuser_email)
    EditText editBaseuserEmail;
    @BindView(R.id.edit_baseuser_brife_title)
    TextView editBaseuserBrifeTitle;
    @BindView(R.id.edit_baseuser_brife)
    EditText editBaseuserBrife;
    private BaseUser_Bean baseUser_bean = new BaseUser_Bean();
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
        setContentView(R.layout.activity_edit_baseuser);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {
        /**
         * 设置用户已填写的基本资料
         */
        if (editUser.avatar != null)
            Glide.with(EditBaseUserActivity.this).load(editUser.avatar).into(editBaseuserHead);
        if (editUser.name != null)
            editBaseuserName.setText(editUser.name);
        if (editUser.gender == 1)
            editBaseuserSex.setText("男");
        else if (editUser.gender == 2)
            editBaseuserSex.setText("女");
        if (editUser.birthday != null)
            editBaseuserBirthday.setText(editUser.birthday);
        if (editUser.hometown != null)
            editBaseuserHometown.setText(editUser.hometown);
        if (editUser.workYearsName != null)
            editBaseuserWorkyearname.setText(editUser.workYearsName);
        if (editUser.city != null)
            editBaseuserCity.setText(editUser.city);
        if (editUser.company != null)
            editBaseuserCompany.setText(editUser.company);
        if (editUser.companyShortName != null)
            editBaseuserCompanyshortname.setText(editUser.companyShortName);
        if (editUser.companyLogo != null)
            Glide.with(EditBaseUserActivity.this).load(editUser.companyLogo).into(editBaseuserCompanylogo);
        if (editUser.position != null)
            editBaseuserPosition.setText(editUser.position);
        if (editUser.titleName != null)
            editBaseuserTitlename.setText(editUser.titleName);
        if (editUser.register != null)
            editBaseuserRegister.setText(editUser.register);
        if (editUser.industryName != null)
            editBaseuserIndustryName.setText(editUser.industryName);
        if (editUser.professionName != null)
            editBaseuserProfessionName.setText(editUser.professionName);
        if (editUser.phoneNumber != null)
            editBaseuserPhoneNumber.setText(editUser.phoneNumber);
        if (editUser.email != null)
            editBaseuserEmail.setText(editUser.email);
        if (editUser.brief != null)
            editBaseuserBrife.setText(editUser.brief);

        /**
         * 设置各个选择器的监听事件
         */
        /*选择性别*/
        editBaseuserSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionPicker sexPicker = new OptionPicker(EditBaseUserActivity.this, new String[]{"男", "女"} );
                sexPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        editBaseuserSex.setText(option);
                        if (option == "男")
                            baseUser_bean.gender = 1;
                        if (option == "女")
                            baseUser_bean.gender = 2;
                    }
                });
                sexPicker.show();
            }
        });

        /*选择生日*/
        editBaseuserBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(EditBaseUserActivity.this);
                datePicker.setRange(1910, 2018);
                datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        baseUser_bean.birthday = year + "-" + month + "-" + day;
                        editBaseuserBirthday.setText(baseUser_bean.birthday);
                    }
                });
                datePicker.show();
            }
        });

        /*选择家乡*/
        
    }

    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("编辑基本资料", View.VISIBLE);
        //取消
        barProxy.getToolbar().setNavigationIcon(R.drawable.guanbi_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //保存
        barProxy.getToolbar().inflateMenu(R.menu.activity_menu_save);
        barProxy.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_save:
                        break;
                }
                return true;
            }
        });
    }

}
