package com.alfred.framework.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;

/**
 * Created by Alfred on 2017/12/11.
 */

public class Loading_Outside_Progress extends AlertDialog {
    private Context mContext;
    private static Loading_Outside_Progress progress;
    private Loading_Outside_Progress(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    public static Loading_Outside_Progress getInstance(@NonNull Context context, @StyleRes int themeResId){
        synchronized (Loading_Outside_Progress.class){
            if(progress == null){
                progress = new Loading_Outside_Progress(context,themeResId);
            }
            return progress;
        }
    }

    private void init(){
        Loading_Outside_View view = new Loading_Outside_View(mContext);
        addContentView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
