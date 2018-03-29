package com.alfred.framework.module.model.user_edit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.model.Common_Bean;
import com.alfred.framework.module.model.Profession_Bean;
import com.alfred.framework.module.model.user_edit.viewbinder.IndustryViewBinder;
import com.alfred.framework.module.model.user_edit.viewbinder.ProfessionViewBinder;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

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

public class SelectProfessionActivity extends BaseActivity implements ProfessionViewBinder.OnChildClickListener {
    @BindView(R.id.profession_recyclerview)
    RecyclerView professionRecyclerview;
    public MultiTypeAdapter adapter;
    public List<Profession_Bean.ProfessionList> list;
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
        setContentView(R.layout.activity_select_profession);
    }

    @Override
    public void findContentView() {

    }

    @Override
    public void disposeProcess() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("", "");
        final RequestBody requestBody = OkHttpManager.appendPOST(OkHttpManager.JSON, map);
        OkHttpManager.httpPost("http://47.92.48.125/dict/listDictProfession", requestBody, new OkHttpManager.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<BaseResponse<Profession_Bean>>() {
                }.getType();
                BaseResponse<Profession_Bean> response = (BaseResponse<Profession_Bean>) GsonUtils.fromJson(result, type);
                list = response.getData().list;
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        adapter = new MultiTypeAdapter();
        ProfessionViewBinder professionViewBinder = new ProfessionViewBinder();
        professionViewBinder.setOnChildClickListener(this);
        adapter.register(Profession_Bean.ProfessionList.class, professionViewBinder);
        professionRecyclerview.setAdapter(adapter);
        professionRecyclerview.setLayoutManager(new LinearLayoutManager(SelectProfessionActivity.this));
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
        barProxy.setTitle("职能/专业", View.VISIBLE);
        barProxy.getToolbar().setNavigationIcon(R.drawable.back_icon);
        barProxy.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void OnChildClick(View v, int position, Profession_Bean.ProfessionList data) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.select_profession_one:
                intent.putExtra("professionName", data.name + "-" + data.list.get(0).name);
                intent.putExtra("profession", data.list.get(0).id);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.select_profession_two:
                intent.putExtra("professionName", data.name + "-" + data.list.get(1).name);
                intent.putExtra("profession", data.list.get(1).id);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.select_profession_three:
                intent.putExtra("professionName", data.name + "-" + data.list.get(2).name);
                intent.putExtra("profession", data.list.get(2).id);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.select_profession_four:
                intent.putExtra("professionName", data.name + "-" + data.list.get(3).name);
                intent.putExtra("profession", data.list.get(3).id);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.select_profession_five:
                intent.putExtra("professionName", data.name + "-" + data.list.get(4).name);
                intent.putExtra("profession", data.list.get(4).id);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
