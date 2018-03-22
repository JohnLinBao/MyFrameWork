package com.alfred.framework.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.AppManager;
import com.alfred.framework.utils.NetWoekUtils;
import com.alfred.framework.utils.StatusBarUtils;
import com.alfred.framework.utils.ToastUtils;
import com.alfred.framework.utils.ToolBarProxy;
import com.alfred.framework.widget.Loading_Inside_View;
import com.alfred.framework.widget.Loading_Outside_Progress;

import butterknife.ButterKnife;

import static com.alfred.framework.myframework.R.id.toolbar;


/**
 * 所有UI Activity 的基类 所有的活动Activity都继承该类 在该类中，做所有ui的基础处理和公用功能
 * 
 * @author chenhui
 * 
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,Loading_Inside_View.OnReLoadListener {
	/**
	 * 网络监控提示
	 */
	public abstract void ShowNetWort(boolean isshow);

	NetBroadcastReceiver netReceiver; // 网络监听广播

	public ToolBarProxy barProxy;

	public Loading_Inside_View loading_Inside;

	public static boolean isLogin;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//StatusBarUtils.setStatusBarColor_KITKAT(this);
		AppManager.getAppManager().addActivity(this);
		initContentView(savedInstanceState);
		ButterKnife.bind(this);
		initToolbar();
		initPrompt();
		findContentView();
		disposeProcess();
		initialize();

	}

	/**
	 * 初始化界面内部显示提示（包括加载图标、没网络提示、失败提示）
	 */
	private void initPrompt() {
//		loading_Inside = (Loading_Inside_View) findViewById(R.id.loading_Inside);
//		if (loading_Inside != null) {
//			loading_Inside.setOnReLoadListener(this);
//		}
	}
	/**
	 * 设置界面内部显示提示（包括加载图标、没网络提示、失败提示）
	 */
	public void setPrompt(int status){
		if (loading_Inside != null) {
			loading_Inside.setStatus(status);
		}
	}

	/**
	 * 初始化处理
	 */
	public abstract void initialize();
	/**
	 * 绑定布局
	 */
	public abstract void initContentView(Bundle savedInstanceState);

	/**
	 * 找到布局中的控件
	 */
	public abstract void findContentView();

	/**
	 * 实现过程
	 */
	public abstract void  disposeProcess();

	/**
	 * 隐藏ActionBar标题栏
	 */
	public void hideActionBar(boolean isHide) {
		ActionBar actionBar = getSupportActionBar();
		if (actionBar == null) {
			Log.i("info", "可能手机版本太低，导致无法显示ActionBar");
			return;
		}
		if (isHide) {
			actionBar.hide();
		} else {
			actionBar.show();
		}
	}

	/**
	 * 初始化Toolbar标题栏
	 */
	protected void initToolbar(){
		hideActionBar(true);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
//			setSupportActionBar(toolbar);
//			getSupportActionBar().setDisplayShowTitleEnabled(false);
			barProxy = new ToolBarProxy(toolbar);
			hanldeToolbar();
		}
	}


	/**
	 * toolbar设置
	 */
	public abstract void hanldeToolbar();

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		netReceiver = new NetBroadcastReceiver();
		registerReceiver(netReceiver, mFilter);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unregisterReceiver(netReceiver);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/**
	 * 启动新的Activity
	 */
	public void OpenActivity(Class<?> clz) {
		OpenActivity(clz, null);
	}

	public void OpenActivity(Class<?> clz, Bundle data) {
		Intent intent = new Intent();
		intent.setClass(this, clz);
		if (data != null) {
			intent.putExtras(data);
		}
		startActivity(intent);
	}

	public void OpenActivityforResult(Class<?> clz, String requestCode) {
		OpenActivityforResult(clz, null, requestCode, null);
	}

	public void OpenActivityforResult(Class<?> clz, Bundle data, String requestCode, String ActivityName) {
		Intent intent = new Intent();
		intent.setClass(this, clz);
		if (data != null) {
			intent.putExtras(data);
		}
		intent.setAction(ActivityName);
		startActivityForResult(intent, Integer.parseInt(requestCode));
	}

	/**
	 * 消息提示
	 */
	public void showMessage(String text) {
		ToastUtils.showToast(this,text);
	}

	/**
	 * 消息提示带图片
	 */
	public void showMessage(String text,int res) {
		ToastUtils.showToastWithImg(this,text,res);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}

	/**
	 * 显示登陆对话框
	 */
	public void showLoginDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("提示").setMessage("您还未登陆，无法使用该功能，是否现在登陆？").setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//OpenActivity(LoginActivity.class);
			}
		}).setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.create().show();
	}

	/**
	 * 注销系统操作
	 */
	public void QuitSystem() {
		ProgressDialog quitDialog = new ProgressDialog(this);
		onDestroy();
		quitDialog.show();
		quitDialog.dismiss();
	}

	/**
	 * 显示退出对话框
	 */
	public void showQuitDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("提示").setMessage("您真的要离开我吗？").setPositiveButton("再坐会儿", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				QuitSystem();
			}
		}).setNegativeButton("残忍拒绝", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog.create().show();
	}

	/**
	 * 显示悬浮在界面外的提示框（只含加载）
	 */
	public void showProgressDialog() {
		Loading_Outside_Progress.getInstance(this,R.style.dialog_untran).show();
	}

	/**
	 * 隐藏悬浮在界面外的提示框（只含加载）
	 */
	public void stopProgressDialog() {
		Loading_Outside_Progress.getInstance(this,R.style.dialog_untran).dismiss();
	}

	public void onViewClicked(View v){}

	@Override
	public void onClick(View v) {
		onViewClicked(v);
	}

	/**
	 * 定义网络监听广播
	 * 
	 * @author chenh
	 */
	public class NetBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			/**
			 * true：有网 false:无网
			 */
			boolean isNet = NetWoekUtils.isNetWorks(context);
			ShowNetWort(!isNet);
		}
	}
}
