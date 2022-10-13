package com.edu.ebus.ebus.recent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Booking;
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.home.TicketDetialActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RecentBookingAdapter extends RecyclerView.Adapter<RecentBookingAdapter.RecentViewHolder> {

    private Booking[] showBookings;
    private Booking[] allBooking;
    public RecentBookingAdapter() {
        showBookings = new Booking[0];
    }

    public void setAllBooking(Booking[] allBooking) {
        this.allBooking = allBooking;
        showBookings = allBooking;
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
    public void onBindViewHolder(@NonNull final RecentViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        final Booking booking = showBookings [position];
        holder.txtName.setText(booking.getUsername());
        holder.txtSource.setText(booking.getScoce());
        holder.txtDest.setText(booking.getDestination());
        holder.txtDates.setText(booking.getDate());

    }


    @Override
    public int getItemCount() {
        return showBookings.length;
    }

    public void searchStation(String keyWord) {
        ArrayList<Booking> arrayList = new ArrayList<>();
        for (Booking booking : allBooking){
            if (booking.getUsername().toLowerCase().contains(keyWord.toLowerCase())||booking.getScoce().toLowerCase().contains(keyWord.toLowerCase())
            ||booking.getDestination().toLowerCase().contains(keyWord.toLowerCase())||booking.getDate().toLowerCase().contains(keyWord.toLowerCase())){
                arrayList.add(booking);
            }
        }
        showBookings = arrayList.toArray(new Booking[arrayList.size()]);
        notifyDataSetChanged();
    }

    class RecentViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName,txtSource,txtDest,txtDates;
        private ImageView imgDel;

        public RecentViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_UserName);
            txtSource = itemView.findViewById(R.id.txt_source);
            txtDest = itemView.findViewById(R.id.txt_destination_recent);
            txtDates = itemView.findViewById(R.id.txtDate);
            imgDel = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context c = v.getContext();
                    Intent intent = new Intent(c, TicketDetialActivity.class);
                    int index = getAdapterPosition();
                    Booking booking = allBooking[index];
                    MySingletonClass.getInstance ().setBooking (booking);
                    c.startActivity(intent);
                }
            });
        }
    }
}
