package com.edu.ebus.ebus;

import android.net.Uri;

public class Ticket {

    private String Name;
    private String Target;
    private String DateofBooking;
    private String price;
    private String imageURI;

    private Ticket()
    {

    }

    public Ticket(String name, String target, String dateofBooking, String price, String imageURI) {
        Name = name;
        Target = target;
        DateofBooking = dateofBooking;
        this.price = price;
        this.imageURI = imageURI;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
    }

    public String getDateofBooking() {
        return DateofBooking;
    }

    public void setDateofBooking(String dateofBooking) {
        DateofBooking = dateofBooking;
    }
    public void setPrice(String prices){
        price = prices;
    }
    public String getPrice() {
        return price;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
