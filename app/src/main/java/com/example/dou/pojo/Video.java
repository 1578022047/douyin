package com.example.dou.pojo;


public class Video {

  private long videoId;
  private String userId;
  private long likeNum;
  private long remarkNum;
  private String describe;


  public long getVideoId() {
    return videoId;
  }

  public void setVideoId(long videoId) {
    this.videoId = videoId;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public long getLikeNum() {
    return likeNum;
  }

  public void setLikeNum(long likeNum) {
    this.likeNum = likeNum;
  }


  public long getRemarkNum() {
    return remarkNum;
  }

  public void setRemarkNum(long remarkNum) {
    this.remarkNum = remarkNum;
  }


  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }

}
