package com.example.babycare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.Holder>{
    private Context mContext;
    private List<RecordMenu> mRecord;

    @NonNull
    ItemClickListener mListener;

    public interface ItemClickListener{
        void onItemClick(int position);
    }

    public void setItemClickListener(ItemClickListener listener){
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mommenuitem,viewGroup,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.setItem(i);
    }

    @Override
    public int getItemCount() {
        return mRecord.size();
    }


    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textTitle;
        TextView textDescription;
        ImageView add;

        public Holder(View itemView){
            super(itemView);
            add = itemView.findViewById(R.id.add);
            textTitle = itemView.findViewById(R.id.text_title_dessert);
            textDescription = itemView.findViewById(R.id.text_description_dessert);
            itemView.setOnClickListener(this);

        }

        public void setItem(int position) {
            textTitle.setText(mRecord.get(position).getName());
            textDescription.setText("ให้พลังงาน " + mRecord.get(position).getCalories()+" กิโลแคลอรี่");
        }

        @Override
        public void onClick(View view) {
            if(mListener!=null)
                mListener.onItemClick(getAdapterPosition());
        }


    }

    public RecordAdapter(List<RecordMenu>  recordMenus){
        mRecord = recordMenus;
    }



//
//
//
//    public RecordAdapter(Context mContext, List<RecordMenu> mRecord){
//        this.mRecord = mRecord;
//        this.mContext = mContext;
//
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.addmomlist, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        final RecordMenu record = mRecord.get(position);
//        holder.name.setText(record.getName());
//        holder.cal.setText(record.getCalories());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mRecord.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView name, cal;
//
//
//        public ViewHolder(View itemView){
//            super(itemView);
//
//            name = itemView.findViewById(R.id.namemenu);
//            cal = itemView.findViewById(R.id.calmenu);
//
//        }
//    }


}
