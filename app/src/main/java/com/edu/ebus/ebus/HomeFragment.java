package com.edu.ebus.ebus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeFragment extends android.app.Fragment{

    private int TEMP;
    private Spinner spSrc,spDes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final EditText edSrc,edDes;
        ImageView imSwap;
        Button btnSearchTicket;
        super.onViewCreated(view, savedInstanceState);
        spSrc = view.findViewById(R.id.spSrc);
        spDes = view.findViewById(R.id.spDes);

        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.place, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSrc.setAdapter(arrayAdapter);
        spDes.setAdapter(arrayAdapter);

        spSrc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spSrc.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spDes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spDes.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Swap value both Spinner
        imSwap = view.findViewById(R.id.imgSwap);
        imSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spSrc.getAdapter().equals(spSrc.getSelectedItemPosition())){
                    spSrc.setAdapter(spDes.getAdapter());
                    spDes.setAdapter(spSrc.getAdapter());
                }else{
                    spSrc.setAdapter(spSrc.getAdapter());
                    spDes.setAdapter(spDes.getAdapter());
                }
            }
        });
        btnSearchTicket = view.findViewById(R.id.btn_booking);
        btnSearchTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BusTicketActivity.class));
            }
        });
    }
}
