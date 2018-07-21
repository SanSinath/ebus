package com.edu.ebus.ebus.events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Events;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Events[] events;
    public EventAdapter() {
        events = new Events[0];
    }

    public void setEvents(Events[] events) {
        this.events = events;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_view_holder, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Events event = events[position];
        holder.txttitle.setText (event.getTitle ());
        holder.txtdate.setText (event.getDate ());
        holder.imageurl.setImageURI (event.getImageURL ());

    }

    @Override
    public int getItemCount() {
        return events.length;
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        private TextView txttitle;
        private TextView txtdate;
        private SimpleDraweeView imageurl;
        private ImageView imgMoreOption;
        private Context context;

        public EventViewHolder(View itemView) {
            super(itemView);
            txttitle = itemView.findViewById (R.id.txt_event_title);
            txtdate = itemView.findViewById (R.id.txt_date_event);
            imageurl = itemView.findViewById (R.id.img_event_holder);
            imgMoreOption = itemView.findViewById(R.id.more_evnt_hoder_icon);

            context = itemView.getContext();

            itemView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent ( context ,EventActivitydetails.class);

                    int index = getAdapterPosition();
                    Events event = events[index];
                    Gson gson = new Gson();
                    String eventJson = gson.toJson(event);
                    intent.putExtra("event", eventJson);


                    context.startActivity(intent);

                }
            });

            imgMoreOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = v.getContext();
                    PopupMenu popup = new PopupMenu( context, imgMoreOption );
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.evens_more_option, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                        public boolean onMenuItemClick(MenuItem item) {

                            getMenuOption(item);
                            return true;
                        }
                    });
                    popup.show();
                }
            });
        }

        private void getMenuOption(MenuItem item) {
            if (item.getItemId() == R.id.share_event){

                Toast.makeText(context, "Feature not aviliable...",Toast.LENGTH_SHORT).show();

            }
            if(item.getItemId() == R.id.view_map){

                Events event = events[getAdapterPosition()];
                String locatoinAdd = event.getLocationAddress();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locatoinAdd));
                context.startActivity(intent);

            }
        }

    }
}
