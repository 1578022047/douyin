package com.example.dou.pojo;


public class Video {

  private Integer videoId;
  private String userId;
  private Integer likeNum;
  private Integer remarkNum;
  private String describe;

  @Override
  public String toString() {
    return "Video{" +
            "videoId=" + videoId +
            ", userId='" + userId + '\'' +
            ", likeNum=" + likeNum +
            ", remarkNum=" + remarkNum +
            ", describe='" + describe + '\'' +
            '}';
  }

  public Video(Integer videoId, String userId, Integer likeNum, Integer remarkNum, String describe) {
    this.videoId = videoId;
    this.userId = userId;
    this.likeNum = likeNum;
    this.remarkNum = remarkNum;
    this.describe = describe;
  }

  public Integer getVideoId() {
    return videoId;
  }

  public void setVideoId(Integer videoId) {
    this.videoId = videoId;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public Integer getLikeNum() {
    return likeNum;
  }

  public void setLikeNum(Integer likeNum) {
    this.likeNum = likeNum;
  }


  public Integer getRemarkNum() {
    return remarkNum;
  }

  public void setRemarkNum(Integer remarkNum) {
    this.remarkNum = remarkNum;
  }


  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }

}
