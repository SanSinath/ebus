package com.edu.ebus.ebus.events;

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

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Events;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EventsFragment extends android.app.Fragment {
     private EventAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated (view,savedInstanceState);
        RecyclerView rclEvent = view.findViewById(R.id.rec_events);

        // Create a layout manager object
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rclEvent.setLayoutManager(layoutManager);

        adapter = new EventAdapter();
        rclEvent.setAdapter(adapter);

        laoddatafromfirebase ();

    }

    private void laoddatafromfirebase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    processEventsResult(task);
                } else {
                    Toast.makeText(getActivity(), "Load events error.", Toast.LENGTH_LONG).show();
                    Log.d("ebus", "Load events error: " + task.getException());
                }
            }
        });

    }
    private void processEventsResult(Task<QuerySnapshot> task) {
        Events[] events = new Events[task.getResult().size()];
        int index = 0;
        for (QueryDocumentSnapshot document : task.getResult()) {
            // Convert Firestore document to Event object
            Events event = document.toObject(Events.class);
            events[index] = event;
            index++;
        }
        adapter.setEvents(events);

    }


}
