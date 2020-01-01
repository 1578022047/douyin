package com.example.dou.pojo;


public class Remark {

  private long remarkId;
  private long videoId;
  private String content;
  private java.sql.Timestamp time;
  private long quoteId;


  public long getRemarkId() {
    return remarkId;
  }

  public void setRemarkId(long remarkId) {
    this.remarkId = remarkId;
  }


  public long getVideoId() {
    return videoId;
  }

  public void setVideoId(long videoId) {
    this.videoId = videoId;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public java.sql.Timestamp getTime() {
    return time;
  }

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }


  public long getQuoteId() {
    return quoteId;
  }

  public void setQuoteId(long quoteId) {
    this.quoteId = quoteId;
  }

}
