package com.example.toneit.Post;

public class Follow {
    private String followBy;
    private long followedAt;

   public Follow(){

    }
    public Follow(String followBy, long followedAt) {
        this.followBy = followBy;
        this.followedAt = followedAt;
    }

    public String getFollowBy(String uid) {
        return followBy;
    }

    public void setFollowBy(String followBy) {
        this.followBy = followBy;
    }

    public long getFollowedAt(long time) {
        return followedAt;
    }

    public void setFollowedAt(long followedAt) {
        this.followedAt = followedAt;
    }
}
