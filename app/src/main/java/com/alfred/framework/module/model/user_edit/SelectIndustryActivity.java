package com.alfred.framework.module.model.user_edit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.model.Common_Bean;
import com.alfred.framework.module.model.Login_Data;
import com.alfred.framework.module.model.user_edit.viewbinder.IndustryViewBinder;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.mob.tools.gui.RoundRectLayout;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.RequestBody;

/**
 * Created by asus on 2018/3/24.
 */

public class SelectIndustryActivity extends BaseActivity {

    public MultiTypeAdapter adapter;
    public List<Common_Bean.CommonList> list;
    @BindView(R.id.industry_recyclerview)
    RecyclerView industryRecyclerview;

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
        setContentView(R.layout.activity_select_industry);

    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("", "");
        final RequestBody requestBody = OkHttpManager.appendPOST(OkHttpManager.JSON, map);
        OkHttpManager.httpPost("http://47.92.48.125/dict/listDictIndustry", requestBody, new OkHttpManager.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<BaseResponse<Common_Bean>>() {
                }.getType();
                BaseResponse<Common_Bean> response = (BaseResponse<Common_Bean>) GsonUtils.fromJson(result, type);
                list = response.getData().list;
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        adapter = new MultiTypeAdapter();
        final IndustryViewBinder industryViewBinder = new IndustryViewBinder();
        industryViewBinder.setOnChildClickListener(new IndustryViewBinder.OnChildClickListener() {
            @Override
            public void OnChildClick(View v, int position, Common_Bean.CommonList data) {
                Intent intent = new Intent();
                intent.putExtra("industryName", data.name);
                intent.putExtra("industry", data.id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        adapter.register(Common_Bean.CommonList.class, industryViewBinder);
        industryRecyclerview.setAdapter(adapter);
        industryRecyclerview.setLayoutManager(new GridLayoutManager(SelectIndustryActivity.this, 3));
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    adapter.setItems(list);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void hanldeToolbar() {
        barProxy.setTitle("细分行业", View.VISIBLE);
        barProxy.getToolbar().setNavigationIcon(R.drawable.back_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
