package com.example.dou.pojo;


public class User {

  private String userId;
  private String name;
  private long phonenum;
  private String password;
  private String brief;
  private String birthday;
  private long sex;

    public User() {
    }

    public User(String userId, String name, long phonenum, String password, String brief, String birthday, long sex) {
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


  public long getPhonenum() {
    return phonenum;
  }

  public void setPhonenum(long phonenum) {
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


  public long getSex() {
    return sex;
  }

  public void setSex(long sex) {
    this.sex = sex;
  }

}
