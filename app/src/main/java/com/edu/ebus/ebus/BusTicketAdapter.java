package com.edu.ebus.ebus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

public class BusTicketAdapter extends RecyclerView.Adapter<BusTicketAdapter.BusTicketViewHolder> {
    private Ticket[] tickets;
    public void setTickets(Ticket[] tickets) {
        this.tickets = tickets;
        notifyDataSetChanged();
    }
    public BusTicketAdapter(){
        tickets = new Ticket[0];
    }

    @NonNull
    @Override
    public BusTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.bus_ticket_holder, parent, false);
        return new BusTicketViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BusTicketViewHolder holder, int position) {
        Ticket ticket = tickets[position];
        holder.txtName.setText(ticket.getName());
        holder.txtTarget.setText(ticket.getTarget());
        holder.txtDate.setText(ticket.getDateofBooking());
        holder.txtPrices.setText(ticket.getPrice()+"$");
        holder.imageBus.setImageURI(ticket.getImageURI());
    }

    @Override
    public int getItemCount() {
        return tickets.length;
    }
    class BusTicketViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName,txtTarget,txtDate,txtPrices;
        private SimpleDraweeView imageBus;
        private Button booking;

        public BusTicketViewHolder(View itemView) {
            super(itemView);;

            txtName = itemView.findViewById(R.id.txtName);
            txtTarget = itemView.findViewById(R.id.txtPlace);
            txtDate = itemView.findViewById(R.id.txtDateBooking);
            txtPrices = itemView.findViewById(R.id.txtPrice);
            imageBus = itemView.findViewById(R.id.imageURL);
            booking = itemView.findViewById(R.id.btnBooking);
            booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
    }
}
