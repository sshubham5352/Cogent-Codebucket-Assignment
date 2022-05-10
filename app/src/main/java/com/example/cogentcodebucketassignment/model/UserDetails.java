package com.example.cogentcodebucketassignment.model;

//DATA CLASS
public class UserDetails {
    //DATA MEMBERS
    String name, email, documentId;

    public UserDetails() {

    }

    public UserDetails(String name, String email, String documentId) {
        this.name = name;
        this.email = email;
        this.documentId = documentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumentId() {
        return documentId;
    }
}
