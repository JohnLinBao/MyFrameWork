package com.alfred.framework.module.model;

/**
 * Created by asus on 2018/3/17.
 */

public class Recruitment_Bean {
    public int id;
    public int onlineStatus;
    public String positionName;
    public int professionCategory;
    public String professionCategoryName;
    public int profession;
    public String professionName;
    public int positionType;
    public String positionTypeName;
    public int workYears;
    public String workYearsName;
    public int degree;
    public String degreeName;
    public int title;
    public String titleName;
    public String register;
    public int salaryType;
    public String salaryTypeName;
    public int salaryMin;
    public int salaryMax;
    public String location;
    public String address;
    public String benefit;
    public String introduction;
    public User user;
    public class User {
        public int userId;
        public String name;
        public String avatar;
        public String position;
    }
    public Company company;
    public class Company{
        public int id;
        public String name;
        public String shortName;
        public String logo;
        public int size;
        public String sizeName;
        public int type;
        public String typeName;
        public int industry;
        public String industryName;

    }
    public boolean deliveryStatus;
    public int resumeCount;
    public int newResumeCount;
    public String shareUrl;
}
