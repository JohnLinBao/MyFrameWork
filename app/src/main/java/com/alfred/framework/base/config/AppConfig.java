package com.alfred.framework.base.config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.alfred.framework.module.model.Dynamic_Bean;
import com.alfred.framework.module.model.Login_Data;
import com.alfred.framework.module.model.User_Bean;
import com.alfred.framework.utils.StringUtils;

import java.util.List;

/**
 * APP相关的基本配置参数
 */
public class AppConfig {

	/**
	 * 存放User信息
	 * 存放TopicList
	 * 用于
	 */
	public static User_Bean user;
	
	/**
	 * app所有信息存放空间索引，缓存配置
	 * 用于 SharedPreferences
	 */
	public static String APP_INFO = "APP_INFO";
	/**
	 * 是否登陆标志字段名
	 */
	public static String TOKEN = "token";
	/**
	 * 缓存到Share的用户名与密码的字段名
	 */
	public static String USNAME = "USNAME";
	public static String USPWD = "USPWD";
    
    // 取得版本号
    public static String GetVersion(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return manager.versionName;
		} catch (NameNotFoundException e) {
			return "Unknown";
		}
	}
    
    
    /**
     * 取得系统唯一标识
     * @param context
     * @param imei
     * @return
     */
	public static String getImei(Context context, String imei) {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imei = telephonyManager.getDeviceId();
		} catch (Exception e) {
			Log.e(StringUtils.class.getSimpleName(), e.getMessage());
		}
		return imei;
	}


}
