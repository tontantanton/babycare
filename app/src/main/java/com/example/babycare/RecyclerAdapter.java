package com.example.babycare;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Data> list;

    public RecyclerAdapter(List<Data> list) {
        this.list = list;
    }

//    private Context context;
//
//       public MyAdapter(Context context) {
//        this.context = context;
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context con = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(con);
        View view = layoutInflater.inflate(R.layout.adapter_recyclerview, parent, false);
        return new ViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.something.setText(
//                "\n\nName: "+list.get(position).getName()+
//                "\nLocation: "+list.get(position).getLocation()+
//                "\nProfession: "+list.get(position).getProfession()+
//                "\nNumber: "+list.get(position).getPhone()+
//                "\nAddress: "+list.get(position).getAddress()+
//                "\nDescription: "+list.get(position).getDescription()
//        );
//    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        holder.something.setText(
//                "\n\nName: "+list.get(position).getName()+
//                        "\nlastName: "+list.get(position).getLastname()

        holder.text_1.setText("" + list.get(position).getName());
        holder.text_2.setText("" + list.get(position).getCalories());



        holder.text_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mItemClickListener != null) {
//                    mItemClickListener.onItemClick(position);
//                }


                //Intent intent =  new Intent (view.getContext(), Page_detail.class);
                //  startActivityForResult(intent, 0);

                // con.startActivity(...);
                //    view.getContext().startActivity(new Intent (view.getContext(), Page_detail.class));


                Intent intent= new Intent(view.getContext(), Page_detail.class);
                //intent.putExtra("your_extra","your_class_value");
                intent.putExtra("s_text_1",list.get(position).getName());
                intent.putExtra("s_text_2",list.get(position).getCalories());
                intent.putExtra("s_text_3",list.get(position).getProtein());
                intent.putExtra("s_text_4",list.get(position).getVitamina());
                intent.putExtra("s_text_5",list.get(position).getVitaminc());
                intent.putExtra("s_text_6",list.get(position).getVitaminb6());
                intent.putExtra("s_text_7",list.get(position).getVitaminb12());
                intent.putExtra("s_text_8",list.get(position).getCalcium());
                intent.putExtra("s_text_9",list.get(position).getIodine());
                intent.putExtra("s_text_10",list.get(position).getIron());

                view.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView text_1;
        private TextView text_2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_1 = itemView.findViewById(R.id.text_1);
            text_2 = itemView.findViewById(R.id.text_2);
        }


//        public void addItemClickListener(ItemClickListener listener) {
//            mItemClickListener = listener;
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerViewHolders holder, final int position) {
//            holder.countryName.setText(itemList.get(position).getName());
//            holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
//            holder.countryPhoto.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mItemClickListener != null) {
//                        mItemClickListener.onItemClick(position);
//                    }
//                }
//            });
//        }

//        @Override
//        public int getItemCount() {
//            return this.itemList.size();
//        }
//
//        //Define your Interface method here
//        public interface ItemClickListener {
//            void onItemClick(int position);
//        }



    }

}
