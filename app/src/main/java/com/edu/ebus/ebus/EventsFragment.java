package com.edu.ebus.ebus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EventsFragment extends android.app.Fragment {

    private BusTicketAdapter adater;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        return view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated (view,savedInstanceState);
        RecyclerView rclEvent = view.findViewById(R.id.rec_ticket);

        // Create a layout manager object
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager (getActivity());
        rclEvent.setLayoutManager(layoutManager);

        // Create an adapter
        adater = new BusTicketAdapter ();
        rclEvent.setAdapter(adater);

        // Load events from Firestore
        loadEventsFromFirestore();

    }
    private void loadEventsFromFirestore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tickets").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    processEventsResult(task);
                    Toast.makeText(getActivity(), "Load events error.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Load events error.", Toast.LENGTH_LONG).show();
                    Log.d("eBus", "Load events error: " + task.getException());
                }
            }
        });
    }

    private void processEventsResult(@NonNull Task<QuerySnapshot> task) {
        Ticket[] tickets = new Ticket[task.getResult().size()];
        int index = 0;
        for (QueryDocumentSnapshot document : task.getResult()) {
            // Convert Firestore document to Event object
            Ticket ticket = document.toObject(Ticket.class);
            tickets[index] = ticket;
            index++;
        }
        adater.setTickets (tickets);
    }

}
