package com.alfred.framework.utils;

import com.bumptech.glide.Glide;
import android.content.Context;
import android.widget.ImageView;

public class GlideUtil {
	//SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
	//assets资源：load("file:///android_asset/f003.gif")
	//raw资源：load("android.resource://com.frank.glide/raw/raw_1")或load("android.resource://com.frank.glide/raw/"+R.raw.raw_1)
	//drawable资源：load("android.resource://com.frank.glide/drawable/news")或load("android.resource://com.frank.glide/drawable/"+R.drawable.news)
	//ContentProvider资源：load("content://media/external/images/media/139469")
	//http资源：load("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg")
	//https资源：load("https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp")
	private static float sizeMultiplier;//原图缩放值
	public static void load(Context context, String url, int loadingImg, int errorImg, ImageView imageView) {
		Glide.with(context)
				.load(url)
				.placeholder(loadingImg)
				.error(errorImg)
				.into(imageView);
	}
}
