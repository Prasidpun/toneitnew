package com.example.toneit.message;

public class UserModel {
    private String id;
    private String userName;
    private String imageUrl;

   public UserModel(){

    }

    public UserModel(String id, String userName, String imageUrl) {
        this.id = id;
        this.userName = userName;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
