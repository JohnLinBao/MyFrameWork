package com.alfred.framework.utils;

import java.util.UUID;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetWoekUtils {

	/**
	 * �ж�����״���Ƿ����
	 * 
	 * @param context
	 * @return boolean true:����״������ false:����״��������
	 */
	
	public static boolean isNetWorks(Context context) {
		// TODO Auto-generated method stub
        ConnectivityManager cm = 
        		(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(cm==null)
        	return false;
        NetworkInfo info[] = cm.getAllNetworkInfo();
        for(NetworkInfo net : info){
        	if(net.isConnected())
        		return true;
        }
        return false;
	}
	
	/**
	 * ��ȡ�ֻ�ƽ����Ե�Ψһ��ʶ��
	 */
	public static String getImei(Context context){
		String imei = ((TelephonyManager)context.
				getSystemService(context.TELEPHONY_SERVICE)).getDeviceId();
		if(StringUtils.isEmpty(imei))
			return UUID.randomUUID().toString().trim().replace("-", "").substring(2, 17);
		else
			return imei;
	}
	
	/**
	 * 打开网络设置界面
	 * @author chenh
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
	//是否连接WIFI
    public static boolean isWifiConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetworkInfo.isConnected())
        {
            return true ;
        }
     
        return false ;
    }
}
