package com.edu.ebus.ebus;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

public class BusTicketActivity extends AppCompatActivity {
    private String TAG = "ebus";
    private BusTicketAdapter adapter = new BusTicketAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_ticket);
        // Set Toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bus Ticket");
        // Recycler view
        RecyclerView recyclerView = findViewById(R.id.rec_ticket);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        // LOAD Ticket from Firestore
        loadTickeFromFirestore();
    }

    private void loadTickeFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tickets").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    proccessTicketResult(task);
                    Toast.makeText(BusTicketActivity.this,"load successfull",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(BusTicketActivity.this, "Load tickets error.", Toast.LENGTH_LONG).show();
                    Log.d("ebus", "Load tickets error: " + task.getException());
                }
            }
        });
}
    private void proccessTicketResult(Task<QuerySnapshot> task) {
        Log.d(TAG, "Size: " + task.getResult().size());
        Ticket[] obTickets = new Ticket[task.getResult().size()];
        int index = 0;
        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
            Ticket ticket = documentSnapshot.toObject(Ticket.class);
            obTickets[index] = ticket;
            index++;
        }
        Log.d(TAG, "Set Data to Adapter" );
        adapter.setTickets(obTickets);
    }
}
