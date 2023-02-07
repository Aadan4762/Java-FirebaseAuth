package com.example.javafirebaseauthentication;

public class MainModel {

    //1.Variables that is contained in our database
    String name,course,email,surl;


    //4.one default Constructor for the firebase with zero argument

    MainModel(){

    }


    //3. Constructor

    public MainModel(String name, String course, String email, String surl) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.surl = surl;
    }


    //3. Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }
}

