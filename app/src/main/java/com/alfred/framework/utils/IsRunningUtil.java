package com.alfred.framework.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;

public class IsRunningUtil {
	/**
	 * Activity是否显示
	 * @param mContext
	 * @param activityClassName
	 * @return
	 */
	public static boolean isActivityRunning(Context mContext,String activityClassName){  
	    ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);  
	    List<RunningTaskInfo> info = activityManager.getRunningTasks(1);  
	    if(info != null && info.size() > 0){  
	        ComponentName component = info.get(0).topActivity;  
	        if(activityClassName.equals(component.getClassName())){  
	            return true;  
	            }  
	        }  
	    return false;  
	}

	/**
	 * 服务是否处在运行状态
	 * @param mContext
	 * @param className
	 * @return
	 */
	public static boolean isServiceRunning(Context mContext,String className) {  
        boolean isRunning = false;  
        ActivityManager activityManager = (ActivityManager)  
        mContext.getSystemService(Context.ACTIVITY_SERVICE);   
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);  
  
        if (!(serviceList.size()>0)) {  
            return false;  
        }  
  
        for (int i=0; i<serviceList.size(); i++) {  
            if (serviceList.get(i).service.getClassName().equals(className) == true) {  
                isRunning = true;  
                break;  
            }  
        }  
        return isRunning;  
	}

	/**
	 * 应用是否处在后台运行
	 * @param context
	 * @return
	 */
	public static boolean isAppInForeground(Context context) {  
	    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  
	    List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();  
	    for (RunningAppProcessInfo appProcess : appProcesses) {  
	        if (appProcess.processName.equals(context.getPackageName())) {  
	            return appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND;  
	        }  
	    }  
	    return false;  
	}
}
