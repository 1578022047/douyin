package com.example.dou.pojo;


public class User {

  private String userId;
  private String name;
  private String phonenum;
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

    public User() {
    }

    public User(String userId, String name, String  phonenum, String password, String brief, String birthday, Integer sex) {
        this.userId = userId;
        this.name = name;
        this.phonenum = phonenum;
        this.password = password;
        this.brief = brief;
        this.birthday = birthday;
        this.sex = sex;
    }

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


  public String getPhonenum() {
    return phonenum;
  }

  public void setPhonenum(String phonenum) {
    this.phonenum = phonenum;
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
