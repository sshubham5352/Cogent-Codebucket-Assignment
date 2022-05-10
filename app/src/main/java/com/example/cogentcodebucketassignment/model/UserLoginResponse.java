package com.example.cogentcodebucketassignment.model;

//DATA CLASS
public class UserLoginResponse {
    //DATA MEMBERS
    boolean userExitsInDb, isPasswordCorrect;
    UserDetails userDetails;

    public UserLoginResponse(boolean userExitsInDb) {
        this.userExitsInDb = userExitsInDb;
    }

    public UserLoginResponse(boolean userExitsInDb, boolean isPasswordCorrect) {
        this.userExitsInDb = userExitsInDb;
        this.isPasswordCorrect = isPasswordCorrect;
    }

    public UserLoginResponse(boolean doesUserExistsInDb, boolean isPasswordCorrect, UserDetails userDetails) {
        this.userExitsInDb = doesUserExistsInDb;
        this.isPasswordCorrect = isPasswordCorrect;
        this.userDetails = userDetails;
    }

    public boolean doesUserExistsInDb() {
        return userExitsInDb;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public boolean isPasswordCorrect() {
        return isPasswordCorrect;
    }
}
