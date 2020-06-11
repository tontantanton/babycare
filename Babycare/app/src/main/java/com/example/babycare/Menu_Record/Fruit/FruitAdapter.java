package com.example.babycare.Menu_Record.Fruit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.Menu_Record.Dessert.DessertAdapter;
import com.example.babycare.Menu_Record.Dessert.DessertMenu;
import com.example.babycare.R;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.Holder>{
    //    private String[] mDataSet;
    private List<FruitMenu> fruitMenus;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fruititem,viewGroup,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.setItem(i);
    }

    @Override
    public int getItemCount() {
        return fruitMenus.size();
    }


    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textTitle;
        TextView textDescription;
        ImageView add;

        public Holder(View itemView){
            super(itemView);
            add = itemView.findViewById(R.id.add);
            textTitle = itemView.findViewById(R.id.text_title_fruit);
            textDescription = itemView.findViewById(R.id.text_description_fruit);
            itemView.setOnClickListener(this);

        }

        public void setItem(int position) {
            textTitle.setText(fruitMenus.get(position).getName());
            textDescription.setText("ให้พลังงาน " + fruitMenus.get(position).getCalories()+" กิโลแคลอรี่");
        }

        @Override
        public void onClick(View view) {
            if(mListener!=null)
                mListener.onItemClick(getAdapterPosition());
        }


    }

    public FruitAdapter(List<FruitMenu>  fruit){
        fruitMenus = fruit;
    }
}
