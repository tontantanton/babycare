package com.example.babycare;


import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_1 extends RecyclerView.Adapter<Adapter_1.ViewHolder> {

    Context context;
    List<Model_1> MainImageUploadInfoList;

    public Adapter_1(Context context, List<Model_1> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nutrients_graph_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Model_1 studentDetails = MainImageUploadInfoList.get(position);

        holder.StudentNameTextView.setText(studentDetails.getName());

        holder.StudentNumberTextView.setText(studentDetails.getCalories());

    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView StudentNameTextView;
        public TextView StudentNumberTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            StudentNameTextView = (TextView) itemView.findViewById(R.id.ShowStudentNameTextView);

            StudentNumberTextView = (TextView) itemView.findViewById(R.id.ShowStudentNumberTextView);
        }
    }
}
