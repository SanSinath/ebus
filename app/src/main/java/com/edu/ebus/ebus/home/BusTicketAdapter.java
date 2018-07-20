package com.edu.ebus.ebus.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Ticket;
import com.facebook.drawee.view.SimpleDraweeView;

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



    class BusTicketViewHolder extends RecyclerView.ViewHolder{
        TextView txtName,txtDate,txtPrices,txtsoure,txtdestination,txthour;
        SimpleDraweeView imageBus;
        public Button booking;
        BusTicketViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtsoure = itemView.findViewById(R.id.txt_source);
            txtdestination =itemView.findViewById (R.id.txt_destination);
            txthour = itemView.findViewById (R.id.txt_hour);
            txtDate = itemView.findViewById(R.id.txtDateBooking);
            txtPrices = itemView.findViewById(R.id.txtPrice);
            imageBus = itemView.findViewById(R.id.imageURL);
            booking = itemView.findViewById(R.id.btnBooking);
            booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext ();
                    Intent intent = new Intent (v.getContext (),SetTicketActivity.class);
                    context.startActivity (intent);
                    Log.d("booking", "Go to set ticket");

                }
            });
        }
    }
}
