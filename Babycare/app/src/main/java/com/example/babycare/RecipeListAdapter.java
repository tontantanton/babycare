package com.example.babycare;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecipeListAdapter extends BaseAdapter {
    Context context;
    List<Recipes> rowItems;
   // DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    // DatabaseReference mUserRef = mRootRef.child("users");
   // DatabaseReference mUserRef = mRootRef.child("userdata3");

    public RecipeListAdapter(Context context, List<Recipes> items) {
        this.context = context;
        this.rowItems = items;
    }

    private class ViewHolder {
        //  ImageView imageView;
        TextView txtID;
        TextView text_name;
        TextView text_calories;
        TextView text_protein;
        TextView text_vitamina;
        TextView text_vitaminc;
        TextView text_vitaminb6;
        TextView text_vitaminb12;
        TextView text_calcium;
        TextView text_iodine;
        TextView text_iron;


        // Button addRecipe;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.recipes_list, null);
            holder = new ViewHolder();

            holder.txtID         = (TextView) convertView.findViewById(R.id.txtID);
            holder.text_name     = (TextView) convertView.findViewById(R.id.text_name);
            holder.text_calories = (TextView) convertView.findViewById(R.id.text_calories);
            holder.text_protein  = (TextView) convertView.findViewById(R.id.text_protein);
            holder.text_vitamina  = (TextView) convertView.findViewById(R.id.text_vitamina);
            holder.text_vitaminc  = (TextView) convertView.findViewById(R.id.text_vitaminc);
            holder.text_vitaminb6  = (TextView) convertView.findViewById(R.id.text_vitaminb6);
            holder.text_vitaminb12  = (TextView) convertView.findViewById(R.id.text_vitaminb12);
            holder.text_calcium  = (TextView) convertView.findViewById(R.id.text_calcium);
            holder.text_iodine  = (TextView) convertView.findViewById(R.id.text_iodine);
            holder.text_iron  = (TextView) convertView.findViewById(R.id.text_iron);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Recipes rowItem = (Recipes) getItem(position);

        final String name = rowItem.getId();//n
        final String calories = rowItem.getName();//c
        final String id = rowItem.getCalories(); //id
        final String protein = rowItem.getProtein();  //p
        final String vitamina = rowItem.getVitamina();//va
        final String vitaminc = rowItem.getVitaminac();//vc
        final String vitaminb6 = rowItem.getVitaminb6();//vc
        final String vitaminb12 = rowItem.getVitaminb12();//vc
        final String calcium = rowItem.getCalcium();//vc
        final String iodine = rowItem.getIodine();//vc
        final String iron = rowItem.getIron();//vc


        holder.txtID.setText(id);
        holder.text_name.setText(name);
        holder.text_calories.setText(calories);
        holder.text_protein.setText(protein);
        holder.text_vitamina.setText(vitamina);
        holder.text_vitaminc.setText(vitaminc);
        holder.text_vitaminb6.setText(vitaminb6);
        holder.text_vitaminb12.setText(vitaminb12);
        holder.text_calcium.setText(calcium);
        holder.text_iodine.setText(iodine);
        holder.text_iron.setText(iron);

        //Picasso.with(context).load(img_url).error(R.drawable.placeholder_error).into(holder.imageView);
        // Picasso.get(context).load((img_url).error(R.drawable.placeholder_error).into(holder.imageView);


//        holder.addRecipe.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String uid = "a9C1zkRCtWTGvTZk9aJJryR3oCj1";
//                DatabaseReference mspecificUser = mUserRef.child(uid+"/recipes/"+id);
//                mspecificUser.child("title").setValue(title);
//                mspecificUser.child("url").setValue(url);
//                mspecificUser.child("image_url").setValue(img_url);
//
//                Toast.makeText(v.getContext(), title+" has been added.", Toast.LENGTH_SHORT).show();
//            }
//        });

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

}
