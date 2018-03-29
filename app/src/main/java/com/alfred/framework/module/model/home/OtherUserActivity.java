package com.alfred.framework.module.model.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.LoginActivity;
import com.alfred.framework.module.RegisterActivityOne;
import com.alfred.framework.module.model.User_Bean;
import com.alfred.framework.module.model.WorkExperience_Bean;
import com.alfred.framework.module.model.user_edit.viewbinder.WorkExperienceViewBinder;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.GsonUtils;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.RequestBody;

/**
 * Created by asus on 2018/3/17.
 */

public class OtherUserActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.otheruser_detail_head)
    ImageView otheruserDetailHead;
    @BindView(R.id.otheruser_detail_name)
    TextView otheruserDetailName;
    @BindView(R.id.otheruser_detail_sex)
    ImageView otheruserDetailSex;
    @BindView(R.id.otheruser_detail_company_position)
    TextView otheruserDetailCompanyPosition;
    @BindView(R.id.otheruser_detail_work_introduction)
    TextView otheruserDetailWorkIntroduction;
    @BindView(R.id.otheruser_detail_phoneimage)
    ImageView otheruserDetailPhoneimage;
    @BindView(R.id.otheruser_detail_phone)
    TextView otheruserDetailPhone;
    @BindView(R.id.otheruser_detail_emailimage)
    ImageView otheruserDetailEmailimage;
    @BindView(R.id.otheruser_detail_email)
    TextView otheruserDetailEmail;
    @BindView(R.id.otheruser_detail_personal_signature)
    TextView otheruserDetailPersonalSignature;
    @BindView(R.id.otheruser_detail_companylogo)
    ImageView otheruserDetailCompanylogo;
    @BindView(R.id.otheruser_detail_released_dynamic)
    TextView otheruserDetailReleasedDynamic;
    @BindView(R.id.otheruser_detail_released_position)
    TextView otheruserDetailReleasedPosition;
    @BindView(R.id.otheruser_detail_workexperience)
    TextView otheruserDetailWorkexperience;
    @BindView(R.id.otheruser_detail_recyclerview)
    RecyclerView otheruserDetailRecyclerview;
    @BindView(R.id.otheruser_detail_addfriend)
    TextView otheruserDetailAddfriend;
    private int userId;
    private String userName;
    private User_Bean user_bean;
    private MultiTypeAdapter workExperienceAdapter;
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
        setContentView(R.layout.activity_otheruser_detail);

    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {
        userId = getIntent().getIntExtra("userId", 0);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("userId", userId);
        RequestBody requestBody = OkHttpManager.appendTokenPOST(OkHttpManager.JSON, map);
        OkHttpManager.httpPost("http://47.92.48.125/user/info", requestBody, new OkHttpManager.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d("itemDynamicHead获取的数据", result);
                Type type = new TypeToken<BaseResponse<User_Bean>>() {
                }.getType();
                BaseResponse<User_Bean> response = (BaseResponse<User_Bean>) GsonUtils.fromJson(result, type);
                user_bean = response.getData();
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("itemDynamicHead获取的数据", e.toString());
            }
        });
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //头像
                    Glide.with(OtherUserActivity.this).load(user_bean.avatar).into(otheruserDetailHead);
                    //姓名
                    otheruserDetailName.setText(user_bean.name);
                    //性别1代表男，2代表女
                    if(user_bean.gender == 1)
                        otheruserDetailSex.setImageResource(R.drawable.xingbie_nan);
                    else if(user_bean.gender == 2)
                        otheruserDetailSex.setImageResource(R.drawable.xingbie_nv);
                    //职位
                    otheruserDetailCompanyPosition.setText(user_bean.city + "  /  " + user_bean.companyShortName + "  /  " + user_bean.position);
                    //行业细分
                    otheruserDetailWorkIntroduction.setText(user_bean.industryName + "  |  " + user_bean.titleName + "  |  " + user_bean.professionName);
                    //电话、邮箱
                    if (user_bean.friend) {
                        otheruserDetailPhone.setText(user_bean.phoneNumber);
                        otheruserDetailEmail.setText(user_bean.email);
                    }
                    else {
                        otheruserDetailPhone.setText("加为好友后可见");
                        otheruserDetailEmail.setText("加为好友后可见");
                    }
                    //一句话简介
                    otheruserDetailPersonalSignature.setText(user_bean.brief);
                    //公司logo
                    if (!user_bean.companyLogo.equals(""))
                        Glide.with(OtherUserActivity.this).load(user_bean.companyLogo).into(otheruserDetailCompanylogo);
                    else
                        otheruserDetailCompanylogo.setImageResource(R.drawable.morengongsilogo);
                    //发布动态个数
                    otheruserDetailReleasedDynamic.setText("发布的动态（" + user_bean.relatedTopicCount + ")");
                    //发布职位个数
                    otheruserDetailReleasedPosition.setText("发布的职位（" + user_bean.recruitmentCount + ")");
                    //工作经历
                    workExperienceAdapter = new MultiTypeAdapter();
                    workExperienceAdapter.register(WorkExperience_Bean.class, new WorkExperienceViewBinder());
                    otheruserDetailRecyclerview.setAdapter(workExperienceAdapter);
                    otheruserDetailRecyclerview.setLayoutManager(new LinearLayoutManager(OtherUserActivity.this));
                    workExperienceAdapter.setItems(user_bean.workExperience);
                    workExperienceAdapter.notifyDataSetChanged();
                    //加好友或和TA聊聊
                    if (user_bean.friend)
                        otheruserDetailAddfriend.setText("和TA聊聊");
                    else
                        otheruserDetailAddfriend.setText("加好友");
                    break;
            }

        }
    };
    @Override
    public void hanldeToolbar() {
        userName = getIntent().getStringExtra("userNmae");
        barProxy.setTitle(userName, View.VISIBLE);
        //设置返回菜单
        barProxy.getToolbar().setNavigationIcon(R.drawable.back_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //toolbar设置菜单方式
        barProxy.getToolbar().inflateMenu(R.menu.activity_menu_share);
        //设置菜单及其点击监听
        barProxy.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain"); // 分享发送的数据类型
                        intent.putExtra(Intent.EXTRA_TEXT, user_bean.shareUrl); // 分享的内容
                        startActivity(Intent.createChooser(intent, ""));// 目标应用选择对话框的标题;
                        break;
                }
                return true;
            }
        });
    }


}
