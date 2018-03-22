package com.alfred.framework.base.config;

import com.alfred.framework.myframework.BuildConfig;

/**
 * Http网络请求相关的基本参数配置
 */
public class HttpConfig {
    private static String BASE_URL = "";//初始
    static {
        BASE_URL = BuildConfig.SERVER_URL;
    }

    public static String ADVERTISMENT = BASE_URL + "www.baidu.com";

    public static String LOGIN = BASE_URL + "/login/login";

    public static String MAINTAIN = BASE_URL + "www.baidu.com";

    public static String REGISTER = BASE_URL + "www.baidu.com";
}
