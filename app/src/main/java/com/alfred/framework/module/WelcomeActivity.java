package com.alfred.framework.module;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.myframework.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by asus on 2018/2/18.
 */

public class WelcomeActivity extends BaseActivity {


    @BindView(R.id.welcome_hy)
    ImageView welcomeHy;
    @BindView(R.id.welcome_gg_content)
    ImageView welcomeGgContent;
    @BindView(R.id.welcome_gg_timer)
    TextView welcomeGgTimer;
    @BindView(R.id.welcome_gg)
    RelativeLayout welcomeGg;
    @BindView(R.id.welcome_guide_content)
    ViewPager welcomeGuideContent;
    @BindView(R.id.welcome_guide_register)
    Button welcomeGuideRegister;
    @BindView(R.id.welcome_guide_login)
    Button welcomeGuideLogin;
    @BindView(R.id.welcome_guide_tv)
    TextView welcomeGuideTv;
    @BindView(R.id.welcome_guide_QQ)
    ImageView welcomeGuideQQ;
    @BindView(R.id.welcome_guide)
    RelativeLayout welcomeGuide;
    int time = 5;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    welcomeHy.setVisibility(View.GONE);
                    welcomeGg.setVisibility(View.VISIBLE);
                    Glide.with(WelcomeActivity.this).load("http://img3.imgtn.bdimg.com/it/u=576260586,2346360963&fm=27&gp=0.jpg").into(welcomeGgContent);
                    timer.start();
                    break;
                case 0:
                    welcomeGgTimer.setText(time + "秒");
                    time--;
                    break;
                case 2:
                    welcomeGg.setVisibility(View.GONE);
                    welcomeGuide.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    public CountDownTimer timer = new CountDownTimer(6000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            handler.sendEmptyMessage(0);
        }

        @Override
        public void onFinish() {
            handler.sendEmptyMessage(2);
        }
    };



    @Override
    public void reload() {

    }

    @Override
    public void ShowNetWort(boolean isshow) {

    }

    @Override
    public void initialize() {

        welcomeGgContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        welcomeGgTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(2);
            }
        });
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.width = ViewPager.LayoutParams.MATCH_PARENT;
        params.height = ViewPager.LayoutParams.MATCH_PARENT;
        ImageView view1 = new ImageView(this);
        ImageView view2 = new ImageView(this);
        ImageView view3 = new ImageView(this);
        view1.setLayoutParams(params);
        view2.setLayoutParams(params);
        view3.setLayoutParams(params);
        view1.setImageResource(R.drawable.yindaoye01);
        view2.setImageResource(R.drawable.yindaoye02);
        view3.setImageResource(R.drawable.yindaoye03);
        final List<View> mListViews = new ArrayList<View>();
        mListViews.add(view1);
        mListViews.add(view2);
        mListViews.add(view3);
        PagerAdapter adapter = new PagerAdapter() {

            //直接继承PagerAdapter，至少必须重写下面的四个方法，否则会报错
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mListViews.get(position));//删除页卡
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //这个方法用来实例化页卡
                container.addView(mListViews.get(position), 0);//添加页卡
                return mListViews.get(position);
            }

            @Override
            public int getCount() {
                return mListViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        welcomeGuideContent.setAdapter(adapter);
        welcomeGuideContent.setCurrentItem(0);
    }

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {
        welcomeHy.setVisibility(View.VISIBLE);
        handler.sendEmptyMessageDelayed(1, 2000);
        welcomeGuideRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, RegisterActivityOne.class);
                startActivity(intent);
            }
        });
        welcomeGuideLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void hanldeToolbar() {

    }



}
