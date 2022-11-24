package com.edu.ebus.ebus.station;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.home.SetTicketActivity;
import com.edu.ebus.ebus.data.Ticket;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;


public class StationFragmentAdapter extends RecyclerView.Adapter<StationFragmentAdapter.StationViewHolder>{

    private Ticket[] showtickets;
    private Ticket[] allTickets;
    public void setAllTickets(Ticket[] ticketsall) {
        allTickets = ticketsall;
        showtickets = ticketsall;
        notifyDataSetChanged();
    }
    public StationFragmentAdapter(){
        showtickets = new Ticket[0];
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.bus_ticket_holder, parent, false);
        return new StationViewHolder (view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        Ticket ticket = showtickets[position];
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
        return showtickets.length;
    }

    public void searchStation(String keyWord) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : allTickets){
            if (ticket.getName().toLowerCase().contains(keyWord.toLowerCase()) || ticket.getDestination().toLowerCase()
                    .contains(keyWord.toLowerCase()) || ticket.getPrice().toLowerCase().contains(keyWord.toLowerCase())
                    || ticket.getDateofBooking().toLowerCase().contains(keyWord.toLowerCase())){

                tickets.add(ticket);

            }
        }
        showtickets = tickets.toArray(new Ticket[tickets.size()]);
        notifyDataSetChanged();
    }

    class StationViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName,txtDate,txtPrices,txtsoure,txtdestination,txthour;
        private SimpleDraweeView imageBus;
        private Button booking;

        StationViewHolder(View itemView) {

            super (itemView);
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
                    int index = getAdapterPosition();
                    Ticket ticket = allTickets[index];
                    MySingletonClass.getInstance ().setTicket (ticket);
                    context.startActivity (intent);
                }
            });

        }
    }
}
