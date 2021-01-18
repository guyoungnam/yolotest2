package com.campers.ground.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.campers.ground.ListInfo;
import com.campers.ground.R;
import com.campers.ground.activity.EventDetailActivity;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private static final String TAG = "HomeAdapter";


    public interface OnListItemSelectedInterface{
        void onItemSelected(View view, int position);
    }

    private OnListItemSelectedInterface mListener;

    private Context context;
    private ArrayList<ListInfo> arrayList;
    private Activity activity;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new MyViewHolder(view);
    }

    public HomeAdapter(ArrayList<ListInfo> arrayList, Context context, Activity activity) {
        this.arrayList = arrayList;
        this.context = context;
        this.activity = activity;

    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.MyViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder : call");

        //holder.title.setText(arrayList.get(position).getTitle());
        holder.category.setText(arrayList.get(position).getCategory());
        holder.location_name.setText(arrayList.get(position).getLocation_name());
        holder.price.setText(arrayList.get(position).getPrice());


       // holder.distance.setText(distance);



        Picasso.get().load(arrayList.get(position).getPhotoUrl()).into(holder.photoUrl);

        Log.d(TAG, "category:" + arrayList.get(position).getPrice());
        Log.d(TAG, "photoUrl:" + arrayList.get(position).getPhotoUrl());
        Log.d(TAG, "distance:" + arrayList.get(position).getLatitude());
        Log.d(TAG, "price:" + arrayList.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick : calicked on:" + arrayList.get(position).getLocation_name());

                Intent intent = new Intent(context, EventDetailActivity.class);

                intent.putExtra("price", arrayList.get(position).getPrice());
                intent.putExtra("photoUrl",arrayList.get(position).getPhotoUrl());
                intent.putExtra("address",arrayList.get(position).getAddress());
                intent.putExtra("location_name",arrayList.get(position).getLocation_name());
                intent.putExtra("description",arrayList.get(position).getDescription());
                intent.putExtra("start_data",arrayList.get(position).getStart_data());
                intent.putExtra("end_data",arrayList.get(position).getEnd_data());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView price, category, location_name,distance;
        public ImageView photoUrl;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.category);
            location_name = (TextView) itemView.findViewById(R.id.location_name);
            photoUrl = (ImageView) itemView.findViewById(R.id.photoUrl);
            distance =(TextView)itemView.findViewById(R.id.distanceValue);
            price = (TextView)itemView.findViewById(R.id.price);

            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    mListener.onItemSelected(view,getAdapterPosition());

                    Log.d("TAG","position="+getAdapterPosition());

                    }

                  //  activity.overridePendingTransition(R.anim.fui_slide_in_right, R.anim.fui_slide_in_right);

            });


        }
    }


}











