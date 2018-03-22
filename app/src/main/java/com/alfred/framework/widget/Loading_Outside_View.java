package com.alfred.framework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alfred on 2017/12/6.
 */

public class Loading_Outside_View extends View {
    /**
     * 默认半径
     */
    public final int defualtRadius = 200;
    /**
     * 线条宽度
     */
    public final int lineWidth = 20;
    private Paint fiPaint = new Paint();
    /**
     * 最大弧度
     */
    private final float arcDeg = 300;
    /**
     * 每次偏移角度
     */
    private final float moveSpeed = 5;
    /**
     *圈头和圈尾的偏移角度比
     */
    private final double moveRate = 0.4;
    /**
     * 偏移角度和
     */
    private float riseDeg = 0;
    /**
     * 起始角度
     */
    private float startDeg = -145;
    /**
     * 画弧坐标范围
     */
    private RectF rectF = new RectF();
    /**
     * 弧长递增 true  弧长递减 false
     */
    private boolean rollfont = true;
    public Loading_Outside_View(Context context) {
        this(context,null);
    }

    public Loading_Outside_View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public Loading_Outside_View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int WHMeasureSpec = 0;
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heighMeasuretSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        if(widthMeasureMode == MeasureSpec.EXACTLY&&heightMeasureMode == MeasureSpec.EXACTLY){
            if(widthMeasureSize >heighMeasuretSize){
                WHMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasureSize,MeasureSpec.EXACTLY);
            }else{
                WHMeasureSpec = MeasureSpec.makeMeasureSpec(heighMeasuretSize,MeasureSpec.EXACTLY);
            }
        }else if(widthMeasureMode == MeasureSpec.EXACTLY&&heightMeasureMode != MeasureSpec.EXACTLY){
            WHMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasureSize,MeasureSpec.EXACTLY);
        }else if(widthMeasureMode != MeasureSpec.EXACTLY&&heightMeasureMode == MeasureSpec.EXACTLY){
            WHMeasureSpec = MeasureSpec.makeMeasureSpec(heighMeasuretSize,MeasureSpec.EXACTLY);
        }else{
            WHMeasureSpec = MeasureSpec.makeMeasureSpec(defualtRadius,MeasureSpec.EXACTLY);
        }
        setMeasuredDimension(WHMeasureSpec,WHMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        rectF.set(lineWidth,lineWidth,width-lineWidth,height-lineWidth);
        fiPaint.setColor(Color.BLUE);
        fiPaint.setAntiAlias(true);
        fiPaint.setStrokeWidth(lineWidth);
        fiPaint.setStrokeCap(Paint.Cap.ROUND);
        fiPaint.setStyle(Paint.Style.STROKE);
        if(rollfont) {
            if (riseDeg <= arcDeg) {
                riseDeg += moveSpeed;
                startDeg += moveSpeed * moveRate;
            } else {
                riseDeg = arcDeg;
                rollfont = false;
            }
        }else {
            if (riseDeg >= moveSpeed) {
                riseDeg -= moveSpeed;
                startDeg += moveSpeed * moveRate + moveSpeed;
            } else {
                riseDeg = 0;
                rollfont = true;
            }
        }
        canvas.drawArc(rectF,startDeg,riseDeg,false,fiPaint);
        invalidate();
    }
}
