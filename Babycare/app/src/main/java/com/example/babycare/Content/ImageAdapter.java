package com.example.babycare.Content;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>  {
    private Context mContext;
    private List<Upload> mUploads;
//    private AdapterView.OnItemClickListener mListener;
    public ImageAdapter(Context context, List<Upload> uploads ) {
        mContext = context;
        mUploads = uploads;
//        mListener =listener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final Upload uploadCurrent = mUploads.get(position);
        holder.setOnClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick, MotionEvent motionEvent) {
                Log.d("", "onClick=====================================================: "+position);
                Intent browerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uploadCurrent.getLink()));
                mContext.startActivity(browerIntent);
            }
        });
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewDes.setText(uploadCurrent.getmDescription());
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
            , View.OnLongClickListener, View.OnTouchListener  {
        public TextView textViewName;
        public ImageView imageView;
        public TextView textViewDes;

        public ItemClickListener itemClickListener;

        public ImageViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            textViewDes = itemView.findViewById(R.id.text_view_des);
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