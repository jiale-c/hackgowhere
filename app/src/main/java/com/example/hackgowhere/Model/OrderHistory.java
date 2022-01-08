package com.example.hackgowhere.Model;

public class OrderHistory {

    String eventName, date, publisherId;

    public OrderHistory() {
    }

    public OrderHistory(String e, String d, String p) {
        this.eventName = e;
        this.date = d;
        this.publisherId = p;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDate() {
        return date;
    }

    public String getPublisherId() {
        return publisherId;
    }

}