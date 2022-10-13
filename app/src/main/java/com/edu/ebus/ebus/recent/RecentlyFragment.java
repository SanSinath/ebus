package com.edu.ebus.ebus.recent;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Booking;
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.data.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class RecentlyFragment extends Fragment implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    RecentBookingAdapter adapter;
    TextView textView;
    private ProgressDialog progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rec_Recent);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecentBookingAdapter();
        recyclerView.setAdapter(adapter);
        textView = view.findViewById(R.id.empty);
        // get contecxt from recycler view
        dataloadbookingfromfirebase();

    }
    public void dataloadbookingfromfirebase(){
        UserAccount account = MySingletonClass.getInstance().getAccount();
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("userTicket")
                .whereEqualTo ("uID", account.getId ())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    loadingProgress();
                    progressBar.show();
                    Log.i("ebus","getdata success");
                    getdata(task);
                    progressBar.cancel();
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
        adapter.setAllBooking(bookings);

        if (bookings.length == 0){
            textView.setVisibility(View.VISIBLE);
        }else {
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_station, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search name ,destination ,price");
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String keyWord) {
        adapter.searchStation(keyWord);
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void loadingProgress(){

        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Loading ticket   ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }
}
