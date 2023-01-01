package com.example.social_media;

public class PostModel {

    private String postId,userId,postText,postImage,postLikes,postComments;
    private long posttime;
    private boolean isliked;

    public PostModel(String postId, String userId, String postText, String postImage, String postLikes, String postComments, long posttime) {
        this.postId = postId;
        this.userId = userId;
        this.postText = postText;
        this.postImage = postImage;
        this.postLikes = postLikes;
        this.postComments = postComments;
        this.posttime = posttime;
    }

    public PostModel(){

    }

    public PostModel(String postId) {
        this.postId = postId;
    }

    public boolean isIsliked() {
        return isliked;
    }

    public void setIsliked(boolean isliked) {
        this.isliked = isliked;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(String postLikes) {
        this.postLikes = postLikes;
    }

    public String getPostComments() {
        return postComments;
    }

    public void setPostComments(String postComments) {
        this.postComments = postComments;
    }

    public long getPosttime() {
        return posttime;
    }

    public void setPosttime(long posttime) {
        this.posttime = posttime;
    }
}
