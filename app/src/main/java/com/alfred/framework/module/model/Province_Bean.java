package com.alfred.framework.module.model;

import java.util.List;

import cn.qqtheme.framework.picker.AddressPicker;

/**
 * Created by asus on 2018/3/27.
 */


public class Province_Bean {

    public Province_Bean() {

    }
    public String name;
    public List<City> city;

    public class City {

        public City() {

        }
        public String name;
        public List<String> area;
    }
}

