package com.alfred.framework.module.model;

import java.util.List;

/**
 * Created by asus on 2018/3/24.
 */

public class Profession_Bean {
    public List<ProfessionList> list;
    public class ProfessionList{
        public int id;
        public String name;
        public List<SpecialList> list;
        public class SpecialList{
            public int id;
            public String name;
        }
    }
}
