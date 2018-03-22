package com.alfred.framework.module.model.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfred.framework.http.BaseResponse;
import com.alfred.framework.http.OkHttpManager;
import com.alfred.framework.module.model.Dynamic_Bean;
import com.alfred.framework.module.model.home.viewbinder.DynamicFooterViewBinder;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.GsonUtils;
import com.alfred.framework.widget.RefreshRecyclerView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.RequestBody;


/**
 * Created by asus on 2018/3/4.
 */

public class HomepageFragment extends Fragment implements DynamicViewBinder.OnChildClickListener {

    @BindView(R.id.recyclerview)
    RefreshRecyclerView recyclerview;
    private Dynamic_Bean dynamicBean;
    private Dynamic_Bean dynamicBeanMore;
    private MultiTypeAdapter adapter;
    private Items items;
    private int rate = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("pagerLimit", 5);
        map.put("pagerOffset", 0);
        RequestBody requestBody = OkHttpManager.appendTokenPOST(OkHttpManager.JSON, map);
        OkHttpManager.httpPost("http://47.92.48.125/home/topicList", requestBody, new OkHttpManager.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d("Homepage获取的数据", result);
                Type type = new TypeToken<BaseResponse<Dynamic_Bean>>() {
                }.getType();
                BaseResponse<Dynamic_Bean> response = (BaseResponse<Dynamic_Bean>) GsonUtils.fromJson(result, type);
                dynamicBean = response.getData();
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("Homepage获取到的数据", e.toString());
            }
        });

        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this, view);
        adapter = new MultiTypeAdapter();
        DynamicViewBinder dynamicBinder = new DynamicViewBinder();
        dynamicBinder.setOnChildClickListener(this);
        DynamicHeaderViewBinder headerBinder = new DynamicHeaderViewBinder();
        DynamicFooterViewBinder footerBinder = new DynamicFooterViewBinder();
        adapter.register(Dynamic_Bean.DynamicList.class,dynamicBinder);
        adapter.register(String.class, headerBinder);
        adapter.register(DynamicFooter.class, footerBinder);
        adapter.register(DynamicSearch.class, new DynamicSearchViewBinder());
        View headerView = inflater.inflate(R.layout.item_dynamic_header, null);
        View footerView = inflater.inflate(R.layout.item_dynamic_footer, null);
        headerBinder.setHeaderView(headerView);
        recyclerview.initHeaderView(headerView);
        footerBinder.setFooterView(footerView);
        recyclerview.initFooterView(footerView);
        recyclerview.setAdapter(adapter);
        recyclerview.setOnRefreshLisitener(new RefreshRecyclerView.OnRefreshLisitener() {
            @Override
            public void OnRefreshing() {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("pagerLimit", 5);
                map.put("pagerOffset", 0);
                final RequestBody requestBody = OkHttpManager.appendTokenPOST(OkHttpManager.JSON, map);
                OkHttpManager.httpPost("http://47.92.48.125/home/topicList", requestBody, new OkHttpManager.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("Homepage获取的数据", result);
                        Type type = new TypeToken<BaseResponse<Dynamic_Bean>>() {
                        }.getType();
                        BaseResponse<Dynamic_Bean> response = (BaseResponse<Dynamic_Bean>) GsonUtils.fromJson(result, type);
                        dynamicBean = response.getData();
                        handler.sendEmptyMessage(0);
                        rate = 1;
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Log.d("Homepage获取到的数据", e.toString());
                        recyclerview.completeRefresh();
                    }
                });
            }
        });
        recyclerview.setOnLoadingListener(new RefreshRecyclerView.OnLoadingLisitener() {
            @Override
            public void OnLoading() {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("pagerLimit", 5);
                map.put("pagerOffset", 5 * rate);
                RequestBody requestBody = OkHttpManager.appendTokenPOST(OkHttpManager.JSON, map);
                OkHttpManager.httpPost("http://47.92.48.125/home/topicList", requestBody, new OkHttpManager.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("Homepage获取的数据", result);
                        Type type = new TypeToken<BaseResponse<Dynamic_Bean>>() {
                        }.getType();
                        BaseResponse<Dynamic_Bean> response = (BaseResponse<Dynamic_Bean>) GsonUtils.fromJson(result, type);
                        dynamicBeanMore = response.getData();
                        handler.sendEmptyMessage(1);
                        rate++;
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Log.d("Homepage获取到的数据", e.toString());
                        recyclerview.completeLoad();
                    }
                });
            }
        });
        return view;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    items = new Items();
                    items.add("");
                    items.add(new DynamicSearch());
                    for (int i = 0; i < dynamicBean.list.size(); i++)
                        items.add(dynamicBean.list.get(i));
                    items.add(new DynamicFooter());
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                    recyclerview.completeRefresh();
                    break;
                case 1:
                    items.remove(items.size()-1);
                    for (int i = 0; i < dynamicBeanMore.list.size(); i++)
                        items.add(dynamicBeanMore.list.get(i));
                    items.add(new DynamicFooter());
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                    recyclerview.completeRefresh();
                    break;
            }
        }
    };


    @Override
    public void OnChildClick(View view, int postion, Dynamic_Bean.DynamicList data) {

        switch (view.getId()){
            case R.id.item_dynamic_head:
                Intent userIntent = new Intent(getActivity(), OtherUserActivity.class);
                userIntent.putExtra("userId", data.user.userId);
                userIntent.putExtra("userNmae", data.user.name);
                startActivity(userIntent);
                break;

            case R.id.item_dynamic_recruitment_linearlayout:
                Intent recruitmentIntent = new Intent(getActivity(), RecruitmentActivity.class);
                recruitmentIntent.putExtra("Id", data.recruitment.id);
                startActivity(recruitmentIntent);
                break;
            case R.id.item_dynamic_resume_linearlayout:
                Intent resumeIntent = new Intent(getActivity(), ResumeActivity.class);
                resumeIntent.putExtra("userId", data.user.userId);
                startActivity(resumeIntent);
                break;

        }
    }
}