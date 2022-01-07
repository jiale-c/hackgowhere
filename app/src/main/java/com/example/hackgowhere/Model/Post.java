package com.example.hackgowhere.Model;

public class Post {

    private String description;
    private String title;
    private String imageurl;
    private String postid;
    private String publisher;
    private String categoryName;
    private String website, difficulty, category;
    private float rating;

    public Post() {
    }

    public Post(String title, String description, String imageurl, String categoryName, String website, String postid, String publisher, String difficulty, String category) {
        this.description = description;
        this.imageurl = imageurl;
        this.postid = postid;
        this.title = title;
        this.publisher = publisher;
        this.categoryName = categoryName;
        this.website = website;
        this.rating = 0;
        this.difficulty=difficulty;
        this.category=category;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public String getCategory() {
        return category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getWebsite() { return website; }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public float getRating() {
        return rating;
    }
}

