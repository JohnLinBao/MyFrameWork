package com.alfred.framework.module.model;

/**
 * Created by asus on 2018/2/28.
 */

public class Login_Data {

    public class User {
        public int id;
        public int adminStatus;
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
        public int topicCount;
        public int recruitmentCount;
        public boolean hasResume;
        public boolean hasCompany;
        public int companyId;
    }



    private User user;
    private Boolean firstLogin;
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Boolean getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
