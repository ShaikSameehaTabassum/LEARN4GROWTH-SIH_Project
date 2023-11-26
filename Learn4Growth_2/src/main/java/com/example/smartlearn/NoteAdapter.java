package com.example.smartlearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    Context context;
    ArrayList list4=new ArrayList();
    ArrayList list5=new ArrayList();


    public NoteAdapter(NoteMakingActivity payment, ArrayList list1, ArrayList list2) {
        this.context=payment;
        this.list4=list1;
        this.list5=list2;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textView.setText(list4.get(position).toString().trim());
        holder.textView1.setText(list5.get(position).toString());

    }
    @Override
    public int getItemCount() {
        return list4.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.nt);
            textView1=(TextView)itemView.findViewById(R.id.dt);
        }
    }
}