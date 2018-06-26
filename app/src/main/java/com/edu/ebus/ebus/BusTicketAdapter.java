package com.edu.ebus.ebus;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

public class BusTicketAdapter extends RecyclerView.Adapter<BusTicketAdapter.BusTicketViewHolder> {
    private Ticket[] tickets;

    public void setTickets(Ticket[] tickets) {
        this.tickets = tickets;
        notifyDataSetChanged();
    }

    public BusTicketAdapter() {
        tickets = new Ticket[0];
    }
    @NonNull
    @Override
    public BusTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.bus_ticket_holder, parent, false);
        BusTicketViewHolder viewHolder = new BusTicketViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusTicketViewHolder holder, int position) {
        Ticket ticket = tickets[position];
        holder.txtName.setText (ticket.getName ());
        holder.txtDestination.setText (ticket.getDestinatin ());
        holder.txtDate.setText (ticket.getDate ());
        
    }

    @Override
    public int getItemCount() {
        return tickets.length;
    }


    class BusTicketViewHolder extends RecyclerView.ViewHolder{

       private TextView txtName;
       private TextView txtDestination;
       private TextView txtDate;


            public BusTicketViewHolder(View itemView) {
                super(itemView);
                txtName = itemView.findViewById(R.id.txtName);
                txtDestination= itemView.findViewById (R.id.txtPlace);
                txtDate = itemView.findViewById (R.id.btn_booking);
            }


    }
}
