package com.alfred.framework.module.model.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfred.framework.base.BaseActivity;
import com.alfred.framework.myframework.R;

/**
 * Created by asus on 2018/3/4.
 */

public class BuilderlinkFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_builderlink, container , false);

    }
}
