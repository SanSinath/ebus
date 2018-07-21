package com.edu.ebus.ebus.data;

public class MySingletonClass {

    private int loginMethod = 1;
    private static MySingletonClass instance;
    private UserAccount account;
    private Booking booking;
    private Ticket ticket;
    private MySingletonClass() {

    }

    public static MySingletonClass getInstance() {
        if (instance == null) {
            instance = new MySingletonClass();
        }
        return instance;
    }

    public int getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(int loginMethod) {
        this.loginMethod = loginMethod;
    }

    public UserAccount getAccount() {
        return account;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }
}
