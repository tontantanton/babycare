package com.example.babycare.Menu_recommand;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.babycare.R;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MenurecAdapter extends RecyclerView.Adapter<MenurecAdapter.MenuViewHolder> {
    private Context mContext;
    private ArrayList<Menureccommand> mMenuRecs;

    public MenurecAdapter(Context context, ArrayList<Menureccommand> menuRecs) {
        mContext = context;
        mMenuRecs = menuRecs;
    }


    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.menurecom_item, parent, false);
        return new MenuViewHolder(v);
    }

    public void onBindViewHolder(final MenuViewHolder holder, int position) {
        final Menureccommand menuCurrent = mMenuRecs.get(position);
        holder.setOnClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick, MotionEvent motionEvent) {
                Log.d("","onClick=========:"+position);
                Intent imageIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(menuCurrent.getImages()));
                mContext.startActivity(imageIntent);
            }
        });
        holder.textViewName.setText(menuCurrent.getName());

        Picasso.with(mContext)
                .load(menuCurrent.getImage())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .centerCrop()
                .into(holder.imageView);

//        Picasso.with(mContext).load(mMenuRecs.get(position).getImage()).into(holder.imageView);



    }

    public int getItemCount() {
        return mMenuRecs.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
            , View.OnLongClickListener, View.OnTouchListener {
        TextView textViewName;
        ImageView imageView;

        public ItemClickListener itemClickListener;

        public MenuViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.image_view_menurec);
            textViewName = (TextView) itemView.findViewById(R.id.text_view_menurec);

        }

        public void setOnClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false, null);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true, null);
            return true;
        }


        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            itemClickListener.onClick(view, getAdapterPosition(), false, motionEvent);
            return true;
        }
    }
}
