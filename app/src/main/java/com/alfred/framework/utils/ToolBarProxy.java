package com.alfred.framework.utils;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.alfred.framework.myframework.R;

/**
 * Created by AlfredZhou on 2017/9/6.
 */

public class ToolBarProxy {
    private Toolbar mToolbar;

    public ToolBarProxy(Toolbar toolbar) {
        this.mToolbar = toolbar;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setToolBarBackground(int color){
        getToolbar().setBackgroundColor(color);
    }

    public void setTitle(String title, int visibility) {
        TextView titleTV = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        titleTV.setText(title);
        titleTV.setVisibility(visibility);
    }
}
