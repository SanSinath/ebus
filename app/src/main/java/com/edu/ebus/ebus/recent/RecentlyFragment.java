package com.edu.ebus.ebus.recent;

import android.app.Fragment;
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
import com.edu.ebus.ebus.data.Booking;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class RecentlyFragment extends Fragment {
    RecyclerView recyclerView;
    RecentBookingAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rec_Recent);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecentBookingAdapter();
        recyclerView.setAdapter(adapter);

        dataloadbookingfromfirebase();


    }
    public void dataloadbookingfromfirebase(){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("userTicket").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Log.i("ebus","getdata success");
                    getdata(task);
                }
                else {
                    Toast.makeText(getActivity(), "Load Booking error.", Toast.LENGTH_LONG).show();
                    Log.d("ebus", "Load events error: " + task.getException());
                }
            }
        });
    }
    public void getdata(Task<QuerySnapshot> task){
        Booking[] bookings = new Booking[task.getResult().size()];
        int i=0;
        for (QueryDocumentSnapshot document : task.getResult()){
            Booking booking = document.toObject(Booking.class);
            bookings[i]=booking;
            Log.i("ebus","getdata success for");
            i++;
        }
        adapter.setBooking(bookings);
    }


}
