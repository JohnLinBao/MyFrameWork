package com.alfred.framework.module.model.register_base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.icu.text.IDNA;
import android.icu.text.SimpleDateFormat;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;


import com.alfred.framework.myframework.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import android.support.v7.widget.AppCompatImageView;

/**
 * Created by asus on 2018/2/25.
 */

public class UserPhoto_ImageView extends AppCompatImageView {
    private Paint p;
    private Bitmap bitmap;
    private Context context;
    private int wAndHeight[]=new int[2];
    private File file;
    private final String PHOTO_NAME = "my_user.jpg";
    public UserPhoto_ImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub

        //获得控件长宽(px)
        wAndHeight = getWidthAndHeight(context,attrs);

        this.context = context;

        //初始化控件
        init();



    }

//    public UserPhoto_ImageView(Context context) {
//        super(context);
//        // TODO Auto-generated constructor stub
//
//        //获得控件长宽（px）
//        wAndHeight=getWidthAndHeight(context, attrs);
//
//        this.context = context;
//        init();
//    }

    public UserPhoto_ImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获得控件长宽（px）
        wAndHeight=getWidthAndHeight(context,attrs);

        // TODO Auto-generated constructor stub
        this.context = context;
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, new Matrix(), p);

    }

    private void init(){
        //从手机存储区域获取图片文件（该位置为手机相册选中的图片经过剪裁后的图片的存储路径）
        file = new File(Environment.getExternalStorageDirectory(), PHOTO_NAME);

        //如果图片文件存在，则显示，否则创建并显示
        if(file.exists()){
            Log.v("文件存在", "是");
            this.bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        else{
            Log.v("文件不存在", "是");

            //生成默认图片的文件
            this.bitmap = BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.defalut));
            //person.setPicture()
            FileOutputStream fos=null;
            try {
                fos = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); //压缩
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //将方形的位图转换为圆形的位图
        this.bitmap = toRoundBitmap(this.bitmap);
        p = new Paint();

    }



    private Bitmap toRoundBitmap(Bitmap map){


        //int height = map.getHeight()+100;
        int height=convertDIP2PX(context,this.wAndHeight[1]); //位图的高度(px)
        int width = convertDIP2PX(context,this.wAndHeight[0]);//位图的宽度（px）

        //创建画布
        Bitmap bit = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bit);

        //画笔
        Paint paint = new Paint();
        paint.setAntiAlias(false);
        int r = (width>height)?height:width;



        //绘制圆形
        RectF rectF = new RectF(0,0,r,r);
        canvas.drawRoundRect(rectF, r/2, r/2, paint);

        //画头像
        //canvas.drawARGB(0, 0, 0, 0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(map, null,rectF, paint);

        //返回圆形位图
        return bit;
    }

    //使当前视图无效，从而使系统重新绘制视图
    public void myValidate(){
        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        bitmap=toRoundBitmap(bitmap);
        invalidate();

    }

    //将dp转换为px
    private static int convertDIP2PX(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dip*scale + 0.5f*(dip>=0?1:-1));
    }

    //根据xml文件中的属性，返回宽高（px）
    private static int[] getWidthAndHeight(Context context,AttributeSet attrs){
        int height,width;
        int n = attrs.getAttributeCount();
        int wAndH[] = new int[2];


        for(int i=0;i<n;i++){
            String str = attrs.getAttributeName(i);

            //获取宽度
            if(str.equals("layout_width")){
                //System.out.println(attrs.getAttributeName(0));
                String sttr = attrs.getAttributeValue(i);
                String temp = "";
                int j=0;
                while(sttr.charAt(j)>='0'&&sttr.charAt(j)<='9'){
                    temp+=sttr.charAt(j);
                    j++;
                }
                wAndH[0]=Integer.parseInt(temp);
                temp="";
                continue;
            }

            //获取长度
            if(str.equals("layout_height")){
                //System.out.println(attrs.getAttributeName(1));
                String sttr = attrs.getAttributeValue(i);
                String temp = "";
                int j=0;
                while(sttr.charAt(j)>='0'&&sttr.charAt(j)<='9'){
                    temp+=sttr.charAt(j);
                    j++;
                }
                //System.out.println("temp"+temp);
                wAndH[1]=Integer.parseInt(temp);
                temp="";
                continue;
            }
        }
        return wAndH;
    }
}
