package com.edu.ebus.ebus.home;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.edu.ebus.ebus.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private DatePickerDialog.OnDateSetListener mderelistener;
    private ProgressDialog progressBar;
    private EditText date;
    private Spinner spSrc,spDes;
    private String source;
    private String destination;
    private String dateset;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Button btnSearchTicket;
        super.onViewCreated(view, savedInstanceState);
        spSrc = view.findViewById(R.id.spSrc);
        spDes = view.findViewById(R.id.spDes);
        date = view.findViewById (R.id.txt_date);
        btnSearchTicket = view.findViewById(R.id.btn_booking);

        //set calendar
        date.setOnClickListener (new View.OnClickListener () {
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable (new ColorDrawable (Color.TRANSPARENT));
                }
                dialog.show ();

            }
        });
        mderelistener = new DatePickerDialog.OnDateSetListener () {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                dateset = year+"-"+month +"-"+day;
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                Date dator = null;
                try {
                    dator = dt.parse (dateset);
                    dt = new SimpleDateFormat("dd/MMM/yyyy");
                    date.setText (dt.format (dator));
                } catch (ParseException e) {
                    e.printStackTrace ();
                }
            }
        };


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
        btnSearchTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgress();
                progressBar.show();
               //put data to new activity
                Intent intent = new Intent (getActivity (), BusTicketActivity.class);
                intent.putExtra ("data", date.getText ().toString ());
                intent.putExtra ("source",source);
                intent.putExtra ("destination",destination);
               startActivity (intent);
               progressBar.cancel();
            }
        });
    }

    private void loadingProgress() {
        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Ticket searching...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
}
