package com.edu.ebus.ebus.events;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Events;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{

    private Events[] events;

    public EventAdapter() {
        events = new Events[0];
    }

    public void setEvents(Events[] events) {
        this.events = events;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_view_holder, parent, false);
        EventViewHolder holder = new EventViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Events event = events[position];
        holder.txttitle.setText (event.getTitle ());
        holder.txtdate.setText (event.getDate ());
        holder.imageurl.setImageURI (event.getImageURL ());

    }

    @Override
    public int getItemCount() {
        return events.length;
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        private TextView txttitle;
        private TextView txtdate;
        private SimpleDraweeView imageurl;

        public EventViewHolder(View itemView) {
            super(itemView);
            txttitle = itemView.findViewById (R.id.txt_event_title);
            txtdate = itemView.findViewById (R.id.txt_date_event);
            imageurl = itemView.findViewById (R.id.img_event_holder);

            itemView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    Context con = view.getContext ();
                    Intent intent = new Intent (view.getContext (),EventActivitydetails.class);

                    int index = getAdapterPosition();
                    Events event = events[index];
                    Gson gson = new Gson();
                    String eventJson = gson.toJson(event);
                    intent.putExtra("event", eventJson);

                    con.startActivity (intent);
                }
            });
        }

    }
}
