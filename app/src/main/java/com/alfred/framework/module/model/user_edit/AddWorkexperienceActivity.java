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
import com.alfred.framework.myframework.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/3/24.
 */

public class AddWorkexperienceActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.add_function_title)
    TextView addFunctionTitle;
    @BindView(R.id.add_function)
    TextView addFunction;
    @BindView(R.id.add_function_arrow)
    ImageView addFunctionArrow;
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
    private String industry = null;
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
        //选择细分行业
        addIndustry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent industryIntent = new Intent(AddWorkexperienceActivity.this, SelectIndustryActivity.class);
                startActivityForResult(industryIntent, INDUSTRY);
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
                        intent.putExtra("company", addCompany.getText().toString());
                        intent.putExtra("position", addPosition.getText().toString());
                        intent.putExtra("industry", industry);
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
                    String industry = intent.getStringExtra("industry");
                    addIndustry.setText(industry);
                }
                break;

            default:
        }
    }

}
