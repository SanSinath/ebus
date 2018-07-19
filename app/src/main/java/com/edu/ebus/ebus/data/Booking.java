package com.edu.ebus.ebus.data;

public class Booking {
    private String date;
    private String destination;
    private String idbus;
    private String money;
    private String namecompany;
    private String numberticket;
    private String phonecompany;
    private String scoce;
    private String subtotal;
    private String time;
    private String username;

    public Booking(){

    }

    public Booking(String date, String destination, String idbus, String money, String namecompany, String numberticket, String phonecompany, String scoce, String subtotal, String time, String username) {
        this.date = date;
        this.destination = destination;
        this.idbus = idbus;
        this.money = money;
        this.namecompany = namecompany;
        this.numberticket = numberticket;
        this.phonecompany = phonecompany;
        this.scoce = scoce;
        this.subtotal = subtotal;
        this.time = time;
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getIdbus() {
        return idbus;
    }

    public void setIdbus(String idbus) {
        this.idbus = idbus;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNamecompany() {
        return namecompany;
    }

    public void setNamecompany(String namecompany) {
        this.namecompany = namecompany;
    }

    public String getNumberticket() {
        return numberticket;
    }

    public void setNumberticket(String numberticket) {
        this.numberticket = numberticket;
    }

    public String getPhonecompany() {
        return phonecompany;
    }

    public void setPhonecompany(String phonecompany) {
        this.phonecompany = phonecompany;
    }

    public String getScoce() {
        return scoce;
    }

    public void setScoce(String scoce) {
        this.scoce = scoce;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
