package com.example.smartlearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    Context context;

    ArrayList ques=new ArrayList();
    ArrayList op1=new ArrayList();
    ArrayList op2=new ArrayList();
    ArrayList op3=new ArrayList();
    ArrayList op4=new ArrayList();
    ArrayList ans=new ArrayList();
    String []answered;

    public TestAdapter(ObjectiveTesting objectiveTesting, ArrayList ques, ArrayList op1, ArrayList op2, ArrayList op3, ArrayList op4, ArrayList ans, String[] answered) {
        this.context=objectiveTesting;
        this.ques=ques;

        this.op1=op1;
        this.op2=op2;
        this.op3=op3;
        this.op4=op4;
        this.ans=ans;
        this.answered=answered;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.test_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ques.setText(ques.get(position).toString().trim());

       /* holder.op1.setText(op1.get(position).toString().trim());
        holder.op2.setText(op2.get(position).toString().trim());
        holder.op3.setText(op3.get(position).toString().trim());
        holder.op4.setText(op4.get(position).toString().trim());*/

        holder.rb1.setText(op1.get(position).toString().trim());
        holder.rb2.setText(op2.get(position).toString().trim());
        holder.rb3.setText(op3.get(position).toString().trim());
        holder.rb4.setText(op4.get(position).toString().trim());


        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton=group.findViewById(checkedId);
                if( radioButton.getText().toString().equalsIgnoreCase(ans.get(position).toString())){
                   answered[position]="1";

                }
                else{
                    answered[position]="0";
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return ques.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ques,op1,op2,op3,op4;
        Button sub;
        RadioButton rb1,rb2,rb3,rb4;
        RadioGroup radioGroup;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sub=itemView.findViewById(R.id.sub);
            radioGroup=itemView.findViewById(R.id.radio_group_options);

            ques=itemView.findViewById(R.id.qs);

            /*op1=itemView.findViewById(R.id.op1);
            op2=itemView.findViewById(R.id.op2);
            op3=itemView.findViewById(R.id.op3);
            op4=itemView.findViewById(R.id.op4);*/

            rb1=itemView.findViewById(R.id.rb1);
            rb2=itemView.findViewById(R.id.rb2);
            rb3=itemView.findViewById(R.id.rb3);
            rb4=itemView.findViewById(R.id.rb4);


        }
    }
}
