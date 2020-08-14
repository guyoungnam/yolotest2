package com.yoloday.yolotest2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yoloday.yolotest2.ListInfo;
import com.yoloday.yolotest2.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder> {

    private Context context;
    private List<ListInfo> list = new ArrayList<>();

    public ListAdapter(Context context, List<ListInfo> list){
        this.context = context;
        this.list = list;
    }


    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;

        holder.title.setText(list.get(itemposition).getTitle());
        holder.category.setText(list.get(itemposition).getCategory());

        Log.e("StudyApp", "onBindViewHolder" + itemposition);
    }



    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView category;

        public Holder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            category = (TextView) view.findViewById(R.id.category);
        }
    }
}

