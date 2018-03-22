package com.alfred.framework.module;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.RequestBody;

/**
 * Created by asus on 2018/2/28.
 */

public class LoginActivity extends BaseActivity {

    //密码是否可见flag
    public Boolean isVisibleFlag = false;
    //三个EditText输入是否为空flag
    public Boolean phoneNumberFlag = false;
    public Boolean passwordFlag = false;

    @BindView(R.id.login_phonenumber)
    EditText loginPhonenumber;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_password_isvisible)
    ImageView loginPasswordIsvisible;
    @BindView(R.id.login_forgot_password)
    TextView loginForgotPassword;
    @BindView(R.id.login_complete)
    Button loginComplete;
    @BindView(R.id.login_useragreement)
    TextView loginUseragreement;

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
        setContentView(R.layout.activity_login);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {

        /*密码是否可见*/
        loginPasswordIsvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVisibleFlag = !isVisibleFlag;
                if (isVisibleFlag) {
                    loginPasswordIsvisible.setImageResource(R.drawable.yanjing_dianliang);
                    loginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    loginPasswordIsvisible.setImageResource(R.drawable.biyan);
                    loginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        /**
         * 判断三个EditText输入是否为空
         * 分别为三个EditText设置TextChangedLinstner*/
        loginPhonenumber.addTextChangedListener(new TextWatcher() {
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
                //mTvAvailableCharNum.setText("还能输入" + (charMaxNum - s.length()) + "字符");
            }

            @Override
            public void afterTextChanged(Editable s) {
                /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
                editStart = loginPhonenumber.getSelectionStart();
                editEnd = loginPhonenumber.getSelectionEnd();
                if (editEnd == 0 && temp.length() == 0) {
                    phoneNumberFlag = false;
                    loginComplete.setBackgroundResource(R.drawable.btn_selector_gray);
                    loginComplete.setEnabled(false);
                } else {
                    phoneNumberFlag = true;
                    if (phoneNumberFlag & passwordFlag) {
                        loginComplete.setBackgroundResource(R.drawable.btn_selector);
                        loginComplete.setEnabled(true);
                    }
                }

            }
        });


        loginPassword.addTextChangedListener(new TextWatcher() {
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
                //mTvAvailableCharNum.setText("还能输入" + (charMaxNum - s.length()) + "字符");
            }

            @Override
            public void afterTextChanged(Editable s) {
                /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
                editStart = loginPassword.getSelectionStart();
                editEnd = loginPassword.getSelectionEnd();
                if (editEnd == 0 && temp.length() == 0) {
                    passwordFlag = false;
                    loginComplete.setBackgroundResource(R.drawable.btn_selector_gray);
                    loginComplete.setEnabled(false);
                } else {
                    passwordFlag = true;
                    if (phoneNumberFlag & passwordFlag) {
                        loginComplete.setBackgroundResource(R.drawable.btn_selector);
                        loginComplete.setEnabled(true);
                    }
                }

            }
        });

        /*登录，输入手机号与密码登录*/
        loginComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginPhonenumber.length() == 11 & loginPassword.length() >= 6 & loginPassword.length() <= 20) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("phoneNumber", loginPhonenumber.getText().toString());
                    map.put("password", loginPassword.getText().toString());
                    final RequestBody requestBody = OkHttpManager.appendPOST(OkHttpManager.JSON, map);
                    OkHttpManager.httpPost("http://47.92.48.125/login/login", requestBody, new OkHttpManager.OnResultListener() {
                        @Override
                        public void onSuccess(String result) {
                           Log.d("Login获取的数据", result);
                            Type type = new TypeToken<BaseResponse<Login_Data>>() {
                            }.getType();
                            BaseResponse<Login_Data> response = (BaseResponse<Login_Data>) GsonUtils.fromJson(result, type);
                            Message tempMsg = handler.obtainMessage();
                            tempMsg.what = 0;
                            Bundle bundle = new Bundle();
                            bundle.putString("login_Message", response.getMessage());
                            if(response.getData() != null) {
                                bundle.putBoolean("login_FirstLogin", response.getData().getFirstLogin());
                                bundle.putString("login_Token", response.getData().getToken());

                            }
                            tempMsg.setData(bundle);
                            handler.sendMessage(tempMsg);
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.e("login失败的数据", "失败");
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号/密码的位数！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //忘记密码
        loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, EditPasswordActivity.class);
                startActivity(intent);
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
                    if (msg.getData().getString("login_Message").equals("SUCCESS")) {
                        SharePreferenceUtils.init(LoginActivity.this);
                        SharePreferenceUtils.saveDatatoShare(AppConfig.TOKEN, msg.getData().getString("login_Token"));
                        //第一次登陆跳转到注册第二步
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    if (msg.getData().getString("login_Message").equals("用户名或密码错误")) {
                        Toast.makeText(LoginActivity.this, msg.getData().getString("login_Message"), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };


    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("登录", View.VISIBLE);
        barProxy.getToolbar().setNavigationIcon(R.drawable.guanbi_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //toolbar设置菜单方式
        barProxy.getToolbar().inflateMenu(R.menu.activity_main_register);
        //设置菜单及其点击监听
        barProxy.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_register:
                        Intent intent = new Intent(LoginActivity.this, RegisterActivityOne.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }


}
