package com.alfred.framework.module.model.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.alfred.framework.base.config.AppConfig;
import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.model.User_Bean;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.GsonUtils;
import com.alfred.framework.utils.StringUtils;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.RequestBody;

/**
 * Created by asus on 2018/3/4.
 */

public class UserFragment extends Fragment {


    @BindView(R.id.user_head)
    ImageView userHead;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_sex)
    ImageView userSex;
    @BindView(R.id.user_companyposition)
    TextView userCompanyposition;
    @BindView(R.id.user_edit_arrow)
    ImageView userEditArrow;
    @BindView(R.id.user_edit_relativelayout)
    RelativeLayout userEditRelativelayout;
    @BindView(R.id.user_myfriend_count)
    TextView userMyfriendCount;
    @BindView(R.id.user_myfriend)
    TableRow userMyfriend;
    @BindView(R.id.user_invitefriend)
    TableRow userInvitefriend;
    @BindView(R.id.user_releasedynamic_count)
    TextView userReleasedynamicCount;
    @BindView(R.id.user_releasedynamic)
    TableRow userReleasedynamic;
    @BindView(R.id.user_joindynamic_count)
    TextView userJoindynamicCount;
    @BindView(R.id.user_joindynamic)
    TableRow userJoindynamic;
    @BindView(R.id.user_recruitmanage)
    TableRow userRecruitmanage;
    @BindView(R.id.user_jobmanage)
    TableRow userJobmanage;
    @BindView(R.id.user_systemsetup)
    TableRow userSystemsetup;

    String city = "";
    String company = "";
    String positin = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, v);
        RequestBody requestBody = OkHttpManager.appendTokenPOST(OkHttpManager.JSON, null);
        OkHttpManager.httpPost("http://47.92.48.125/user/info", requestBody, new OkHttpManager.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<BaseResponse<User_Bean>>(){}.getType();
                BaseResponse<User_Bean> response = (BaseResponse<User_Bean>) GsonUtils.fromJson(result, type);
                AppConfig.user = response.getData();
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        return v;
    }

    public Handler handler =  new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    /*用户名*/
                    userName.setText(AppConfig.user.name);
                    /*用户头像*/
                    Glide.with(UserFragment.this).load(AppConfig.user.avatar).into(userHead);
                    /*用户性别*/
                    if (AppConfig.user.gender == 1){
                        userSex.setImageResource(R.drawable.xingbie_nan);
                    }else if(AppConfig.user.gender == 2){
                        userSex.setImageResource(R.drawable.xingbie_nv);
                    }
                    /*用户公司，职位等*/
                    if (AppConfig.user.city != null)
                        city = AppConfig.user.city + "  ";
                    if (AppConfig.user.company != null)
                        company = AppConfig.user.company + "  ";
                    if (AppConfig.user.position != null)
                        positin = AppConfig.user.position + "  ";
                    userCompanyposition.setText(city + company + positin);
                    /*编辑用户资料*/
                    userEditRelativelayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), UserEditActivity.class);
                            startActivity(intent);
                        }
                    });
                    /*朋友个数*/
                    userMyfriendCount.setText(AppConfig.user.friendCount + "");
                    /*发布动态个数*/
                    userReleasedynamicCount.setText(AppConfig.user.topicCount + "");
                    /*参与动态个数*/
                    userJoindynamicCount.setText(AppConfig.user.relatedTopicCount + "");
                    /*系统设置*/
                    userSystemsetup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), SetupActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };


}
