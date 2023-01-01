package com.example.social_media;

public class UserModel {
    private String userId;
    private String userBio;
    private String userName;
    private String userNumber;
    private String userEmail;
    private String userProfile;
    private String userCover;

    public UserModel(){

    }

    // Constructor

    public UserModel(String userId, String userBio, String userName, String userNumber, String userEmail, String userProfile, String userCover) {
        this.userId = userId;
        this.userBio = userBio;
        this.userName = userName;
        this.userNumber = userNumber;
        this.userEmail = userEmail;
        this.userProfile = userProfile;
        this.userCover = userCover;
    }


    // Getter and Setter


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

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserCover() {
        return userCover;
    }

    public void setUserCover(String userCover) {
        this.userCover = userCover;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }
}
