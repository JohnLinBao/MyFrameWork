package com.alfred.framework.base;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.utils.SharePreferenceUtils;

/**
 * 
 * @author Alfred
 *
 */
public class BaseApplication extends Application{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SharePreferenceUtils.init(getApplicationContext());
		OkHttpManager.initOkHttpManager(this);//初始化OKGO异步加载框架
		MultiDex.install(this);
	}
}
