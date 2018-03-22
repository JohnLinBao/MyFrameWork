package com.alfred.framework.http;

import android.app.Application;
import android.os.Handler;
import android.text.TextUtils;

import com.alfred.framework.base.config.AppConfig;
import com.alfred.framework.utils.SharePreferenceUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpManager<T> {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpManager mInstance;

    public static Handler mHandler;

    private static OkHttpClient mOkHttpClient;

    private OkHttpManager(Application application){
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            File sdcache = application.getExternalCacheDir();
            int cacheSize = 10 * 1024 * 1024;
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .hostnameVerifier(DO_NOT_VERIFY)
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
            mOkHttpClient = builder.build();
            mHandler = new Handler();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static void initOkHttpManager(Application application) {
        synchronized (OkHttpManager.class) {
            if (mInstance == null) {
                mInstance = new OkHttpManager(application);
            }
        }
    }

    public static OkHttpManager getInstance() {
        return mInstance;
    }

    /**
     * 上传多个文件
     *
     * @param url
     * @param files
     * @param fileKeys
     * @param params
     * @param listener
     */
    public void postFiles(String url, File[] files,
                          String[] fileKeys, Map<String,String> params, final OnResultListener listener) {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(str);
                    }
                });
            }
        });
    }


    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, Map<String,String> params) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        for (String key : params.keySet()) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                    RequestBody.create(null, params.get(key)));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * POST
     *
     * @param url
     * @param body
     * @param listener
     */
    public  static void httpPost(String url, RequestBody body, final OnResultListener listener) {
        mOkHttpClient.newCall(new Request.Builder()
                .url(url)
                .post(body)
                .build())
                .enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            listener.onFailure(e);
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String str = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess(str);
                        }
                    });
                }
        });
    }

    /**
     * get
     *
     * @param url
     * @param params
     * @param listener
     */
    public void httpGet(String url, Map<String,String> params, final OnResultListener listener) {
        Request request = new Request.Builder().url(appendTokenGET(url,params)).get().build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {


                 listener.onSuccess(response.body().string());


            }
        });
    }

    public void httpGet(String url, final OnResultListener listener) {
        httpGet(url, null, listener);
    }

    public interface OnResultListener {
        void onSuccess(String result);

        void onFailure(Exception e);
    }

    public static <T> String appendTokenGET(String url,T t) {
        try {
            if (t != null) {
                url += "?";
                Field[] fields = t.getClass().getDeclaredFields();
                for (Field f : fields) {
                        url += f + "=" + f.get(t) + "&";
                }
                String token = SharePreferenceUtils.getDatafromShare(AppConfig.TOKEN);
                if (TextUtils.isEmpty(token)){
                    return url += AppConfig.TOKEN + "=" + token;
                }else{
                    return url.substring(0, url.length() - 1);
                }
            }else{
                return url;
            }
        } catch (IllegalAccessException e) {
            return url;
        }
    }

    public static <T> RequestBody appendPOST(MediaType contentType, T t){
        String content = "";
        if (t != null) {
            Map map = new HashMap<String,T>();
            map.put("data",t);
            content = new Gson().toJson(map);
        }
        return RequestBody.create(contentType,content);
    }


    public static <T> RequestBody appendTokenPOST(MediaType contentType, T t){
        String content = "";

            String token = SharePreferenceUtils.getDatafromShare(AppConfig.TOKEN);
            Map map = new HashMap<String, Object>();
            if (!TextUtils.isEmpty(token)){
                map.put(AppConfig.TOKEN ,token);
            }
            map.put("data",t);

            content = new Gson().toJson(map);

        return RequestBody.create(contentType,content);
    }
}