package com.edu.ebus.ebus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends android.app.Fragment {

    private Editable TEMP;
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
        edSrc = view.findViewById(R.id.etSrc);
        edDes = view.findViewById(R.id.etDes);
        btnSearchTicket = view.findViewById(R.id.btn_booking);
        imSwap = view.findViewById(R.id.imgSwap);
        imSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TEMP = edSrc.getText();
                edSrc.setText(edDes.getText());
                edDes.setText(TEMP);
            }
        });

    }
}
