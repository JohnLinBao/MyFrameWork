package com.alfred.framework.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Alfred on 2017/11/15.
 */

public class NetWorkUtil {

    /**
     * 移动连接
     * @param context
     * @return
     */
    public static boolean isInternetConnection(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager==null)
            return false;
        NetworkInfo info[] = manager.getAllNetworkInfo();
        for(NetworkInfo net : info){
            if(net.isConnected())
                return true;
        }
        return false;
    }

    /**
     * wifi连接
     * @param context
     * @return
     */
    public static boolean isWifiConnection(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        switch (wifiManager.getWifiState()){
            case WifiManager.WIFI_STATE_DISABLING:
            case WifiManager.WIFI_STATE_DISABLED:
            case WifiManager.WIFI_STATE_UNKNOWN:
            case WifiManager.WIFI_STATE_ENABLING:
                return false;
            case WifiManager.WIFI_STATE_ENABLED:
                return true;
            default:
                return false;
        }
    }

    /**
     * 打开网络设置界面
     */
    public static void openSysNetSetting(Context context){
        Intent intent=null;
        //判断手机系统的版本  即API大于10 就是3.0或以上版本
        if(android.os.Build.VERSION.SDK_INT>10){
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        }else{
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        context.startActivity(intent);
    }
}
