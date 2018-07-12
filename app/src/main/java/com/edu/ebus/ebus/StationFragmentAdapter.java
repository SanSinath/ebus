package com.edu.ebus.ebus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by USER on 7/11/2018.
 */

public class StationFragmentAdapter extends RecyclerView.Adapter<StationFragmentAdapter.StationViewHolder>{

    private Ticket[] tickets;
    public void setTicketall(Ticket[] ticketsall) {
        this.tickets = ticketsall;
        notifyDataSetChanged();
    }
    public StationFragmentAdapter(){
        tickets = new Ticket[0];
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.bus_ticket_holder, parent, false);
        StationViewHolder viewHolder = new StationViewHolder (view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        Ticket ticket = tickets[position];
        holder.txtName.setText(ticket.getName());
        holder.txtsoure.setText (ticket.getSource ());
        holder.txtdestination.setText (ticket.getDestination ());
        holder.txthour.setText (ticket.getHour ());
        holder.txtDate.setText(ticket.getDateofBooking());
        holder.txtPrices.setText(ticket.getPrice()+"$");
        holder.imageBus.setImageURI(ticket.getImageURI());

    }

    @Override
    public int getItemCount() {
        return tickets.length;
    }

    class StationViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName,txtDate,txtPrices,txtsoure,txtdestination,txthour;
        private SimpleDraweeView imageBus;
        private Button booking;

        public StationViewHolder(View itemView) {

            super (itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtsoure = itemView.findViewById(R.id.txt_source);
            txtdestination =itemView.findViewById (R.id.txt_destination);
            txthour = itemView.findViewById (R.id.txt_hour);
            txtDate = itemView.findViewById(R.id.txtDateBooking);
            txtPrices = itemView.findViewById(R.id.txtPrice);
            imageBus = itemView.findViewById(R.id.imageURL);
            booking = itemView.findViewById(R.id.btnBooking);
        }
    }
}
