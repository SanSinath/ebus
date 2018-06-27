package com.edu.ebus.ebus;

public class Ticket {

    private String Name;
    private String Target;
    private String DateofBooking;

    private Ticket()
    {

    }
    public Ticket(String name, String target, String dateofBooking) {
        Name = name;
        Target = target;
        DateofBooking = dateofBooking;
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
}
