package com.edu.ebus.ebus.data;

public class UserAccount {

    private String id;
    private String fbId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phone;
    private String profileImage;

    public String getFbId() {
        return fbId;
    }
    public void setFbId(String fbId){
        this.fbId = fbId;
    }
    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getName(){
        return firstName + " " + lastName;
    }

}
