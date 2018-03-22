package com.alfred.framework.utils;

import android.widget.ImageView;

public class SceneAnimation {
	private ImageView mImageView;
	private int[] mFrameRess;
	private int[] mDurations;
	private int mDuration;

	private int mLastFrameNo;
	private long mBreakDelay;
	
	private boolean sign = false;
	private AnimationListener animationListener; 
	public SceneAnimation(ImageView pImageView, int[] pFrameRess,
			int[] pDurations,AnimationListener animationListener) {
		mImageView = pImageView;
		mFrameRess = pFrameRess;
		mDurations = pDurations;
		mLastFrameNo = pFrameRess.length - 1;
		this.animationListener = animationListener;
		mImageView.setBackgroundResource(mFrameRess[0]);
		//play(0);
	}

	public SceneAnimation(ImageView pImageView, int[] pFrameRess, int pDuration,AnimationListener animationListener) {
		mImageView = pImageView;
		mFrameRess = pFrameRess;
		mDuration = pDuration;
		mLastFrameNo = pFrameRess.length - 1;
		this.animationListener = animationListener;
		mImageView.setBackgroundResource(mFrameRess[0]);
		//playConstant(0);
	}

	public SceneAnimation(ImageView pImageView, int[] pFrameRess,
			int pDuration, long pBreakDelay,AnimationListener animationListener) {
		mImageView = pImageView;
		mFrameRess = pFrameRess;
		mDuration = pDuration;
		mLastFrameNo = pFrameRess.length - 1;
		mBreakDelay = pBreakDelay;
		this.animationListener = animationListener;
		mImageView.setBackgroundResource(mFrameRess[0]);
		//playConstant(0);
	}

	public void play(final int pFrameNo) {//固定延时
		mImageView.postDelayed(new Runnable() {
			public void run() {
				if(sign){
					mImageView.setBackgroundResource(mFrameRess[mLastFrameNo]);
					animationListener.IsStop(true);
					return;
				}
				mImageView.setBackgroundResource(mFrameRess[pFrameNo]);
				if (pFrameNo == mLastFrameNo){
				//	play(0);重复
					animationListener.IsStop(true);
					return; //停止
				}
				else{
					animationListener.IsStop(false);
					play(pFrameNo + 1);
				}
			}
		}, mDurations[pFrameNo]);
	}

	public void playConstant(final int pFrameNo) {//不固定延时
		mImageView.postDelayed(new Runnable() {
			public void run() {
				if(sign){
					mImageView.setBackgroundResource(mFrameRess[mLastFrameNo]);
					animationListener.IsStop(true);
					return;
				}
				mImageView.setBackgroundResource(mFrameRess[pFrameNo]);
				if (pFrameNo == mLastFrameNo){
					//playConstant(0); 重复
					animationListener.IsStop(true);
					return; //停止
				}
				else{
					animationListener.IsStop(false);
					playConstant(pFrameNo + 1);
				}
			}
		}, pFrameNo == mLastFrameNo && mBreakDelay > 0 ? mBreakDelay
				: mDuration);
	}
	
	public boolean isSign() {
		return sign;
	}

	public void setSign(boolean sign) {
		this.sign = sign;
	}

	public interface AnimationListener{
		public void IsStop(boolean sign);
	};
};
