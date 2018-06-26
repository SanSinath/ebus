package com.edu.ebus.ebus;

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
    }
}
