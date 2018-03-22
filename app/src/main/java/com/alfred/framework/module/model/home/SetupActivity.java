package com.alfred.framework.module.model.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.module.EditPasswordActivity;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.DataCleanManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/3/21.
 */

public class SetupActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.setup_newmessage)
    TextView setupNewmessage;
    @BindView(R.id.setup_newmessage_arrow)
    ImageView setupNewmessageArrow;
    @BindView(R.id.setup_newmessage_linearlayout)
    LinearLayout setupNewmessageLinearlayout;
    @BindView(R.id.setup_line)
    ImageView setupLine;
    @BindView(R.id.setup_editpassword)
    TextView setupEditpassword;
    @BindView(R.id.setup_editpassword_arrow)
    ImageView setupEditpasswordArrow;
    @BindView(R.id.setup_editpassword_linearlayout)
    LinearLayout setupEditpasswordLinearlayout;
    @BindView(R.id.setup_line1)
    ImageView setupLine1;
    @BindView(R.id.setup_clearcache)
    TextView setupClearcache;
    @BindView(R.id.setup_clearcache_size)
    TextView setupClearcacheSize;
    @BindView(R.id.setup_clearcache_arrow)
    ImageView setupClearcacheArrow;
    @BindView(R.id.setup_clearcache_linearlayout)
    LinearLayout setupClearcacheLinearlayout;
    @BindView(R.id.setup_line2)
    ImageView setupLine2;
    @BindView(R.id.setup_feedback)
    TextView setupFeedback;
    @BindView(R.id.setup_feedback_arrow)
    ImageView setupFeedbackArrow;
    @BindView(R.id.setup_feedback_linearlayout)
    LinearLayout setupFeedbackLinearlayout;
    @BindView(R.id.setup_line3)
    ImageView setupLine3;
    @BindView(R.id.setup_goodreputation)
    TextView setupGoodreputation;
    @BindView(R.id.setup_goodreputation_arrow)
    ImageView setupGoodreputationArrow;
    @BindView(R.id.setup_goodreputation_linearlayout)
    LinearLayout setupGoodreputationLinearlayout;
    @BindView(R.id.setup_line4)
    ImageView setupLine4;
    @BindView(R.id.setup_aboutus)
    TextView setupAboutus;
    @BindView(R.id.setup_aboutus_arrow)
    ImageView setupAboutusArrow;
    @BindView(R.id.setup_aboutus_linearlayout)
    LinearLayout setupAboutusLinearlayout;
    @BindView(R.id.setup_logout)
    TextView setupLogout;

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
        setContentView(R.layout.activity_setup);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {
        /*新消息通知*/
        setupNewmessageLinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetupActivity.this, NewMessageActivity.class);
                startActivity(intent);
            }
        });
        /*修改密码*/
        setupEditpasswordLinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetupActivity.this, EditPasswordActivity.class);
                startActivity(intent);
            }
        });
        /*清理缓存*/
        setupClearcacheSize.setText(DataCleanManager.getTotalCacheSize(SetupActivity.this));  //显示缓存大小
        setupClearcacheLinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(SetupActivity.this);  //清理缓存
                setupClearcacheSize.setText(DataCleanManager.getTotalCacheSize(SetupActivity.this)); //显示清理完后的缓存大小
            }
        });
        /*关于筑链*/
        setupAboutusLinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetupActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("系统设置", View.VISIBLE);
        barProxy.getToolbar().setNavigationIcon(R.drawable.back_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
