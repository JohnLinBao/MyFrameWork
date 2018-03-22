package com.alfred.framework.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Alfred on 2016/12/2.
 * 沉浸式：此模式所表现的效果，我们可以把它看作钓鱼时鱼竿上“浮头”。开始时就是鱼咬钩的状态，浮头消失在水面下。当鱼松口后，浮头会自动出现 浮上来。
 */

public class StatusBarUtils {
    /**
     * 5.0以后修改状态栏颜色
     * @param activity
     * @param colorResId
     */
    public static void setWindowStatusBarColor_LOLLIPOP(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));
                ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                    ViewCompat.setFitsSystemWindows(mChildView, true);
                }
                //底部导航栏
                window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 4.4以后半透明状态栏 通过布局的背景颜色改变状态栏颜色
     * 半透明状态栏 布局显示到状态栏后面
     */
    public static void setStatusBarColor_KITKAT(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 完全隐藏导航栏
     * IMMERSIVE 只用于NAVIGATION时有效
     * @param activity
     */
    public static void hideNavigationBar(Activity activity){
        View decorView = activity.getWindow().getDecorView();
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //隐藏导航栏
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //SYSTEM_UI_FLAG_HIDE_NAVIGATION 隐藏导航栏
            //SYSTEM_UI_FLAG_IMMERSIVE 保持导航栏的隐藏状态 不设置此标识 导航栏向内滑动会重新显示 API19
            flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        }
        decorView.setSystemUiVisibility(flag);
    }

    /**
     * 沉浸隐藏导航栏
     * IMMERSIVE_STICKY 可用于NAVIGATION显示后，自动隐藏（进过一个短暂的时间）
     * @param activity
     */
    public static void hideNavigationBar_Auto(Activity activity){
        View decorView = activity.getWindow().getDecorView();
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //隐藏导航栏
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //SYSTEM_UI_FLAG_HIDE_NAVIGATION 隐藏导航栏
            //SYSTEM_UI_FLAG_IMMERSIVE 保持导航栏的隐藏状态 不设置此标识 导航栏向内滑动会重新显示 API19
            flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        decorView.setSystemUiVisibility(flag);
    }

    /**
     * 沉浸隐藏状态栏
     * SYSTEM_UI_FLAG_IMMERSIVE_STICKY与SYSTEM_UI_FLAG_IMMERSIVE的区别除了
     * 后者只能适用于Navigation而前者可用于两者（status和navigation）。
     * 前者的效果是status和navigation会自动重新收缩隐藏。
     */
    public static void hideWindowStatusBar_Auto(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            flag = View.SYSTEM_UI_FLAG_FULLSCREEN; //隐藏状态栏
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //SYSTEM_UI_FLAG_FULLSCREEN 隐藏导航栏
            //SYSTEM_UI_FLAG_IMMERSIVE_STICKY 保持导航栏的隐藏状态 不设置此标识 导航栏向内滑动会重新显示 API19
            flag = View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        decorView.setSystemUiVisibility(flag);
    }


    /**
     * 全屏
     * 注：状态栏无法完全隐藏
     */
    public static void fullWindow(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            flag = View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            flag = View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        decorView.setSystemUiVisibility(flag);
    }
}
