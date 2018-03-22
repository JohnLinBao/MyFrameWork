package com.alfred.framework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.alfred.framework.myframework.R;

/**
 * Created by AlfredZhou on 2017/9/8.
 */

public class CountDownView extends View {
    public int defualtRadius = 200;
    public int width;
    public int height;
    public RectF rectf;
    public int circleWidth = 5;
    public boolean refresh;
    public int beginDegree = -90;
    public int endDegree = 360;
    public int moveDegree = 3;
    private MyOnFinishListener myOnFinishListener;
    private String content = "跳过";
    public void setMyOnFinishListener(MyOnFinishListener myOnFinishListener) {
        this.myOnFinishListener = myOnFinishListener;
    }

    public CountDownView(Context context) {
        super(context);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int measureSize = 0;
        switch (mode){
            case MeasureSpec.EXACTLY:
                measureSize = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                measureSize = defualtRadius;
                break;
        }
        int measureMeasure = MeasureSpec.makeMeasureSpec(measureSize, MeasureSpec.EXACTLY);
        setMeasuredDimension(measureMeasure,measureMeasure);
    }

    public void startRoll(){
        beginDegree = -90;
        endDegree = 360;
        refresh = true;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        rectf = new RectF(circleWidth,circleWidth,width-circleWidth,height-circleWidth);
        if(refresh) {
            Paint wordPaint = new Paint();
            wordPaint.setAntiAlias(true);
            wordPaint.setColor(getResources().getColor(R.color.black));
            wordPaint.setStyle(Paint.Style.STROKE);
            wordPaint.setTextSize(20);
            float[] charLength = new float[content.length()];
            wordPaint.getTextWidths(content,charLength);
            float sumLength = 0;
            for (float l:charLength)
                sumLength +=l;
            canvas.drawText(content, (width-sumLength) / 2, height / 2+5, wordPaint);
            Paint outerpaint = new Paint();
            outerpaint.setAntiAlias(true);
            outerpaint.setColor(getResources().getColor(R.color.theme_color));
            outerpaint.setStyle(Paint.Style.STROKE);
            outerpaint.setStrokeWidth(circleWidth);
            endDegree -= moveDegree;
            beginDegree += moveDegree;
            canvas.drawArc(rectf, beginDegree, endDegree, false, outerpaint);
            if(endDegree<=0) {
                refresh = false;
                myOnFinishListener.finish();
            }else
                invalidate();
        }
    }

    public interface MyOnFinishListener{
       public void finish();
    }
}
