package com.tdin360.zjw.marathon.utils;

/**
 * Created by admin on 17/8/13.
 */

public class MessageEvent {


    public enum MessageType{

        HOTEL_STATS_UPDATE,
        DEFAULT,
        DOWNLOAD_UPDATE


    }
    private String message;
    private MessageType type;

    private long totalSize;
    private long currentSize;


 public MessageEvent(MessageType type){

     this.type=type;
 }

    /**
     * 带有一个参数的信息
     * @param message
     * @param type
     */

    public MessageEvent(String message, MessageType type) {
        this.message = message;
        this.type = type;
    }

    /**
     * 用于下载更新
     * @param currentSize
     * @param totalSize
     * @param type
     */
    public MessageEvent(long currentSize,long totalSize,MessageType type){

        this.currentSize=currentSize;
        this.totalSize=totalSize;
        this.type=type;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getType() {
        return type;
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public long getTotalSize() {
        return totalSize;
    }
}
