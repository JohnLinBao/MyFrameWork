package com.alfred.framework.module.model.user_edit;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.myframework.R;

/**
 * Created by asus on 2018/3/24.
 */

public class AddProjectActivity extends BaseActivity {
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
                switch (item.getItemId()){
                    case R.id.action_save:

                        break;
                }
                return true;
            }
        });
    }
}
