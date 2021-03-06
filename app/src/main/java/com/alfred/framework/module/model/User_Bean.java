package com.alfred.framework.module.model;

import com.alfred.framework.utils.StringUtils;

import java.util.List;

/**
 * Created by asus on 2018/3/17.
 */

public class User_Bean{
    public int id;
    public String uniqueId;
    public String name;
    public String avatar;

    public User_Bean() {

    }

    public int gender;
    public String birthday;
    public String hometown;
    public String phoneNumber;
    public String email;
    public int workYears;
    public String workYearsName;
    public String city;
    public String company;
    public String companyShortName;
    public String companyLogo;
    public String position;
    public int title;
    public String titleName;
    public String register;
    public int industry;
    public String industryName;
    public int profession;
    public String professionName;
    public String brief;
    public List<Education_Bean> education;

    public List<Project_Bean> project;

    public List<WorkExperience_Bean> workExperience;


    public int topicCount;
    public int recruitmentCount;
    public int relatedTopicCount;
    public String shareUrl;
    public boolean friend;
    public int friendCount;

}
