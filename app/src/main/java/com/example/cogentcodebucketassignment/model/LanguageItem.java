package com.example.cogentcodebucketassignment.model;

//DATA CLASS
public class LanguageItem {
    //DATA MEMBERS
    String languageName, localName;

    public LanguageItem(String languageName, String localName) {
        this.languageName = languageName;
        this.localName = localName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public String getLocalName() {
        return localName;
    }
}
