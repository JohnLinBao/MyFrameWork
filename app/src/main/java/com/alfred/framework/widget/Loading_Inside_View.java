package com.alfred.framework.widget;

/**
 * Created by Alfred on 2017/12/5.
 */

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alfred.framework.myframework.R;


/**
 * 自定义loadingView
 */
public class Loading_Inside_View extends LinearLayout implements View.OnClickListener {
    public static final int LOADING = 0;
    public static final int STOP_LOADING = 1;
    public static final int NO_DATA = 2;
    public static final int NO_NETWORK = 3;
    public static final int GONE = 4;

    private ProgressBar imageView;
    private RelativeLayout mRlError;
    private LinearLayout mLlLoading;
    private AnimationDrawable mAni;
    private View mView;

    private OnReLoadListener mListener;

    public void setOnReLoadListener(OnReLoadListener mListener) {
        this.mListener = mListener;
    }

    public interface OnReLoadListener {
        void reload();
    }

    public Loading_Inside_View(Context context) {
        super(context);
        init(context);
    }

    public Loading_Inside_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Loading_Inside_View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.loading_layout, this);
        imageView = (ProgressBar) mView.findViewById(R.id.iv_loading);
        mLlLoading = (LinearLayout) mView.findViewById(R.id.ll_loading);
        mRlError = (RelativeLayout) mView.findViewById(R.id.rl_error);
        TextView tvError = (TextView) mView.findViewById(R.id.tv_error);
        String exchange = getResources().getString(R.string.click_to_refresh);
        tvError.setText(Html.fromHtml(exchange));
        mRlError.setOnClickListener(this);

        setStatus(GONE);
    }

    public void setStatus(int status) {
        setVisibility(View.VISIBLE);

        try {
            if (status == LOADING) {//更新
                mRlError.setVisibility(View.GONE);
                mLlLoading.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
            } else if (status == STOP_LOADING) {
                imageView.setVisibility(View.GONE);
                setVisibility(View.GONE);
            } else if (status == NO_DATA) {//无数据情况
                imageView.setVisibility(View.GONE);
                mRlError.setVisibility(View.VISIBLE);
                mLlLoading.setVisibility(View.GONE);
            } else if (status == NO_NETWORK) {//无网络情况
                imageView.setVisibility(View.GONE);
                mRlError.setVisibility(View.VISIBLE);
                mLlLoading.setVisibility(View.GONE);
            } else {
                imageView.setVisibility(View.GONE);
                setVisibility(View.GONE);
            }
        } catch (OutOfMemoryError e) {
        }
    }

    @Override
    public void onClick(View v) {
        if(mListener!=null)
            mListener.reload();
    }
}

