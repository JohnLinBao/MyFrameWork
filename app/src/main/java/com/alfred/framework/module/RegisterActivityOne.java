package com.alfred.framework.module;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.base.config.AppConfig;
import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
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
import okhttp3.RequestBody;

/**
 * Created by asus on 2018/2/22.
 */

public class RegisterActivityOne extends BaseActivity  {


    //密码是否可见flag
    public Boolean isVisibleFlag = false;
    //三个EditText输入是否为空flag
    public Boolean phoneNumberFlag = false;
    public Boolean identifyCodeFlag = false;
    public Boolean passwordFlag = false;


    @BindView(R.id.register_phonenumber)
    EditText registerPhonenumber;
    @BindView(R.id.register_identifycode)
    EditText registerIdentifycode;
    @BindView(R.id.register_getcode)
    TextView registerGetcode;
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.register_password_isvisible)
    ImageView registerPasswordIsvisible;
    @BindView(R.id.register_useragreement)
    TextView registerUseragreement;
    @BindView(R.id.register_next)
    TextView registerNext;

    /*OkHttp监听器——获取验证码*/
    OkHttpManager.OnResultListener getSmsCodeOnResultListener = new OkHttpManager.OnResultListener() {
        @Override
        public void onSuccess(String result) {
            Log.d("registerone获取的数据", result);
            Type type = new TypeToken<BaseResponse<SmsCode>>(){}.getType();
            BaseResponse<SmsCode> response = (BaseResponse<SmsCode>) GsonUtils.fromJson(result, type);
            Message tempMsg = handler.obtainMessage();
            tempMsg.what = 1;
            tempMsg.obj = response.getMessage();
            handler.sendMessage(tempMsg);
            Log.d("registerone获取的数据", response.getMessage());

        }

        @Override
        public void onFailure(Exception e) {
            Log.e("registerone失败的数据", "失败");
        }
    };

    /*OkHttp监听器——注册第一步*/
    OkHttpManager.OnResultListener registerOnResultListenerOne = new OkHttpManager.OnResultListener() {
        @Override
        public void onSuccess(String result) {
            Type type = new TypeToken<BaseResponse<Token>>(){}.getType();
            BaseResponse<Token> response = (BaseResponse<Token>) GsonUtils.fromJson(result, type);
            Message tempMsg = handler.obtainMessage();
            tempMsg.what = 2;
            Bundle bundle = new Bundle();
            bundle.putString("register_Message", response.getMessage());
            bundle.putString("register_Token", response.getData().getToken());
            tempMsg.setData(bundle);
            handler.sendMessage(tempMsg);
        }

        @Override
        public void onFailure(Exception e) {
            Log.e("registerone失败的数据", "失败");
        }
    };


    //Handler使用
    int time = 60;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    registerGetcode.setText("已发送" + "(" + time + ")");
                    time--;
                    break;
                case 1:
                    if (((String)msg.obj).equals("SUCCESS")) {
                        timer.start();
                        registerGetcode.setEnabled(false);
                        Toast.makeText(RegisterActivityOne.this, "获取成功", Toast.LENGTH_SHORT).show();
                    }
                    if (((String)msg.obj).equals("该账号已注册")) {
                        Toast.makeText(RegisterActivityOne.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 2:
                    if(msg.getData().getString("register_Message").equals("SUCCESS") ) {
                        Toast.makeText(RegisterActivityOne.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        SharePreferenceUtils.init(RegisterActivityOne.this);
                        SharePreferenceUtils.saveDatatoShare(AppConfig.TOKEN ,msg.getData().getString("register_Token"));
                        Intent intent = new Intent(RegisterActivityOne.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    if(msg.getData().getString("register_Message").equals("短信验证码错误或已失效")) {
                        Toast.makeText(RegisterActivityOne.this, msg.getData().getString("register_Message"), Toast.LENGTH_SHORT).show();
                    }
                    break;

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
            registerGetcode.setText("获取验证码");
            registerGetcode.setEnabled(true);
            time = 60;

        }
    };



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
        setContentView(R.layout.activity_register_one);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {

            /*获取验证码*/
            registerGetcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!registerPhonenumber.getText().toString().isEmpty() & registerPhonenumber.getText().length() == 11) {

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("phoneNumber", registerPhonenumber.getText().toString());
                        map.put("register", false);
                        RequestBody requestBody = OkHttpManager.appendPOST(OkHttpManager.JSON, map);
                        //异步请求，要耗时，可能getSmsCode_Message并未赋值成功就已经执行下一句if了
                        OkHttpManager.httpPost("http://47.92.48.125/login/getSmsCode", requestBody, getSmsCodeOnResultListener);

                }

            }
//                @Override
//                public void onClick(View v) {
//                    timer.start();
//                    registerGetcode.setEnabled(false);
//                    String url = "https://api.buildlinker.com/login/getSmsCode";
//                    OkHttpClient okHttpClient = new OkHttpClient();
//
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("phoneNumber", registerPhonenumber.getText().toString());
//                    map.put("password", "true");
//                    Map<String ,Map> requestMap = new HashMap<String , Map>();
//                    requestMap.put("data", map);
//                    Gson gson = new Gson();
//                    String strGson = gson.toJson(requestMap);
//                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), strGson);
//
//                    final Request request = new Request.Builder()
//                            .url(url)
//                            .post(body)
//                            .build();
//
//                    Call call = okHttpClient.newCall(request);
//
//                    call.enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            Log.v("失败","失败");
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            Log.v("获取的数据" , response.body().string());
//                        }
//                    });
//                }
        });

        /*密码是否可见*/
        registerPasswordIsvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVisibleFlag = !isVisibleFlag;
                if(isVisibleFlag) {
                    registerPasswordIsvisible.setImageResource(R.drawable.yanjing_dianliang);
                    registerPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
                    registerPasswordIsvisible.setImageResource(R.drawable.biyan);
                    registerPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });


        /**
         * 判断三个EditText输入是否为空
         * 分别为三个EditText设置TextChangedLinstner*/
        registerPhonenumber.addTextChangedListener(new TextWatcher() {
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
                editStart = registerPhonenumber.getSelectionStart();
                editEnd = registerPhonenumber.getSelectionEnd();
                if(editEnd == 0 && temp.length() == 0) {
                    phoneNumberFlag = false;
                    registerNext.setBackgroundResource(R.drawable.btn_selector_gray);
                    registerNext.setEnabled(false);
                } else {
                    phoneNumberFlag = true;
                    if(phoneNumberFlag & identifyCodeFlag & passwordFlag ) {
                        registerNext.setBackgroundResource(R.drawable.btn_selector);
                        registerNext.setEnabled(true);
                    }
                }

            }
        });

        registerIdentifycode.addTextChangedListener(new TextWatcher() {
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
                editStart = registerIdentifycode.getSelectionStart();
                editEnd = registerIdentifycode.getSelectionEnd();
                if(editEnd == 0 && temp.length() == 0) {
                    identifyCodeFlag = false;
                    registerNext.setBackgroundResource(R.drawable.btn_selector_gray);
                    registerNext.setEnabled(false);
                } else {
                    identifyCodeFlag = true;
                    if(phoneNumberFlag & identifyCodeFlag & passwordFlag ){
                        registerNext.setBackgroundResource(R.drawable.btn_selector);
                        registerNext.setEnabled(true);
                    }
                }

            }
        });

        registerPassword.addTextChangedListener(new TextWatcher() {
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
                editStart =  registerPassword.getSelectionStart();
                editEnd =  registerPassword.getSelectionEnd();
//            if (temp.length() > charMaxNum) {
//                Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
//                s.delete(editStart - 1, editEnd);
//                int tempSelection = editStart;
//                mEditTextMsg.setText(s);
//                mEditTextMsg.setSelection(tempSelection);
                if(editEnd == 0 && temp.length() == 0) {
                    passwordFlag = false;
                    registerNext.setBackgroundResource(R.drawable.btn_selector_gray);
                    registerNext.setEnabled(false);
                } else{
                    passwordFlag = true;
                    if(phoneNumberFlag & identifyCodeFlag & passwordFlag ) {
                        registerNext.setBackgroundResource(R.drawable.btn_selector);
                        registerNext.setEnabled(true);
                    }
                }

            }
        });

        /*注册下一步，（第一步）向后台设置用户手机号以及密码，并获取返回的token*/

        registerNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registerPhonenumber.length() == 11 & registerIdentifycode .length()== 6
                        & registerPassword.length() >= 6 & registerPassword.length() <= 20) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("phoneNumber", registerPhonenumber.getText().toString());
                    map.put("smsCode", registerIdentifycode.getText().toString());
                    map.put("password", registerPassword.getText().toString());
                    RequestBody requestBody = OkHttpManager.appendPOST(OkHttpManager.JSON, map);
                    OkHttpManager.httpPost("http://47.92.48.125/login/registerStepOne", requestBody, registerOnResultListenerOne);
                }
                else {
                    Toast.makeText(RegisterActivityOne.this, "请输入正确的手机号/验证码/密码的位数！", Toast.LENGTH_SHORT).show();

                }
            }
        });

        /*用户协议*/
        registerUseragreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivityOne.this, UserAgreementActivity.class);
                startActivity(intent);
            }
        });
    }

    //ActionBar设置菜单方式
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.activity_main_login, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_login :
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void hanldeToolbar() {

        barProxy.setTitle("注册", View.VISIBLE);
        barProxy.getToolbar().setNavigationIcon(R.drawable.guanbi_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //toolbar设置菜单方式
        barProxy.getToolbar().inflateMenu(R.menu.activity_main_login);
        //设置菜单及其点击监听
        barProxy.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_login:
                        Intent intent = new Intent(RegisterActivityOne.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

}

