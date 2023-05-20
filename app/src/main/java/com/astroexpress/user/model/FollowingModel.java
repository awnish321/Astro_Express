package com.astroexpress.user.model;

public class FollowingModel {

    public int follow_image;
    public String follow_name;

    public int getFollow_image() {
        return follow_image;
    }

    public void setFollow_image(int follow_image) {
        this.follow_image = follow_image;
    }

    public String getFollow_name() {
        return follow_name;
    }

    public void setFollow_name(String follow_name) {
        this.follow_name = follow_name;
    }

    public FollowingModel(int follow_image, String follow_name) {
        this.follow_image = follow_image;
        this.follow_name = follow_name;
    }
}
