package com.edu.ebus.ebus.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Ticket;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BusTicketActivity extends AppCompatActivity {

    private String TAG = "ebus";
    private BusTicketAdapter adapter = new BusTicketAdapter();
    private String date,source,destination;

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

        //get data from HomeFrament
        Intent intent = getIntent();
        date = intent.getStringExtra ("data");
        source = intent.getStringExtra ("source");
        destination = intent.getStringExtra ("destination");

        loadTickeFromFirestore();
    }

    private void loadTickeFromFirestore() {
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
                    Toast.makeText (BusTicketActivity.this,"Search Not found",Toast.LENGTH_LONG).show ();
                }
                 // get data from firebase into adapter
                int index = 0;
                Toast.makeText (BusTicketActivity.this,"detail get ready"+queryDocumentSnapshots.getDocuments ().size (),Toast.LENGTH_LONG).show ();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Ticket ticket = documentSnapshot.toObject (Ticket.class);
                    String detail = ticket.getDateofBooking ();
                    obTickets[index] = ticket;
                    index++;
                }
                adapter.setTickets(obTickets);
            }
        });
   }
}
