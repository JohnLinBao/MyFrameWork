package com.alfred.framework.module;

import android.os.Bundle;
import android.widget.TextView;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.myframework.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/2/23.
 */

public class UserAgreementActivity extends BaseActivity {
    @BindView(R.id.user_agreement)
    TextView userAgreement;

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
        setContentView(R.layout.activity_user_agreement);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {

    }

    @Override
    public void hanldeToolbar() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
