package com.example.dou.pojo;


import java.io.Serializable;

public class User implements Serializable {

  private String userId;
  private String name;
  private String phoneNum;
  private String password;
  private String brief;
  private String birthday;
  private String imageUrl;

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(final String imageUrl) {
    this.imageUrl = imageUrl;
  }

  private Integer sex;

  @Override
  public String toString() {
    return "User{" +
            "userId='" + userId + '\'' +
            ", name='" + name + '\'' +
            ", phoneNum='" + phoneNum + '\'' +
            ", password='" + password + '\'' +
            ", brief='" + brief + '\'' +
            ", birthday='" + birthday + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", sex=" + sex +
            '}';
  }

  public User(final String userId, final String name, final String phoneNum, final String password, final String brief, final String birthday, final String imageUrl, final Integer sex) {
    this.userId = userId;
    this.name = name;
    this.phoneNum = phoneNum;
    this.password = password;
    this.brief = brief;
    this.birthday = birthday;
    this.imageUrl = imageUrl;
    this.sex = sex;
  }

  public User(){};

    public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }




  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getBrief() {
    return brief;
  }

  public void setBrief(String brief) {
    this.brief = brief;
  }


  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }


  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

}
