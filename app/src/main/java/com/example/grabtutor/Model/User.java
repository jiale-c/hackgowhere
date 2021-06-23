package com.example.grabtutor.Model;

public class User {
    public String status, email, username, password, profile_picture, id, search;
    public Integer balance;

    public User() {}

    public User(String email, String username, String password, String id, String status){
        this.email = email;
        this.username = username;
        this.password= password;
        this.balance = 0;
        this.profile_picture = "https://t4.ftcdn.net/jpg/00/64/67/63/360_F_64676383_LdbmhiNM6Ypzb3FM4PPuFP9rHe7ri8Ju.jpg";
        this.id = id;
        this.status = status;
        this.search = username.toLowerCase();
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

    public Integer getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}