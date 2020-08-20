package com.yoloday.yolotest2.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.yoloday.yolotest2.ListInfo;
import com.yoloday.yolotest2.R;
import com.yoloday.yolotest2.activity.EventDetailActivity;
import com.yoloday.yolotest2.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ListInfo> arrayList;
    private Activity activity;




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        
        return new MyViewHolder(view);
    }

    public ListAdapter(ArrayList<ListInfo> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;

    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.MyViewHolder holder, int position) {

        holder.title.setText(arrayList.get(position).getTitle());
        holder.category.setText(arrayList.get(position).getCategory());
        holder.location_name.setText(arrayList.get(position).getLocation_name());
       // Picasso.get().load(photoUrl.get(position).getPhotoUrl()).into(holder.photoUrl);

        Log.d("title","title");

    }

    @Override
    public int getItemCount() {
        return (arrayList!=null? arrayList.size():0);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,category,location_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            category = (TextView) itemView.findViewById(R.id.category);
            location_name = (TextView) itemView.findViewById(R.id.location_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(activity, EventDetailActivity.class);
                    activity.startActivity(intent);


                }
            });
        }
    }
}







