package com.alfred.framework.module.model;

import com.alfred.framework.utils.StringUtils;

import java.util.List;

/**
 * Created by asus on 2018/3/17.
 */

public class User_Bean {
    public int id;
    public String uniqueId;
    public String name;
    public String avatar;
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
    public List<Education> education;
    public class Education {

        public String schoolName;
        public String  major;
        public int degree;
        public String degreeName;
        public String startTime;
        public String endTime;

    }
    public List<Project> project;
    public class Project {

        public int id;
        public String name;
        public String company;
        public int role;
        public String roleName;
        public String location;
        public String type;
        public String area;
        public String floorOverground;
        public String floorUnderground;
        public String startTime;
        public String endTime;
        public String introduction;
        public List<String> pictures;

    }
    public List<WorkExperience> workExperience;
    public class WorkExperience {

        public int id;
        public String company;
        public String companyLogo;
        public String position;
        public int industry;
        public String industryName;
        public int profession;
        public String professionName;
        public String startTime;
        public String endTime;
        public String introduction;

    }
    public int topicCount;
    public int recruitmentCount;
    public int relatedTopicCount;
    public String shareUrl;
    public boolean friend;
    public int friendCount;

}
