package com.edu.ebus.ebus;

<<<<<<< HEAD
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
=======
/**
 * Created by USER on 6/22/2018.
 */

public class Ticket {
    private String name;
    private String destinatin;
    private String date;

    public Ticket(){

    }

    public Ticket(String name, String source, String destinatin, String date) {
        this.name = name;
        this.destinatin = destinatin;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestinatin() {
        return destinatin;
    }

    public void setDestinatin(String destinatin) {
        this.destinatin = destinatin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
>>>>>>> origin/master
    }
}
