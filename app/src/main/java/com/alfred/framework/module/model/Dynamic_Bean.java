package com.alfred.framework.module.model;

import java.util.List;

/**
 * Created by asus on 2018/3/8.
 */

public class Dynamic_Bean {

    public List<DynamicList> list;
    public class DynamicList {

        public Topic topic;
        public class Topic{
            public int id;
            public int type;
            public int recruitmentId;
            public String content;
            public List<String> pictures;
            public String shareUrl;
            public long createTime;
        }

        public User user;
        public class User{
            public int userId;
            public String name;
            public String avatar;
            public String city;
            public String company;
            public String companyShortName;
            public String position;
        }

        public Recruitment recruitment;
        public class  Recruitment {
            public int id;
            public int onlineStatus;
            public int firstPublish;
            public String positionName;
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
            public long createTime;

            public User user;
            public class User{
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
                public String qualification;
                public int industry;
                public String industryName;
            }

            public String  deliveryStatus;
        }

        public Resume resume;
        public class Resume{
            public int userId;
            public String name;
            public String avatar;
            public String city;
            public int workYears;
            public String workYearsName;
            public int degree;
            public String degreeName;
            public int title;
            public String titleName;
            public String register;
            public int expectedSalary;
            public String expectedSalaryName;
            public LastWorkExperience lastWorkExperience;
            public class LastWorkExperience{
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
            public int firstPublish;
        }

        public List<CommentList> commentList;
        public class CommentList{
            public int userId;
            public String name;
            public String avatar;
            public String city;
            public String company;
            public String companyShortName;
            public String  position;
            public int id;
            public String content;
            public int topicId;
            public long createTime;
            public long updateTime;

        }

        public List<ShareList> shareList;
        public class ShareList{
            public int userId;
            public String name;
            public String avatar;
            public String city;
            public String company;
            public String companyShortName;
            public String  position;
            public int id;
            public String content;
            public int topicId;
            public long createTime;
            public long updateTime;

        }

        public List<LikeList> likeList;
        public class LikeList{
            public int userId;
            public String name;
            public String avatar;
            public String city;
            public String company;
            public String companyShortName;
            public String  position;
            public int id;
            public String content;
            public int topicId;
            public long createTime;
            public long updateTime;

        }

        public Boolean userLike;
        public int commentCount;
        public int likeCount;
        public int shareCount;
    }
}
