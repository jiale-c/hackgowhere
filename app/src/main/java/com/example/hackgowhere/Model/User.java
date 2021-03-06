package com.example.hackgowhere.Model;

public class User {
    public String status, email, username, password, profile_picture, id, search, imageurl;
    public int balance, points, isCompany;

    public User() {}

    public User(String email, String username, String password, String id, String status, String imageurl, int dummy){
        this.email = email;
        this.username = username;
        this.imageurl = imageurl;
        this.password= password;
        this.balance = 0;
        this.profile_picture = "https://t4.ftcdn.net/jpg/00/64/67/63/360_F_64676383_LdbmhiNM6Ypzb3FM4PPuFP9rHe7ri8Ju.jpg";
        this.id = id;
        this.status = status;
        this.search = username.toLowerCase();
        this.points = 0;
        this.isCompany = dummy;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public int getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getStatus() {
        return status;
    }

    public String getSearch() {
        return search;
    }
}
