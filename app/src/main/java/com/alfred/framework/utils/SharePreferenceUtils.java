package com.alfred.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alfred.framework.base.config.AppConfig;

public class SharePreferenceUtils {

	private static SharedPreferences sharedpreferences;
	
	public static void init(Context context){
		if(sharedpreferences==null) {
			sharedpreferences = context.getSharedPreferences(AppConfig.APP_INFO, Context.MODE_PRIVATE);
		}
	}
	
	public SharePreferenceUtils(){
		
	}
	
	/**
	 * 单个数据存储
	 * @param key  存执的键
	 * @param value  需要存的值
	 * @author chenh
	 */
	public static boolean saveDatatoShare(String key,String value){
		
		try {
			SharedPreferences.Editor editor = sharedpreferences.edit();
			
			if(StringUtils.isEmpty(key))
				return false;
			
			editor.putString(key, value);
			//提交数据进行存储
			editor.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * 多个数据进行存储
	 * @param keys  传入的所有的键
	 * @param values  传入的所有需要存储的值，和键对应
	 * @author chenh
	 */
	public static boolean savaDatatoShare(String[] keys,String []values ){
		SharedPreferences.Editor editor = sharedpreferences.edit();
		
		if(StringUtils.isEmpty(keys))
			return false;
			
		try {
			
			for(int i = 0;i<keys.length;i++){
				String key = keys[i];
				String value = values[i];
				
				if(!StringUtils.isEmpty(key))
					editor.putString(key, value);
			}
			editor.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 删除单个信息
	 * @return true 成功
	 *         false 失败
	 */
	public static boolean removeDatafromShare(String key){
		try {
			SharedPreferences.Editor editor = sharedpreferences.edit();
			if(StringUtils.isEmpty(key))
				return false;
			
			editor.remove(key);
			/**
			 * 提交数据进行存储
			 */
			editor.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 删除多个信息
	 * @param keys
	 * @return true 成功     
	 *         false 失败
	 */
	public static boolean removeDatafromShare(String[] keys){

		SharedPreferences.Editor editor = sharedpreferences.edit();
		
		if(!StringUtils.isEmpty(keys))
			return false;
			
		try {
			
			for(int i = 0;i<keys.length;i++){
				String key = keys[i];
				if(!StringUtils.isEmpty(key))
					editor.remove(key);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 根据传入的Key值获取相应用户信息
	 * @author chenh
	 */
	public static String[] getDatafromShare(String keys[]){
		int length = keys.length;
		String values[] = new String[length];

		if(StringUtils.isEmpty(keys))
			return null;
		
		for(int i =0;i<length;i++){
			String key = keys[i];
			if(!StringUtils.isEmpty(key)){
				/**
				 * 键值不为空
				 */
				values[i] = sharedpreferences.getString(key, "");
			}
		}
		return values;
	}
	
	/**
	 * 根据某个键获取相应的值
	 */
	public static String getDatafromShare(String key){
		String value = sharedpreferences.getString(key,"");
		return value;
	}
	
	
}
