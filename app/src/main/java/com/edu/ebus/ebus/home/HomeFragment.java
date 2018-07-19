package com.edu.ebus.ebus.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.edu.ebus.ebus.home.BusTicketActivity;
import com.edu.ebus.ebus.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends android.app.Fragment {

    private EditText mdeplay;
    private DatePickerDialog.OnDateSetListener mderelistener;

    private EditText date;
    private Spinner spSrc,spDes;
    private String source;
    private String destination;
    private String dateset;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ImageView imSwap;
        Button btnSearchTicket;
        super.onViewCreated(view, savedInstanceState);
        spSrc = view.findViewById(R.id.spSrc);
        spDes = view.findViewById(R.id.spDes);
        date = view.findViewById (R.id.txt_date);

        //set calendar
        mdeplay = view.findViewById (R.id.txt_date);
        mdeplay.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance ();
                int year = cal.get (Calendar.YEAR);
                int month = cal.get (Calendar.MONTH);
                int day = cal.get (Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog (
                        getActivity (),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mderelistener,
                        year,month,day);
                dialog.getWindow ().setBackgroundDrawable (new ColorDrawable (Color.TRANSPARENT));
                dialog.show ();

            }
        });
        mderelistener = new DatePickerDialog.OnDateSetListener () {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                dateset = year+"-"+month +"-"+day;
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = dt.parse (dateset);
                    Toast.makeText (getActivity (),"Text cheng",Toast.LENGTH_LONG);
                    dt = new SimpleDateFormat("dd/MMM/yyyy");
                    mdeplay.setText (dt.format (date));
                } catch (ParseException e) {
                    e.printStackTrace ();
                }
            }
        };



        //date.getText ();

        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.place, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSrc.setAdapter(arrayAdapter);
        spDes.setAdapter(arrayAdapter);

        spSrc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                source = spSrc.getSelectedItem().toString ();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spDes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                destination = spDes.getSelectedItem().toString ();
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

            }
        });
        btnSearchTicket = view.findViewById(R.id.btn_booking);
        btnSearchTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //put data to new activity
                Intent intent = new Intent (getActivity (), BusTicketActivity.class);
                intent.putExtra ("data", date.getText ().toString ());
                intent.putExtra ("source",source);
                intent.putExtra ("destination",destination);
               startActivity (intent);
            }
        });


    }
}
