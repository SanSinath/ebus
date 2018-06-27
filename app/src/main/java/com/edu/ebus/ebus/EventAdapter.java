package com.edu.ebus.ebus;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{
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

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        public EventViewHolder(View itemView) {
            super(itemView);
        }
    }
}
