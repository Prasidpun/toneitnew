package com.example.toneit;

public class Users {
    String profilePic;
    String mail;
    String password;
    String lastMessage;
    String userId;
    String userName;
    private int followerCount;

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public Users(String profilePic, String mail, String password, String lastMessage, String userId, String userName) {
        this.profilePic = profilePic;
        this.mail = mail;
        this.password = password;
        this.lastMessage = lastMessage;
        this.userId = userId;
        this.userName = userName;
    }
    public Users(){

    };

    public Users(  String userName, String mail,String password ) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;

    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
