package com.edu.ebus.ebus.recent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Booking;
import com.edu.ebus.ebus.data.Events;
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.home.TicketDetialActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RecentBookingAdapter extends RecyclerView.Adapter<RecentBookingAdapter.RecentViewHolder> implements View.OnCreateContextMenuListener {

    private Booking[] showBookings;
    private Booking[] allBooking;
    private String ID;
    public RecentBookingAdapter() {
        showBookings = new Booking[0];
    }

    public void setAllBooking(Booking[] allBooking) {
        this.allBooking = allBooking;
        showBookings = allBooking;
        notifyDataSetChanged();
    }
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recent_view_holder, parent, false);
        return new RecentViewHolder(view);
    }

    @Override
    public void onViewRecycled(@NonNull RecentViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);

    }
    @Override
    public void onBindViewHolder(@NonNull final RecentViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        final Booking booking = showBookings [position];
        holder.txtName.setText(booking.getUsername());
        holder.txtSource.setText(booking.getScoce());
        holder.txtDest.setText(booking.getDestination());
        holder.txtDates.setText(booking.getDate());
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                int index = holder.getAdapterPosition();
                Booking booking = allBooking[index];
                ID = booking.getDocID();

                Log.d("ebus","id doc :"+ID);
                DocumentReference reference = firebaseFirestore.collection("userTicket")
                        .document(ID);
                reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                            Log.d("ebus","Doc ID : " + ID);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context,"fialed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Delete ticket");
        menu.add(Menu.NONE,v.getId(),Menu.NONE,"Delete");
        menu.add(Menu.NONE,v.getId(),Menu.NONE,"Cancel");
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
