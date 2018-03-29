package com.alfred.framework.module.model;

import java.util.List;

/**
 * Created by asus on 2018/3/17.
 */

public class Resume_Bean {
    public int userId;
    public String name;
    public String avatar;
    public int gender;
    public String birthday;
    public String city;
    public int workYears;
    public String workYearsName;
    public int title;
    public String titleName;
    public String register;
    public String phoneNumber;
    public String email;
    public String brief;
    public String introduction;
    public int jobStatus;
    public String jobStatusName;
    public int industry;
    public String industryName;
    public int professionCategory;
    public String professionCategoryName;
    public int profession;
    public String professionName;
    public String expectedPosition;
    public int positionType;
    public String positionTypeName;
    public int expectedSalary;
    public String expectedSalaryName;
    public String desiredCity;
    public int arrivalTime;
    public String arrivalTimeName;
    public String supplement;
    public List<Education> education;
    public class Education {
        public String schoolName;
        public String major;
        public int degree;
        public String degreeName;
        public String startTime;
        public String endTime;
    }
    public List<Project> project;
    public class Project{
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
    public List<Skill> skill;
    public class Skill{
        public String name;
        public int level;
        public String levelName;
    }

    public List<Training> training;
    public class Training {
        public String organization;
        public String course;
        public String startTime;
        public String endTime;
        public String introduction;
    }

    public List<WorkExperience> workExperience;
    public class WorkExperience{
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

        public WorkExperience(String company, String companyLogo, String position, String industryName, String professionName, String startTime, String endTime, String introduction) {
            this.company = company;
            this.companyLogo = companyLogo;
            this.position = position;
            this.industryName = industryName;
            this.professionName = professionName;
            this.startTime = startTime;
            this.endTime = endTime;
            this.introduction = introduction;
        }
    }
    public String shareUrl;
}
