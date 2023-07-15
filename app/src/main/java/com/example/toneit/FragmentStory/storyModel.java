package com.example.toneit.FragmentStory;

public class storyModel {
    int profileImage;
    String userName;

    public storyModel(int profileImage, String userName) {
        this.profileImage = profileImage;
        this.userName = userName;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
