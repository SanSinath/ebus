package com.edu.ebus.ebus.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.ebus.ebus.EventAdapter;
import com.edu.ebus.ebus.R;

public class EventsFragment extends android.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        return view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated (view,savedInstanceState);
        RecyclerView rclEvent = view.findViewById(R.id.rec_events);

        // Create a layout manager object
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rclEvent.setLayoutManager(layoutManager);
        EventAdapter adapter = new EventAdapter();
        rclEvent.setAdapter(adapter);

    }

}
