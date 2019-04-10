package com.example.introsliderapp.Model;

public class Parent {

    String userNameParent;
    String passwordParent;
    String emailAddressParent;
    String dobParent;
    Long phoneNumberParent;
    String instituteParent;
    String examPreparingFor;
    boolean childAccount;

    public Parent(String userNameParent, String passwordParent, String emailAddressParent, String dobParent, Long phoneNumberParent, String instituteParent, String examPreparingFor, boolean childAccount) {
        this.userNameParent = userNameParent;
        this.passwordParent = passwordParent;
        this.emailAddressParent = emailAddressParent;
        this.dobParent = dobParent;
        this.phoneNumberParent = phoneNumberParent;
        this.instituteParent = instituteParent;
        this.examPreparingFor = examPreparingFor;
        this.childAccount = childAccount;
    }

    public Parent() {


    }

    public String getUserNameParent() {
        return userNameParent;
    }

    public String getPasswordParent() {
        return passwordParent;
    }

    public String getEmailAddressParent() {
        return emailAddressParent;
    }

    public String getDobParent() {
        return dobParent;
    }

    public Long getPhoneNumberParent() {
        return phoneNumberParent;
    }

    public String getInstituteParent() {
        return instituteParent;
    }

    public String getExamPreparingFor() {
        return examPreparingFor;
    }

    public boolean isChildAccount() {
        return childAccount;
    }
}
