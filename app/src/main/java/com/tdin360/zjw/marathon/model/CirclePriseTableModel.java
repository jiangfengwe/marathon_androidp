package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2018/1/15.
 */

public class CirclePriseTableModel {
    /**
     * NickName : 用户13515
     * DynamicPictureUrl : http://www.baijar.com/content/images/thumbs/0002436.jpeg
     * messageType : messagenotification
     * HeadImg :
     * DynamicId : 180
     * CommentContent : 模仿秀
     * DynamicContent :
     */

    private String NickName;
    private String DynamicPictureUrl;
    private String messageType;
    private String HeadImg;
    private int DynamicId;
    private String CommentContent;
    private String DynamicContent;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CirclePriseTableModel(String nickName, String dynamicPictureUrl, String messageType, String headImg, int dynamicId, String commentContent, String dynamicContent,String time){
        NickName = nickName;
        DynamicPictureUrl = dynamicPictureUrl;
       this.messageType = messageType;
        HeadImg = headImg;
        DynamicId = dynamicId;
        CommentContent = commentContent;
        DynamicContent = dynamicContent;
        this.time=time;
    }
    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getDynamicPictureUrl() {
        return DynamicPictureUrl;
    }

    public void setDynamicPictureUrl(String DynamicPictureUrl) {
        this.DynamicPictureUrl = DynamicPictureUrl;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getHeadImg() {
        return HeadImg;
    }

    public void setHeadImg(String HeadImg) {
        this.HeadImg = HeadImg;
    }

    public int getDynamicId() {
        return DynamicId;
    }

    public void setDynamicId(int DynamicId) {
        this.DynamicId = DynamicId;
    }

    public String getCommentContent() {
        return CommentContent;
    }

    public void setCommentContent(String CommentContent) {
        this.CommentContent = CommentContent;
    }

    public String getDynamicContent() {
        return DynamicContent;
    }

    public void setDynamicContent(String DynamicContent) {
        this.DynamicContent = DynamicContent;
    }
}
