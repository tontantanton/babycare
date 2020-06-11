package com.example.babycare.Menu_Record.Sidedish;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.R;

import java.util.List;

public class SidedishAdapter extends RecyclerView.Adapter<SidedishAdapter.Holder>{
    private List<SidedishMenu> sidedish;

    @NonNull
    SidedishAdapter.ItemClickListener mListener;


    public interface ItemClickListener{
        void onItemClick(int position);
    }

    public void setItemClickListener(SidedishAdapter.ItemClickListener listener){
        mListener = listener;
    }

    @Override
    public SidedishAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sidedishitem,viewGroup,false);
        SidedishAdapter.Holder holder = new SidedishAdapter.Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SidedishAdapter.Holder holder, int i) {
        holder.setItem(i);
    }

    @Override
    public int getItemCount() {
        return sidedish.size();
    }


    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textTitle;
        TextView textDescription;
        ImageView add;

        public Holder(View itemView){
            super(itemView);
            add = itemView.findViewById(R.id.add);
            textTitle = itemView.findViewById(R.id.text_title_sidedish);
            textDescription = itemView.findViewById(R.id.text_description_sidedish);
            itemView.setOnClickListener(this);

        }

        public void setItem(int position) {
            textTitle.setText(sidedish.get(position).getName());
            textDescription.setText("ให้พลังงาน " + sidedish.get(position).getCalories()+" กิโลแคลอรี่");
        }

        @Override
        public void onClick(View view) {
            if(mListener!=null)
                mListener.onItemClick(getAdapterPosition());
        }


    }

    public SidedishAdapter(List<SidedishMenu>  sidedishs){
        sidedish = sidedishs;
    }

}
