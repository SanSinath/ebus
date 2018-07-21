package com.edu.ebus.ebus.data;

import android.net.Uri;

public class Ticket {

    private String Name;
    private String Source;
    private String Hour;
    private String Destination;
    private String DateofBooking;
    private String price;
    private String imageURI;
    private String idbus;
    private String phonenumber;

    private Ticket()
    {

    }

    public Ticket(String name, String source, String hour, String destination, String dateofBooking, String price, String imageURI, String idbus, String phonenumber) {
        Name = name;
        Source = source;
        Hour = hour;
        Destination = destination;
        DateofBooking = dateofBooking;
        this.price = price;
        this.imageURI = imageURI;
        this.idbus = idbus;
        this.phonenumber = phonenumber;
    }

    public String getIdbus() {
        return idbus;
    }

    public void setIdbus(String idbus) {
        this.idbus = idbus;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getDateofBooking() {
        return DateofBooking;
    }

    public void setDateofBooking(String dateofBooking) {
        DateofBooking = dateofBooking;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
