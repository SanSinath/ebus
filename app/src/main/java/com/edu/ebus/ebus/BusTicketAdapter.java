package com.edu.ebus.ebus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BusTicketAdapter extends RecyclerView.Adapter<BusTicketAdapter.BusTicketViewHolder>{
    private String TAG = "ebus";
    private Ticket[] tickets;
    public BusTicketAdapter(){
        tickets = new Ticket[0];
    }
    public void setTickets(Ticket[] tickets) {
        this.tickets = tickets;
        notifyDataSetChanged();
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
        holder.txtName.setText(ticket.getName());
        holder.txtTarget.setText(ticket.getTarget());
        holder.txtDate.setText(ticket.getDateofBooking());
    }

    @Override
    public int getItemCount() {
        return tickets.length;
    }
    class BusTicketViewHolder extends RecyclerView.ViewHolder{

        private TextView txtName;
        private TextView txtTarget;
        private TextView txtDate;
        BusTicketViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtTarget = itemView.findViewById(R.id.txtPlace);
            txtDate = itemView.findViewById(R.id.txtDateBooking);

        }
    }
}
