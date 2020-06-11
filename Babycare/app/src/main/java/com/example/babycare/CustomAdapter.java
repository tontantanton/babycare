package com.example.babycare;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    TextView name, calories;

    Context context;

    ArrayList<Data_new_1> data;

    LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<Data_new_1> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.from(context).inflate(R.layout.custom_list, viewGroup, false);

        name = (TextView) view.findViewById(R.id.readname);
        calories = (TextView) view.findViewById(R.id.readage);


        name.setText(name.getText() + data.get(i).getName());
        calories.setText(calories.getText() + "" + data.get(i).getCalories());


        return view;
    }
}
