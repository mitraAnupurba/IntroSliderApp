package com.example.introsliderapp.Model;

import java.util.Date;

public class Student {

    String userNameStudent;
    String passwordStudent;
    String emailAddressStudent;
    String dobStudent;
    Long phoneNumberStudent;
    String instituteName;
    String statusStudent;
    String examPreparingFor;
    boolean parentAccount;


    public Student() {

    }

    public Student(String userNameStudent, String passwordStudent, String emailAddressStudent, String dobStudent, Long phoneNumberStudent, String instituteName, String statusStudent, String examPreparingFor, boolean parentAccount) {
        this.userNameStudent = userNameStudent;
        this.passwordStudent = passwordStudent;
        this.emailAddressStudent = emailAddressStudent;
        this.dobStudent = dobStudent;
        this.phoneNumberStudent = phoneNumberStudent;
        this.instituteName = instituteName;
        this.statusStudent = statusStudent;
        this.examPreparingFor = examPreparingFor;
        this.parentAccount = parentAccount;
    }

    public String getUserNameStudent() {
        return userNameStudent;
    }

    public String getPasswordStudent() {
        return passwordStudent;
    }

    public String getEmailAddressStudent() {
        return emailAddressStudent;
    }

    public String getDobStudent() {
        return dobStudent;
    }



    public Long getPhoneNumberStudent() {
        return phoneNumberStudent;
    }


    public String getInstituteName() {
        return instituteName;
    }


    public String getStatusStudent() {
        return statusStudent;
    }


    public String getExamPreparingFor() {
        return examPreparingFor;
    }


    public boolean isParentAccount() {
        return parentAccount;
    }



}
