package com.example.babycare.Calculator;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.babycare.Chat.Doctor.MessageActivity;
import com.example.babycare.Chat.Doctor.MessageAdapter;
import com.example.babycare.Chat.Doctor.UserAdater;
import com.example.babycare.Model.Chat;
import com.example.babycare.Model.User;
import com.example.babycare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CalTodayAdapter extends RecyclerView.Adapter<CalTodayAdapter.ViewHolder> {

    public static final int TRUE = 1;
    public static final int FALSE = 0;
    private Context mContext;
    private List<CalToday> mcalTodays;
    FirebaseUser fuser;


    public CalTodayAdapter(Context mContext, List<CalToday> mcalTodays){
        this.mcalTodays = mcalTodays;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public CalTodayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_cal_item, parent, false);
        return new CalTodayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalTodayAdapter.ViewHolder holder, int position) {
        final CalToday calToday = mcalTodays.get(position);
        holder.name.setText(calToday.getName());
        holder.cal.setText(calToday.getCalories()+" กิโลแคลอรี่");



//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, CalTodayActivity.class);
//                intent.putExtra("id", calToday.getId());
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mcalTodays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name, cal;


        public ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.name);
            cal = itemView.findViewById(R.id.cal);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(mcalTodays.get(position).getId().equals(fuser.getUid())){
            return TRUE;
        } else {
            return FALSE;
        }
    }



}

