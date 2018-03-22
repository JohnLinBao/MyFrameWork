package com.alfred.framework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.alfred.framework.myframework.R;

/**
 * Created by asus on 2018/3/15.
 */

public class RefreshRecyclerView extends RecyclerView {
    private Context context;
    private float startY;
    private View headerView;
    private View footerView;
    private int maxHeight_header;
    private int maxHeight_footer;
    private double rate = 0.3;
    private boolean headerFlag;
    private boolean footerFlag;
    private boolean refreshHeaderFlag;
    private boolean loadFooterFlag;
    private OnRefreshLisitener onRefreshLisitener;
    private OnLoadingLisitener onLoadingLisitener;
    public RefreshRecyclerView(Context context) {
        this(context,null);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void initHeaderView(View headerView){
        this.headerView = headerView;
        this.headerView.measure(0,0);
        maxHeight_header = this.headerView.getMeasuredHeight()-10;
        this.headerView.setPadding(0,-maxHeight_header,0,0);

    }

    public void initFooterView(View footerView){
        this.footerView = footerView;
        this.footerView.measure(0,0);
        maxHeight_footer = this.footerView.getMeasuredHeight() - 10;
        this.footerView.setPadding(0,0, 0,  -maxHeight_footer);
    }
        
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //手指滑动的距离
                double raleDis = (startY - e.getY()) * rate;
                //位移
                float offset = computeVerticalScrollOffset();
                //屏幕高度
                float extent = computeVerticalScrollExtent();
                //数据实际高度
                float range = computeVerticalScrollRange();
                if(raleDis<0){
                    if (offset==0 && !refreshHeaderFlag) {
                        headerView.setPadding(0, (int) Math.abs(raleDis) - maxHeight_header, 0, 0);
                        if ((int) Math.abs(raleDis) >= maxHeight_header) {
                            headerFlag = true;
                        } else {
                            headerFlag = false;
                        }
                    }
                }else{
                    if ((offset + extent) >= (range-1) && !loadFooterFlag){
                        footerView.setPadding(0,  0, 0,(int) Math.abs(raleDis) - maxHeight_footer);
                        if ((int) Math.abs(raleDis) >= maxHeight_footer) {
                            footerFlag = true;
                        } else {
                            footerFlag = false;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (headerFlag) {
                    headerFlag = false;
                    refreshHeaderFlag = true;
                    AnimationDrawable anim =  (AnimationDrawable)headerView.findViewById(R.id.item_dynamic_header).getBackground();
                    anim.start();
                    headerView.setPadding(0, 0, 0, 0);
                    onRefreshLisitener.OnRefreshing();
                }
                else completeRefresh();
                if (footerFlag){
                    footerFlag = false;
                    loadFooterFlag = true;
                    footerView.setPadding(0, 0, 0, 0);
                    onLoadingLisitener.OnLoading();
                }else completeLoad();

                break;
        }
        return super.onTouchEvent(e);
    }

    public void setOnRefreshLisitener(OnRefreshLisitener lisitener) {
        onRefreshLisitener = lisitener;
    }

    public interface OnRefreshLisitener {
        public void OnRefreshing();
    }

    public void setOnLoadingListener (OnLoadingLisitener listener) {
        onLoadingLisitener = listener;
    }

    public interface OnLoadingLisitener {
        public void OnLoading();
    }
    public void completeRefresh(){
        headerView.setPadding(0, -maxHeight_header, 0, 0);
        refreshHeaderFlag = false;

    }
    public void completeLoad(){
        footerView.setPadding(0, 0, 0, -maxHeight_footer);
        loadFooterFlag = false;
    }
}
