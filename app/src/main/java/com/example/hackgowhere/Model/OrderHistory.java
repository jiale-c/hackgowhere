package com.example.hackgowhere.Model;

public class OrderHistory {
    //
//    String refNumber, status, userId, date, postTitle, publisherId, type, postId;
//    int postPrice;
    String eventName, date, publisherId;

    public OrderHistory() {
    }

    public OrderHistory(String e, String d, String p) {
        this.eventName = e;
        this.date = d;
        this.publisherId = p;
    }

//    public OrderHistory(String refNumber, int postPrice, String status, String postTitle, String userId, String publisherId, String date, String type, String postId) {
//        this.refNumber = refNumber;
//        this.postPrice = postPrice;
//        this.status = status;
//        this.userId = userId;
//        this.postTitle = postTitle;
//        this.publisherId = publisherId;
//        this.date = date;
//        this.type = type;
//        this.postId = postId;
//    }

    public String getEventName() {
        return eventName;
    }

    public String getDate() {
        return date;
    }

    //    public String getRefNumber() {
//        return refNumber;
//    }
//
//    public int getPostPrice() {
//        return postPrice;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public String getPostTitle() {return postTitle; }
//
//    public String getUserId() {
//        return userId;
//    }
//
    public String getPublisherId() {
        return publisherId;
    }
//
//    public String getType() { return type; }
//
//    public String getDate() {
//        return date;
//    }
//
//    public String getPostId() {
//        return postId;
//    }
}