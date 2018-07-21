package com.edu.ebus.ebus.recent;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Booking;
import com.edu.ebus.ebus.data.Events;
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.home.TicketDetialActivity;
import com.google.gson.Gson;

public class RecentBookingAdapter extends RecyclerView.Adapter<RecentBookingAdapter.RecentViewHolder>{

    private Booking[] bookings;
    public RecentBookingAdapter() {
        bookings = new Booking[0];
    }

    public void setBooking(Booking[] events) {
        this.bookings = events;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recent_view_holder, parent, false);
        return new RecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, int position) {
        Booking booking = bookings [position];
        holder.txtName.setText(booking.getUsername());
        holder.txtSource.setText(booking.getScoce());
        holder.txtDest.setText(booking.getDestination());
        holder.txtDates.setText(booking.getDate());

    }

    @Override
    public int getItemCount() {
        return bookings.length;
    }

    class RecentViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName,txtSource,txtDest,txtDates;
        private ImageView imageView;

        public RecentViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_UserName);
            txtSource = itemView.findViewById(R.id.txt_source);
            txtDest = itemView.findViewById(R.id.txt_destination_recent);
            txtDates = itemView.findViewById(R.id.txtDate);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context c = v.getContext();
                    Intent intent = new Intent(c, RecntlyActivity.class);
                    int index = getAdapterPosition();
                    Booking booking = bookings[index];
                    MySingletonClass.getInstance ().setBooking (booking);
                    c.startActivity(intent);
                }
            });
        }
    }
}
