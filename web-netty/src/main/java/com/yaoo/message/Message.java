package com.yaoo.message;

public class Message {
    public static final byte HEART = 0;

    public static final byte MSG = 1;

    //消息类型
    byte type;
    //消息长度
    int length;
    //消息内容
    String content;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
