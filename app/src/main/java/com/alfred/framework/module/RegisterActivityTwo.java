package com.alfred.framework.module;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.base.config.AppConfig;
import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.model.Login_Data;
import com.alfred.framework.module.model.home.MainActivity;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.GsonUtils;
import com.alfred.framework.utils.SharePreferenceUtils;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.RequestBody;


/**
 * Created by asus on 2018/2/25.
 */

public class RegisterActivityTwo extends BaseActivity {


    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    // private static final int PHOTO_REQUEST_PICK_PHOTO = 4;//获取图片成功
    // private final String PHOTO_NAME = "my_user.jpg";

    private Boolean userNameFlag = false;
    File image = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.register_photo)
    ImageView registerPhoto;
    @BindView(R.id.register_username)
    EditText registerUsername;
    @BindView(R.id.register_complete)
    Button registerComplete;


    @Override
    public void reload() {

    }

    @Override
    public void ShowNetWort(boolean isshow) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_two);
    }

    @Override
    public void findContentView() {

    }


    @Override
    public void disposeProcess() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("pagerLimit", 3);
        map.put("pagerOffset", 0);
        RequestBody requestBody = OkHttpManager.appendTokenPOST(OkHttpManager.JSON, map);
        OkHttpManager.httpPost("http://47.92.08.125/home/topicList", requestBody, new OkHttpManager.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d("Homepage获取的数据", result);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("Homepage获取到的数据", e.toString());
            }
        });
        Bitmap bitmap = BitmapFactory.decodeStream(RegisterActivityTwo.this.getResources().openRawResource(R.drawable.userdefalut));
        registerPhoto.setImageBitmap(toRoundBitmap(bitmap));
        registerPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        /**
         * 判断EditText输入是否为空
         * 为EditText设置TextChangedLinstner*/
        registerUsername.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;//监听前的文本
            private int editStart;//光标开始位置
            private int editEnd;//光标结束位置
            // private final int charMaxNum = 20;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //输入文本之前的状态
                temp = s;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //输入文字中的状态，count是一次性输入字符数
            }
            @Override
            public void afterTextChanged(Editable s) {
                /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
                editStart = registerUsername.getSelectionStart();
                editEnd = registerUsername.getSelectionEnd();
                if(editEnd == 0 && temp.length() == 0) {
                    userNameFlag = false;
                   registerComplete.setBackgroundResource(R.drawable.btn_selector_gray);
                    registerComplete.setEnabled(false);
                } else {
                    userNameFlag = true;
                    if(userNameFlag) {
                        registerComplete.setBackgroundResource(R.drawable.btn_selector);
                        registerComplete.setEnabled(true);
                    }
                }

            }
        });

        //完成注册
        registerComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("avatar", "");
                map.put("name", registerUsername.getText().toString());
                final RequestBody requestBody = OkHttpManager.appendTokenPOST(OkHttpManager.JSON, map);
                OkHttpManager.httpPost("http://47.92.48.125/login/registerStepTwo", requestBody, new OkHttpManager.OnResultListener(){
                    @Override
                    public void onSuccess(String result) {
                        Log.d("registertwo获取的数据", result);
                        Type type = new TypeToken<BaseResponse<Login_Data>>() {
                        }.getType();
                        BaseResponse<Login_Data> response = (BaseResponse<Login_Data>) GsonUtils.fromJson(result, type);
                        Message tempMsg = handler.obtainMessage();
                        tempMsg.what = 0;
                        Bundle bundle = new Bundle();
                        bundle.putString("register_two_Message", response.getMessage());
                        if(response.getData() != null) {
                            bundle.putBoolean("register_two_FirstLogin", response.getData().getFirstLogin());
                            bundle.putString("register_two_Token", response.getData().getToken());
                        }
                        tempMsg.setData(bundle);
                        handler.sendMessage(tempMsg);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("registertwo获取数据失败", "");
                    }
                });
            }
        });

    }

    //handler使用方式
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (msg.getData().getString("register_two_Message").equals("SUCCESS")) {
                        SharePreferenceUtils.init(RegisterActivityTwo.this);
                        SharePreferenceUtils.saveDatatoShare(AppConfig.TOKEN, msg.getData().getString("register_two_Token"));
                        Intent intent = new Intent(RegisterActivityTwo.this, MainActivity.class);
                        startActivity(intent);
                    }
                    if (!msg.getData().getString("register_two_Message").equals("SUCCESS")) {
                        Toast.makeText(RegisterActivityTwo.this, msg.getData().getString("register_two_Message"), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    //提示对话框方法
    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("头像设置")
                .setPositiveButton("拍照", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        // 调用系统的拍照功能
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 指定调用相机拍照后照片的储存路径
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
                        startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
                    }
                })
                .setNegativeButton("相册", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                    }
                }).show();
    }

    //根据方式进行裁剪
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //打开相机
            case PHOTO_REQUEST_TAKEPHOTO: {
                if (resultCode == RESULT_OK) {

                    //启动裁剪activity
                    Log.v("启动剪裁程序", "是的");
                    startPhotoZoom(Uri.fromFile(image), 150);
                }
                break;
            }

            //打开相册
            case PHOTO_REQUEST_GALLERY: {

                if (resultCode == RESULT_OK) {
                    //启动剪裁程序
                    Log.v("启动剪裁程序", "是的");
                    startPhotoZoom(data.getData(), 150);
                }
                break;
            }

            //裁剪图片完成后更新UI
            case PHOTO_REQUEST_CUT: {
                if (data != null)
                    setPicToView(data);
                break;
            }


        }
    }

    //开始裁剪图片
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        //intent.putExtra("scale", true);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    //将进行剪裁后的图片显示到UI界面上
    private void setPicToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            registerPhoto.setImageBitmap(toRoundBitmap(photo));
        }
    }

    //将图片变为圆形
    private Bitmap toRoundBitmap(Bitmap map){



        int height=150; //位图的高度(px)
        int width = 150;//位图的宽度（px）

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

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("注册", View.VISIBLE);
    }



}
