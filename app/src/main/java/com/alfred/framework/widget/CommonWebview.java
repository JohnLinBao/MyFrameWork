package com.alfred.framework.widget;


import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.alfred.framework.utils.NetWorkUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alfred on 2017/11/14.
 * WebViewClient拦截http与WebChromeClient拦截资源 自行重写（与业务处理相关）
 */

public class CommonWebview extends WebView implements View.OnLongClickListener{
    private Context mContext;
    private static final String APP_CACAHE_DIRNAME = "WBCACHE";
    private static final String CACHE_DIR_PATH = Environment.getExternalStorageDirectory()+APP_CACAHE_DIRNAME;

    public CommonWebview(Context context) {
        super(context);
        this.mContext = context;
        basicSetting();
    }

    public CommonWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        basicSetting();
    }

    public CommonWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        basicSetting();
    }

    private void basicSetting() {
        commonSetting();
        cacheSetting();
    }

    /**
     * 常规配置
     */
    public void commonSetting(){
        WebSettings webSettings = getSettings();

        //支持获取手势焦点，输入用户名、密码或其他
        requestFocusFromTouch();
        webSettings.setJavaScriptEnabled(true);  //支持js 存在漏洞 谨慎使用

        //This method was deprecated in API level 18.
        //webSettings.setPluginState(WebSettings.PluginState.ON); //支持插件
        //webSettings.setRenderPriority(RenderPriority.HIGH);  //提高渲染的优先级

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); //缩放至屏幕的大小

        webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
        webSettings.setTextZoom(2);//设置文本的缩放倍数，默认为 100
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webSettings.setStandardFontFamily("sans-serif");//设置 WebView 的字体，默认字体为 "sans-serif"
        webSettings.setDefaultFontSize(20);//设置 WebView 字体的大小，默认大小为 16
        webSettings.setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /**
     * 缓存设置
     */
    public void cacheSetting(){
        WebSettings webSettings = getSettings();
        if (NetWorkUtil.isInternetConnection(mContext)&& NetWorkUtil.isWifiConnection(mContext)) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }

        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        webSettings.setAppCachePath(CACHE_DIR_PATH+APP_CACAHE_DIRNAME);
    }

    /**
     * cookie同步设置
     * 注：1.访问url前完成同步 2.常规设置需要在同步前完成。不然同步无效 2.setCookie每次只能设置一对key-value。
     */
    public void syncCookieSetting(String url, List<String> cookies){
        CookieSyncManager cookieSyncManager = android.webkit.CookieSyncManager.createInstance(mContext);
        CookieManager CookieManager = android.webkit.CookieManager.getInstance();
        for (String cookie:cookies)
            CookieManager.setCookie(url, cookie);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            cookieSyncManager.sync();
        }else{
            CookieManager.flush();
        }
    }

    /**
     * 获取cookie
     * @param url
     * @return
     */
    public String getCookie(String url){
        CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        return cookieManager.getCookie(url);
    }

    /**
     * 删除过期cookie
     * 5.0以后设置的callback才有效
     * @param callback
     */
    public void deleteSessionCookie(ValueCallback<Boolean> callback){
        CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            cookieManager.removeSessionCookie();
        }else{
            cookieManager.removeSessionCookies(callback);
        }
    }

    /**
     * 删除所有cookie
     * 5.0以后设置的callback才有效
     * @param callback
     */
    public void deleteAllCookie(ValueCallback<Boolean> callback){
        CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            cookieManager.removeAllCookie();
        }else{
            cookieManager.removeAllCookies(callback);
        }
    }

    /**
     * 长按处理
     * WebView.HitTestResult.UNKNOWN_TYPE 未知类型
     * WebView.HitTestResult.PHONE_TYPE 电话类型
     * WebView.HitTestResult.EMAIL_TYPE 电子邮件类型
     * WebView.HitTestResult.GEO_TYPE 地图类型
     * WebView.HitTestResult.SRC_ANCHOR_TYPE 超链接类型
     * WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE 带有链接的图片类型
     * WebView.HitTestResult.IMAGE_TYPE 单纯的图片类型
     * WebView.HitTestResult.EDIT_TEXT_TYPE 选中的文字类型
     */
    @Override
    public boolean onLongClick(View v) {
        HitTestResult result = ((WebView)v).getHitTestResult();
        if (null == result)
            return false;
        int type = result.getType();
        if (type == WebView.HitTestResult.UNKNOWN_TYPE)
            return false;
        // 这里可以拦截很多类型，我们只处理图片类型就可以了
        switch (type) {
            case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                break;
            case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                break;
            case WebView.HitTestResult.GEO_TYPE: // 地图类型
                break;
            case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                break;
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                break;
            case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                // 获取图片的路径
                String saveImgUrl = result.getExtra();
                // 跳转图片查看界面
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void loadUrl(String url) {
        Map<String, String> header = new HashMap<String, String>();
        loadUrl(url, header);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        super.loadUrl(url, additionalHttpHeaders);
    }
    /**
     * 加载带参数的JS函数
     *
     * @param JsName JS函数名
     * @param params 不定参数
     */
    public void loadJSWithParam(String JsName, String... params) {
        String TotalParam = "";
        for (int i = 0; i < params.length; i++) {
            if (i == params.length - 1) {
                //最后一个
                TotalParam += (params[i]);
            } else {
                TotalParam += (params[i] + "','");
            }
        }
        this.loadUrl("javascript:" + JsName + "('" + TotalParam + "')");
    }

    /**
     * 加载不带参数的JS函数
     *
     * @param JsName JS函数名
     */
    public void loadJS(String JsName) {
        this.loadUrl("javascript:" + JsName + "()");
    }
}
