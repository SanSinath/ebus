package com.edu.ebus.ebus.home;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Ticket;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BusTicketActivity extends AppCompatActivity {

    private BusTicketAdapter adapter = new BusTicketAdapter();
    private String date,source,destination;
    private ProgressDialog progressBar;
    private TextView textView;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_ticket);
        // Set Toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.bus_ticket);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Recycler view
        RecyclerView recyclerView = findViewById(R.id.rec_ticket);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        textView = findViewById(R.id.noTicket);

        //get data from HomeFrament
        Intent intent = getIntent();
        date = intent.getStringExtra ("data");
        source = intent.getStringExtra ("source");
        destination = intent.getStringExtra ("destination");

        loadTickeFromFirestore();
    }

    private void loadTickeFromFirestore() {
        loadingProgress();
        progressBar.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tickets")
                .whereEqualTo ("DateofBooking",date)
                .whereEqualTo ("Source",source)
                .whereEqualTo ("Destination",destination)
                .get ().addOnSuccessListener (new OnSuccessListener<QuerySnapshot> () {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                Ticket[] obTickets = new Ticket[queryDocumentSnapshots.getDocuments ().size ()];

                if(queryDocumentSnapshots.getDocuments ().size ()==0){
                    progressBar.cancel();
                }
                 // get data from firebase into adapter
                int index = 0;
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Ticket ticket = documentSnapshot.toObject (Ticket.class);
                    obTickets[index] = ticket;
                    index++;

                }
                adapter.setTickets(obTickets);
                progressBar.cancel();
                if (obTickets.length == 0){
                    textView.setVisibility(View.VISIBLE);
                }else {
                    textView.setVisibility(View.GONE);
                }
            }
        });
   }
    private void loadingProgress() {
        progressBar = new ProgressDialog(BusTicketActivity.this);
        progressBar.setMessage("Ticket searching...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
}
