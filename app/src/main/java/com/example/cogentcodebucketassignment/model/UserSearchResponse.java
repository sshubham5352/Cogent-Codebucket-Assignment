package com.example.cogentcodebucketassignment.model;

//DATA CLASS
public class UserSearchResponse {
    //DATA MEMBERS
    boolean userExitsInDb;

    public UserSearchResponse(boolean doesUserExistsInDb) {
        this.userExitsInDb = doesUserExistsInDb;
    }

    public boolean doesUserExistsInDb() {
        return userExitsInDb;
    }
}
