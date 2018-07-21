package com.edu.ebus.ebus.data;

/**
 * Created by USER on 7/20/2018.
 */

public class MySingketonClassTiket {
    private static MySingketonClassTiket instance;
    private Ticket ticket;
    private MySingketonClassTiket() {

    }
    public static MySingketonClassTiket getInstance() {
        if (instance == null) {
            instance = new MySingketonClassTiket ();
        }
        return instance;
    }

    public static void setInstance(MySingketonClassTiket instance) {
        MySingketonClassTiket.instance = instance;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
