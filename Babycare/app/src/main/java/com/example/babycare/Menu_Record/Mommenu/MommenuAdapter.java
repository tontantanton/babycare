package com.example.babycare.Menu_Record.Mommenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.Calculator.CalToday;
import com.example.babycare.Calculator.CalTodayAdapter;
import com.example.babycare.Menu_Record.Dessert.DessertAdapter;
import com.example.babycare.Menu_Record.Dessert.DessertMenu;
import com.example.babycare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MommenuAdapter extends RecyclerView.Adapter<MommenuAdapter.Holder>{

    private List<MomMenu> mMom;

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
        return mMom.size();
    }


    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textTitle;
        TextView textDescription;
        ImageView add;

        public Holder(View itemView){
            super(itemView);
            add = itemView.findViewById(R.id.add);
            textTitle = itemView.findViewById(R.id.name);
            textDescription = itemView.findViewById(R.id.cal);
            itemView.setOnClickListener(this);

        }

        public void setItem(int position) {
            textTitle.setText(mMom.get(position).getName());
            textDescription.setText("ให้พลังงาน " + mMom.get(position).getCalories()+" กิโลแคลอรี่");
        }

        @Override
        public void onClick(View view) {
            if(mListener!=null)
                mListener.onItemClick(getAdapterPosition());
        }


    }

    public MommenuAdapter(List<MomMenu>  momMenus){
        mMom = momMenus;
    }
}