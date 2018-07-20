package com.edu.ebus.ebus.data;

public class UserAccount {

    private String id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String profileImage;
    private int loginMethod;

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setLoginMethod(int loginMethod) {
        this.loginMethod = loginMethod;
    }

    public int getLoginMethod() {
        return loginMethod;
    }
}
