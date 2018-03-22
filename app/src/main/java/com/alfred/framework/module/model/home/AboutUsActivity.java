package com.alfred.framework.module.model.home;

import android.os.Bundle;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.myframework.R;

import android.view.View;

/**
 * Created by asus on 2018/3/21.
 */

public class AboutUsActivity extends BaseActivity {
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
        setContentView(R.layout.activity_setup_aboutus);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {

    }

    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("关于筑链", View.VISIBLE);
        barProxy.getToolbar().setNavigationIcon(R.drawable.back_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
