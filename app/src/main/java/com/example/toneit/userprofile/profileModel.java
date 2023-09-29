package com.example.toneit.userprofile;

public class profileModel {
    private int followerCount;
    int userImg, userUploadedImg,profile_image;

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public profileModel(int profile_image) {
        this.profile_image = profile_image;
    }

    public int getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }

    String userName, status, time, likeCount, commentCount, shareCount;
    String coverPhoto,profile;
    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public profileModel() {

    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public int getUserImg() {
        return userImg;
    }

    public void setUserImg(int userImg) {
        this.userImg = userImg;
    }

    public int getUserUploadedImg() {
        return userUploadedImg;
    }

    public void setUserUploadedImg(int userUploadedImg) {
        this.userUploadedImg = userUploadedImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {

        System.out.println("tfgfhnvgdfgcngc...... "+time);
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public profileModel(int userImg, int userUploadedImg, String userName, String status, String time, String likeCount, String commentCount, String shareCount) {
        this.userImg = userImg;
        this.userUploadedImg = userUploadedImg;
        this.userName = userName;
        this.status = status;
        this.time = time;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
    }
}
