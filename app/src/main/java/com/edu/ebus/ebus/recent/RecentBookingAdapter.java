package com.edu.ebus.ebus.recent;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.home.TicketDetialActivity;

public class RecentBookingAdapter extends RecyclerView.Adapter<RecentBookingAdapter.RecentViewHolder>{

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recent_view_holder, parent, false);
        return new RecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class RecentViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName,txtDest,txtDates;
        private ImageView imageView;

        public RecentViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_UserName);
            txtDest = itemView.findViewById(R.id.txtDes);
            txtDates = itemView.findViewById(R.id.txtDate);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context c = v.getContext();
                    Intent intent = new Intent(v.getContext(), TicketDetialActivity.class);
                    c.startActivity(intent);
                }
            });
        }
    }
}
