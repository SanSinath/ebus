package com.edu.ebus.ebus;

public class Ticket {

    private String Name;
    private String Target;
    private String DateofBooking;
    private String price;
    private String busImg;

    private Ticket()
    {

    }
    public Ticket(String name, String target, String dateofBooking,String Price,String BusImg) {
        Name = name;
        Target = target;
        DateofBooking = dateofBooking;
        price = Price;
        busImg = BusImg;
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

    public String getBusImg() {
        return busImg;
    }

    public void setBusImg(String busImg) {
        this.busImg = busImg;
    }
}
