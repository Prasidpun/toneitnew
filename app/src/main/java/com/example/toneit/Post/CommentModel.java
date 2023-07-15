package com.example.toneit.Post;

public class CommentModel {
    String profile;
    String CommentText;
    String postedBy;
    long commentAt;
    String commentedBy;
    CommentModel(){

    }


    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }

    public CommentModel(String profile, String commentText) {
        this.profile = profile;
        CommentText = commentText;
    }

    public CommentModel(String profile, String commentText, String postedBy) {
        this.profile = profile;
        CommentText = commentText;
        this.postedBy = postedBy;

    }

    public long getCommentAt() {
        return commentAt;
    }

    public void setCommentAt(long commentAt) {
        this.commentAt = commentAt;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCommentText() {
        return CommentText;
    }

    public void setCommentText(String commentText) {
        CommentText = commentText;
    }
}
