package com.example.dou.pojo;

import java.io.Serializable;

public class Flag implements Serializable {
    boolean likeFlag;
    boolean attentionFlag;

    public Flag(boolean likeFlag, boolean attentionFlag) {
        this.likeFlag = likeFlag;
        this.attentionFlag = attentionFlag;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "likeFlag=" + likeFlag +
                ", attentionFlag=" + attentionFlag +
                '}';
    }

    public boolean isLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(boolean likeFlag) {
        this.likeFlag = likeFlag;
    }

    public boolean isAttentionFlag() {
        return attentionFlag;
    }

    public void setAttentionFlag(boolean attentionFlag) {
        this.attentionFlag = attentionFlag;
    }
}
