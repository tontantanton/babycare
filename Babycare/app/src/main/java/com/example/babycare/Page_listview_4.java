package com.example.babycare;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Page_listview_4 extends AppCompatActivity {

    //private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ble-1-2300e.firebaseio.com/");
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://babycare-87875.firebaseio.com/");


    private final List<Data> dataList = new ArrayList<>();
    EditText pop;

    private boolean watch = true;

    TextView text_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_listview_4);



       // text_1 = (TextView) findViewById(R.id.text_1);


        final Intent intent = getIntent();
        String s_text_1 = intent.getStringExtra("s_text_1");
        //text_1.setText(""+s_text_1);


        final Button nothing = findViewById(R.id.nothing);
        pop = findViewById(R.id.pop);
        pop.setText(""+s_text_1);
        monitorSearch();

        nothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.size() == 0) {
                    firebaseDatabase.getReference().child("food").addValueEventListener(new ValueEventListener() {
                        // firebaseDatabase.getReference().child("Calculators").addValueEventListener(new ValueEventListener() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                // String location = data.child("userLocation").getValue(String.class);
                                //  String name = data.child("Name").getValue(String.class);
                                // String phone = data.child("userNumber").getValue(String.class);
                                //  String profession = data.child("userProfession").getValue(String.class);
                                //String address = data.child("userAddress").getValue(String.class);
                                // String description = data.child("userDescription").getValue(String.class);
                                //  Data data1 = new Data(name, phone, profession, location, address, description);

                                // String name = data.child("Name").getValue(String.class);
                                //String lastname = data.child("lastName").getValue(String.class);
                               // String name = data.child("Name").getValue(String.class);
                               // String calories = data.child("Cal").getValue(String.class);
                                String name = data.child("name").getValue(String.class);
                                String calories = data.child("calories").getValue(String.class);
                                String protein = data.child("protein").getValue(String.class);
                                String vitamina = data.child("vitamina").getValue(String.class);
                                String vitaminc = data.child("vitaminc").getValue(String.class);
                                String vitaminb6 = data.child("vitaminb6").getValue(String.class);
                                String vitaminb12 = data.child("vitaminb12").getValue(String.class);
                                String calcium = data.child("calcium").getValue(String.class);
                                String iodine = data.child("iodine").getValue(String.class);
                                String iron = data.child("iron").getValue(String.class);


                                //Data data1 = new Data(name);
                                Data data1 = new Data(name,calories,protein,vitamina,vitaminc,vitaminb6,vitaminb12,calcium,iodine,iron);
                                dataList.add(data1);

                                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Page_listview_4.this));
                                recyclerView.setAdapter(new RecyclerAdapter(filterList())); // send to recycle adapter
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Toast.makeText(Page_listview_4.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    RecyclerView recyclerView = findViewById(R.id.recyclerview);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Page_listview_4.this));
                    recyclerView.setAdapter(new RecyclerAdapter(filterList()));
                }
            }
        });
    }

    private List<Data> filterList() {
        String profession = "";
        boolean postToProfession = true;
        String datanew = "";
        for (int i = 0; i < pop.getText().toString().length(); i++) {
            if (postToProfession) {
                if (pop.getText().toString().charAt(i) != ' ') {
                    profession += Character.toString(pop.getText().toString().charAt(i));
                } else {
                    postToProfession = false;
                }
            } else {
                datanew += Character.toString(pop.getText().toString().charAt(i));
            }
        }

        List<Data> list = new ArrayList<>();
//        boolean noData = true;
//        for (Data data : dataList) {
//            if (data.getLocation().toLowerCase().contains(location.toLowerCase()) || location.toLowerCase().contains(data.getLocation().toLowerCase())) {
//                if (profession.toLowerCase().contains(data.getProfession().toLowerCase()) || data.getProfession().toLowerCase().contains(profession.toLowerCase())) {
//                    list.add(data);
//                    noData = false;
//                }
//            }
//        }
        boolean noData = true;
        for (Data data : dataList) {
            if (data.getName().toLowerCase().contains(datanew.toLowerCase()) ) {
                if (profession.toLowerCase().contains(data.getName().toLowerCase())) {
                    list.add(data); //โหลดตาดาเบสให้แสดงในลิส
                    noData = false;
                }
            }
        }

        if (noData) {
            // Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
        }

        return list;
    }

    private void monitorSearch() {
        pop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (watch) {
                    if (s.toString().contains(" ")) {
                        watch = false;
                        pop.setText(pop.getText().toString().trim() + " In ");
                    }
                } else {
                    if (!s.toString().contains(" ")) {
                        watch = true;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}

