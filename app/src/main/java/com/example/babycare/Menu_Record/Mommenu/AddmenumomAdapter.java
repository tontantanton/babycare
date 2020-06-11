package com.example.babycare.Menu_Record.Mommenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.babycare.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AddmenumomAdapter extends RecyclerView.Adapter<AddmenumomAdapter.ViewHolder>{

    private Context mContext;
    private List<Addmenu> mAddmenu;
    FirebaseUser fuser;


    public AddmenumomAdapter(Context mContext, List<Addmenu> mAddmenu){
        this.mAddmenu = mAddmenu;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public AddmenumomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.addmomlist, parent, false);
        return new AddmenumomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddmenumomAdapter.ViewHolder holder, int position) {
        final Addmenu addmenu = mAddmenu.get(position);
        holder.name.setText(addmenu.getName());
        holder.cal.setText(addmenu.getCal());

    }

    @Override
    public int getItemCount() {
        return mAddmenu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name, cal;


        public ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.namemenu);
            cal = itemView.findViewById(R.id.calmenu);

        }
    }

}
