package com.yoloday.yolotest2.adapter;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ListInfo> arrayList;




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
       // Picasso.get().load(photoUrl.get(position).getPhotoUrl()).into(holder.photoUrl);


    }

    @Override
    public int getItemCount() {
        return (arrayList!=null? arrayList.size():0);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            category = (TextView) itemView.findViewById(R.id.category);

        }
    }
}







