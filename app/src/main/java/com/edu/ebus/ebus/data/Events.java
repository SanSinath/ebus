package com.edu.ebus.ebus.data;

/**
 * Created by USER on 7/7/2018.
 */

public class Events {
    private String title;
    private String date;
    private String details;
    private String timestart;
    private String price;
    private String phonenumber;
    private String imageURL;
    private String location;

    public Events (){

    }

    public Events(String title, String date, String details, String timestart, String price, String phonenumber, String imageURL, String location) {
        this.title = title;
        this.date = date;
        this.details = details;
        this.timestart = timestart;
        this.price = price;
        this.phonenumber = phonenumber;
        this.imageURL = imageURL;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
