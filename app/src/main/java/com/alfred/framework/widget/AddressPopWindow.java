package com.alfred.framework.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alfred.framework.module.model.Province_Bean;
import com.alfred.framework.module.model.user_edit.viewbinder.CityViewBinder;
import com.alfred.framework.module.model.user_edit.viewbinder.ProvinceViewBinder;
import com.alfred.framework.myframework.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by asus on 2018/3/27.
 */

public class AddressPopWindow extends PopupWindow {

    @BindView(R.id.address_cancel)
    TextView addressCancel;
    @BindView(R.id.address_sure)
    TextView addressSure;
    @BindView(R.id.address_province)
    RecyclerView addressProvince;
    @BindView(R.id.address_city)
    RecyclerView addressCity;
    @BindView(R.id.address_linearlayout)
    LinearLayout addressLinearlayout;
    private View conentView;
    private Activity context;

    private OnSureClickListener listener;

    public void setOnSureClickListener(OnSureClickListener listener) {
        this.listener = listener;
    }
    private MultiTypeAdapter provinceAdapter = new MultiTypeAdapter();
    private ProvinceViewBinder provinceBinder = new ProvinceViewBinder();
    private MultiTypeAdapter cityAdapter = new MultiTypeAdapter();
    private CityViewBinder cityBinder = new CityViewBinder();
    private String city="";
    private List<Province_Bean> province_bean = new ArrayList<Province_Bean>();
    public AddressPopWindow(final Activity context, List<Province_Bean> province_bean) {
        super(context);
        this.context = context;
        this.province_bean = province_bean;
        this.initPopupWindow();
    }

    private void initPopupWindow() {
        //获取引入的布局
        conentView = LayoutInflater.from(context).inflate(R.layout.layout_popwindow_address, null);
        this.setContentView(conentView);
        ButterKnife.bind(this, conentView);
        //获取popupwindow的高度与宽度并设置
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.setWidth(metrics.widthPixels);
        this.setHeight(metrics.heightPixels / 3);
        // 设置SelectPicPopupWindow弹出窗体可点
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setTouchable(true);
        // 刷新状态
        this.update();
        //设置背景颜色
        this.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.white)));
        //设置recyclerview
        provinceAdapter.register(Province_Bean.class, provinceBinder);
        addressProvince.setAdapter(provinceAdapter);
        addressProvince.setLayoutManager(new LinearLayoutManager(context));
        provinceAdapter.setItems(province_bean);
        provinceAdapter.notifyDataSetChanged();
        provinceBinder.setOnChildClickListener(new ProvinceViewBinder.OnChildClickListener() {
            @Override
            public void OnChildClick(View v, int position, Province_Bean data) {
                cityAdapter.register(Province_Bean.City.class, cityBinder);
                addressCity.setAdapter(cityAdapter);
                addressCity.setLayoutManager(new LinearLayoutManager(context));
                cityAdapter.setItems(data.city);
                cityAdapter.notifyDataSetChanged();
                cityBinder.setOnChildClickListener(new CityViewBinder.OnChildClickListener() {
                    @Override
                    public void OnChildClick(View v, int position, Province_Bean.City data) {
                        city = data.name;
                    }
                });
            }
        });
        //设置取消点击事件
        addressCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //设置确定点击事件
        addressSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnSureClick(city);
                dismiss();
            }
        });
    }

    @Override
    public boolean isShowing() {
        return super.isShowing();
    }

    /**
     * 显示popupWindow的方式设置，当然可以有别的方式。
     *一会会列出其他方法
     * @param parent
     */

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.BOTTOM,0,0);
        } else {
            this.dismiss();
        }
    }
    public interface OnSureClickListener{
        void OnSureClick(String city);
    }
}
