package com.zhowin.basicframework.common.model;

/**
 * Created by: Z_B on 2018/8/26.
 * Function: EventBus的消息模版
 */
public class EventNotice {

    private int noticeType;
    private Object noticeOne;
    private Object noticeTwo;
    private Object noticeThree;

    public EventNotice(int noticeType) {
        this.noticeType = noticeType;
    }

    public EventNotice(int noticeType, Object noticeOne) {
        this.noticeType = noticeType;
        this.noticeOne = noticeOne;
    }

    public EventNotice(int noticeType, Object noticeOne, Object noticeTwo) {
        this.noticeType = noticeType;
        this.noticeOne = noticeOne;
        this.noticeTwo = noticeTwo;
    }

    public EventNotice(int noticeType, Object noticeOne, Object noticeTwo, Object noticeThree) {
        this.noticeType = noticeType;
        this.noticeOne = noticeOne;
        this.noticeTwo = noticeTwo;
        this.noticeThree = noticeThree;
    }

    public int getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
    }

    public Object getNoticeOne() {
        return noticeOne;
    }

    public void setNoticeOne(Object noticeOne) {
        this.noticeOne = noticeOne;
    }

    public Object getNoticeTwo() {
        return noticeTwo;
    }

    public void setNoticeTwo(Object noticeTwo) {
        this.noticeTwo = noticeTwo;
    }

    public Object getNoticeThree() {
        return noticeThree;
    }

    public void setNoticeThree(Object noticeThree) {
        this.noticeThree = noticeThree;
    }
}
