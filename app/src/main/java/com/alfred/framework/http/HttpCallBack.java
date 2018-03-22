package com.alfred.framework.http;

/**
 * Created by Alfred on 2017/12/5.
 */

public interface HttpCallBack<T> {
    public void success(T t);
    public void failed(String str);
}
