package com.alfred.framework.module.model.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.myframework.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;

/**
 * Created by asus on 2018/3/1.
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.maincontent)
    FrameLayout maincontent;
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;

    private int i = 0;
    private String texts[] = {"主页", "筑链", "消息", "我的"};
    private int imageTab[] = {R.drawable.selector_homepage, R.drawable.selector_builderlink,
            R.drawable.selector_message, R.drawable.selector_user};
    private Class fragmentArray[] = {HomepageFragment.class, BuilderlinkFragment.class,MessageFragment.class, UserFragment.class};

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
        setContentView(R.layout.activity_main);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {

        tabhost.setup(MainActivity.this, getSupportFragmentManager(),
                R.id.maincontent);
        for (i = 0; i < texts.length; i++) {
            TabHost.TabSpec spec = tabhost.newTabSpec(texts[i]).setIndicator(getView(i));
            tabhost.addTab(spec, fragmentArray[i], null);

        }

        //      去掉分隔的竖线
        tabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        tabhost.setCurrentTabByTag(texts[0]);
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                if(tabId.equals("主页"))
                    barProxy.setTitle("主页", View.VISIBLE);
                if(tabId.equals("筑链"))
                    barProxy.setTitle("筑链", View.VISIBLE);
                if(tabId.equals("消息"))
                    barProxy.setTitle("消息", View.VISIBLE);
                if(tabId.equals("我的"))
                    barProxy.setTitle("我的", View.VISIBLE);
            }
        });
//        for (j = 0; j < texts.length; j++) {
//            tabhost.getTabWidget().getChildAt(j).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    switch (j) {
//                        case 0:
//                            barProxy.setTitle("主页", View.VISIBLE);
//                            break;
//                        case 1:
//                            barProxy.setTitle("筑链", View.VISIBLE);
//                            break;
//                        case 2:
//                            barProxy.setTitle("消息", View.VISIBLE);
//                            break;
//                        case 3:
//                            barProxy.setTitle("我的", View.VISIBLE);
//                            break;
//                    }
//                }
//            });

    }

    private View getView(int i) {
        //取得布局实例
        View view = View.inflate(MainActivity.this, R.layout.activity_tabcontent, null);

        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.image);

        //设置图标
        imageView.setImageResource(imageTab[i]);
        return view;
    }

    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("主页", View.VISIBLE);
    }

}
