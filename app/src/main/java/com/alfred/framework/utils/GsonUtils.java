package com.alfred.framework.utils;



import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
	/**
	 * @Title: toJson
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param bean
	 * @return String 返回类型
	 * @throws：
	 */
	public static String toJson(Object bean) {
		Gson gson = new GsonBuilder().registerTypeAdapter(java.util.Date.class,new UtilDateSerializer())
				.setDateFormat("yyyyMMddhhmmss").create();
		return gson.toJson(bean);
	}
	
	public static String toJson1(Object bean) {
		Gson gson = new Gson();
		return gson.toJson(bean);
	}

	public static String toJson(Object bean, Type type) {
		Gson gson = new GsonBuilder().registerTypeAdapter(java.util.Date.class,new UtilDateSerializer())
				.setDateFormat("yyyyMMddhhmmss").create();
		return gson.toJson(bean, (Type) type);
	}

	/**
	 * @Title: fromJson
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param <T>
	 * @param json
	 * @param type
	 * @return T 返回类型
	 * @throws：
	 */
	public static Object fromJson(String json,Type type) {
		Gson gson = new GsonBuilder().registerTypeAdapter(java.util.Date.class,new UtilDateDeserializer())
				.setDateFormat("yyyyMMddhhmmss").create();
		return gson.fromJson(json, type); 
	}

	/**
	 * @Title: fromJson
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param <T>
	 * @param json
	 * @param classOfT
	 * @return T 返回类型
	 * @throws：
	 */
	public static <T> T fromJson(String json, Class<T> classOfT) {
		Gson gson = new GsonBuilder().registerTypeAdapter(java.util.Date.class,new UtilDateDeserializer())
				.setDateFormat("yyyyMMddhhmmss").create();
		return gson.fromJson(json, classOfT);
	}
	
}
