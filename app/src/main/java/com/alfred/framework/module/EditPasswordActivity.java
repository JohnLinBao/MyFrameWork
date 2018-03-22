package com.alfred.framework.module;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.base.config.AppConfig;
import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.model.Login_Data;
import com.alfred.framework.module.model.SmsCode;
import com.alfred.framework.module.model.Token;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.GsonUtils;
import com.alfred.framework.utils.SharePreferenceUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;

/**
 * Created by asus on 2018/3/3.
 */

public class EditPasswordActivity extends BaseActivity {
    @BindView(R.id.editpassword_phonenumber)
    EditText editpasswordPhonenumber;
    @BindView(R.id.editpassword_identifycode)
    EditText editpasswordIdentifycode;
    @BindView(R.id.editpassword_getcode)
    TextView editpasswordGetcode;
    @BindView(R.id.editpassword_password)
    EditText editpasswordPassword;
    @BindView(R.id.editpassword_isvisible)
    ImageView editpasswordIsvisible;
    @BindView(R.id.editpassword_complete)
    Button editpasswordComplete;

    //密码是否可见flag
    public Boolean isVisibleFlag = false;
    //三个EditText输入是否为空flag
    public Boolean phoneNumberFlag = false;
    public Boolean identifyCodeFlag = false;
    public Boolean passwordFlag = false;
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
        setContentView(R.layout.activity_editpassword);
    }

    @Override
    public void findContentView() {

    }

    //Handler使用
    int time = 60;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    editpasswordGetcode.setText("已发送" + "(" + time + ")");
                    time--;
                    break;
                case 1:
                    if (((String)msg.obj).equals("SUCCESS")) {
                        timer.start();
                        editpasswordGetcode.setEnabled(false);
                        Toast.makeText(EditPasswordActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                    }
                    else if (!((String)msg.obj).equals("SUCCESS") & ((String)msg.obj) != null) {
                        Toast.makeText(EditPasswordActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 2:
                    if(msg.getData().getString("editpassword_Message").equals("SUCCESS") ) {
                        //Toast.makeText(EditPasswordActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        SharePreferenceUtils.init(EditPasswordActivity.this);
                        SharePreferenceUtils.saveDatatoShare(AppConfig.TOKEN ,msg.getData().getString("editpassword_Token"));
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("password", editpasswordPassword.getText().toString());
                        RequestBody requestBody = OkHttpManager.appendTokenPOST(OkHttpManager.JSON, map);
                        OkHttpManager.httpPost("http://47.92.48.125/login/resetPassword", requestBody, new OkHttpManager.OnResultListener(){
                            @Override
                            public void onSuccess(String result) {
                                Log.d("editpasswordtwo获取的数据", result);
                                Type type = new TypeToken<BaseResponse<Login_Data>>(){}.getType();
                                BaseResponse<Login_Data> response = (BaseResponse<Login_Data>) GsonUtils.fromJson(result, type);
                                Message tempMsg = handler.obtainMessage();
                                tempMsg.what = 3;
                                Bundle bundle = new Bundle();
                                bundle.putString("editpasswordtwo_Message", response.getMessage());
//                                bundle.putString("editpasswordtwo_Token", response.getData().getToken());
                                tempMsg.setData(bundle);
                                handler.sendMessage(tempMsg);
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Log.e("editpasswordtwo失败的数据", "失败");
                            }
                        });
                    }
                    else if(msg.getData().getString("editpassword_Message")!= null) {
                        Toast.makeText(EditPasswordActivity.this, msg.getData().getString("editpassword_Message"), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    if(msg.getData().getString("editpasswordtwo_Message").equals("SUCCESS") ) {
                        Intent intent = new Intent(EditPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else if(msg.getData().getString("editpasswordtwo_Message") != null){
                        Toast.makeText(EditPasswordActivity.this, msg.getData().getString("editpasswordtwo_Message"), Toast.LENGTH_SHORT).show();
                    }

            }
        }
    };

    /*倒计时*/
    public CountDownTimer timer = new CountDownTimer(61000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            handler.sendEmptyMessage(0);
        }

        @Override
        public void onFinish() {
            editpasswordGetcode.setText("获取验证码");
            editpasswordGetcode.setEnabled(true);
            time = 60;

        }
    };
    @Override
    public void disposeProcess() {

          /*获取验证码*/
        editpasswordGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editpasswordPhonenumber.getText().toString().isEmpty() & editpasswordPhonenumber.getText().length() == 11) {

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("phoneNumber", editpasswordPhonenumber.getText().toString());
                    map.put("register", true);
                    RequestBody requestBody = OkHttpManager.appendPOST(OkHttpManager.JSON, map);
                    //异步请求，要耗时，可能getSmsCode_Message并未赋值成功就已经执行下一句if了
                    OkHttpManager.httpPost("http://47.92.48.125/login/getSmsCode", requestBody, new OkHttpManager.OnResultListener() {
                        @Override
                        public void onSuccess(String result) {
                            Log.d("getSmsCode获取的数据", result);
                            Type type = new TypeToken<BaseResponse<SmsCode>>(){}.getType();
                            BaseResponse<SmsCode> response = (BaseResponse<SmsCode>) GsonUtils.fromJson(result, type);
                            Message tempMsg = handler.obtainMessage();
                            tempMsg.what = 1;
                            tempMsg.obj = response.getMessage();
                            handler.sendMessage(tempMsg);
                            Log.d("getSmsCode获取的验证码", response.getMessage());
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d("getSmsCode失败的验证码", "失败");
                        }
                    });

                }
            }
        });

        /*密码是否可见*/
        editpasswordIsvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVisibleFlag = !isVisibleFlag;
                if(isVisibleFlag) {
                    editpasswordIsvisible.setImageResource(R.drawable.yanjing_dianliang);
                    editpasswordPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
                    editpasswordIsvisible.setImageResource(R.drawable.biyan);
                    editpasswordPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });


        /**
         * 判断三个EditText输入是否为空
         * 分别为三个EditText设置TextChangedLinstner*/
        editpasswordPhonenumber.addTextChangedListener(new TextWatcher() {
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
                editStart = editpasswordPhonenumber.getSelectionStart();
                editEnd = editpasswordPhonenumber.getSelectionEnd();
                if(editEnd == 0 && temp.length() == 0) {
                    phoneNumberFlag = false;
                    editpasswordComplete.setBackgroundResource(R.drawable.btn_selector_gray);
                    editpasswordComplete.setEnabled(false);
                } else {
                    phoneNumberFlag = true;
                    if(phoneNumberFlag & identifyCodeFlag & passwordFlag ) {
                        editpasswordComplete.setBackgroundResource(R.drawable.btn_selector);
                        editpasswordComplete.setEnabled(true);
                    }
                }

            }
        });

        editpasswordIdentifycode.addTextChangedListener(new TextWatcher() {
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
                editStart = editpasswordIdentifycode.getSelectionStart();
                editEnd = editpasswordIdentifycode.getSelectionEnd();
                if(editEnd == 0 && temp.length() == 0) {
                    identifyCodeFlag = false;
                    editpasswordComplete.setBackgroundResource(R.drawable.btn_selector_gray);
                    editpasswordComplete.setEnabled(false);
                } else {
                    identifyCodeFlag = true;
                    if(phoneNumberFlag & identifyCodeFlag & passwordFlag ){
                        editpasswordComplete.setBackgroundResource(R.drawable.btn_selector);
                        editpasswordComplete.setEnabled(true);
                    }
                }

            }
        });

        editpasswordPassword.addTextChangedListener(new TextWatcher() {
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
                editStart =  editpasswordPassword.getSelectionStart();
                editEnd =  editpasswordPassword.getSelectionEnd();

                if(editEnd == 0 && temp.length() == 0) {
                    passwordFlag = false;
                    editpasswordComplete.setBackgroundResource(R.drawable.btn_selector_gray);
                    editpasswordComplete.setEnabled(false);
                } else{
                    passwordFlag = true;
                    if(phoneNumberFlag & identifyCodeFlag & passwordFlag ) {
                        editpasswordComplete.setBackgroundResource(R.drawable.btn_selector);
                        editpasswordComplete.setEnabled(true);
                    }
                }

            }
        });

        //完成修改密码操作
        editpasswordComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editpasswordPhonenumber.length() == 11 & editpasswordIdentifycode.length()== 6
                        & editpasswordPassword.length() >= 6 & editpasswordPassword.length() <= 20) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("phoneNumber", editpasswordPhonenumber.getText().toString());
                    map.put("smsCode", editpasswordIdentifycode.getText().toString());
                    RequestBody requestBody = OkHttpManager.appendPOST(OkHttpManager.JSON, map);
                    OkHttpManager.httpPost("http://47.92.48.125/login/resetPasswordCheckSmsCode", requestBody, new OkHttpManager.OnResultListener() {
                        @Override
                        public void onSuccess(String result) {
                            Log.d("editpassword获取的数据", result);
                            Type type = new TypeToken<BaseResponse<Token>>(){}.getType();
                            BaseResponse<Token> response = (BaseResponse<Token>) GsonUtils.fromJson(result, type);
                            Message tempMsg = handler.obtainMessage();
                            tempMsg.what = 2;
                            Bundle bundle = new Bundle();
                            bundle.putString("editpassword_Message", response.getMessage());
                            bundle.putString("editpassword_Token", response.getData().getToken());
                            tempMsg.setData(bundle);
                            handler.sendMessage(tempMsg);
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d("editpassword失败的数据", "失败");
                        }
                    });
                }
                else {
                    Toast.makeText(EditPasswordActivity.this, "请输入正确的手机号/验证码/密码的位数！", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("修改密码", View.VISIBLE);
        barProxy.getToolbar().setNavigationIcon(R.drawable.back_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
