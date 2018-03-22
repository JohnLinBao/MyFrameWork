package com.alfred.framework.widget.recycleview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by Alfred on 2017/11/27.
 */

public class CommonSwipeLayout extends SwipeRefreshLayout{
    private OnLoadMoreListener mListener;
    private RecyclerView mTarget;
    private int mTouchSlop;
    int mActivePointerId;
    int pointerIndex;
    private float mInitialDownY;
    private boolean isStartLoadMore;
    private boolean loadMoreEnable = true;
    public interface OnLoadMoreListener {
        /**
         * Called when a swipe gesture triggers a loadMore.
         */
        void onLoadMore();
    }

    public CommonSwipeLayout(Context context) {
        this(context,null);
    }

    public CommonSwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mListener) {this.mListener = mListener;}

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return super.onInterceptTouchEvent(ev);
                }
                mInitialDownY = ev.getY(pointerIndex);
                //1.能否上拉加载 2.是否存在目标View 3.是否到达底部（只处理到达底部的消息，其它情况的消息可向下传递）
                if(loadMoreEnable&&ensureTarget()&&isScrollToBottom()){
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                final float y = ev.getY(pointerIndex);
                final float yDiff = y - mInitialDownY;
                if (yDiff<0&&Math.abs(yDiff) > mTouchSlop){
                    if(ensureTarget()&&isScrollToBottom()) {
                        isStartLoadMore = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isStartLoadMore){
                    mListener.onLoadMore();
                    isStartLoadMore = false;
                }
                break;
        }
        return true;
    }

    private boolean ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid
        // out yet.
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child instanceof RecyclerView) {
                    mTarget = (RecyclerView) child;
                    return true;
                }
            }
        }else{
            return true;
        }
        return false;
    }

    private boolean isScrollToBottom(){
        final int offset = mTarget.computeVerticalScrollOffset();
        final int range = mTarget.computeVerticalScrollRange() - mTarget.computeVerticalScrollExtent();
        return offset > 0&&offset >= range - 1;
    }

    public void setLoadMoreEnable(boolean enable){
        loadMoreEnable = enable;
    }
}
