package com.example.babycare;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Page_dessert  extends AppCompatActivity {
    FirebaseUser firebaseUser;


    String userID;
    ////////////////////////////////////////////////////////
    TextView text_user_id;
    ////////////////////////
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeRef = mRootRef.child("Catagories2/Dessert");




    ListView listView;
    List<Model_dessert> rowItems;
   // ArrayList<String> searchedIngredients;
    private static final String TAG = "Chiz";


    String s_name;
    String s_calories;
    String s_protein;
    String s_calcium;
    String s_vitamina;
    String s_vitaminc;
    String s_vitaminb6;
    String s_vitaminb12;
    String s_iodine;
    String s_iron;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_dessert);

        // firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
//        intent = getIntent();
//        userID = intent.getStringExtra("id");


       listView = (ListView) findViewById(R.id.recipe_list);
        text_user_id = (TextView) findViewById(R.id.text_user_id);

        text_user_id.setText("" + userID);

        this.getData();
    }

    public void getData() {
        mRecipeRef.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rowItems = new ArrayList<Model_dessert>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final String recipeKey = postSnapshot.getKey();
                    final String name = postSnapshot.child("name").getValue(String.class);
                   final String calories = postSnapshot.child("calories").getValue(String.class);
                    final String protein = postSnapshot.child("protein").getValue(String.class);
                    final String vitamina = postSnapshot.child("vitamina").getValue(String.class);
                    final String vitaminc = postSnapshot.child("vitaminc").getValue(String.class);
                    final String vitaminb6 = postSnapshot.child("vitaminb6").getValue(String.class);
                    final String vitaminb12 = postSnapshot.child("vitaminb12").getValue(String.class);
                    final String calcium = postSnapshot.child("calcium").getValue(String.class);
                    final String iodine = postSnapshot.child("iodine").getValue(String.class);
                    final String iron = postSnapshot.child("iron").getValue(String.class);


                    Model_dessert  item = new Model_dessert(recipeKey, name, calories, protein, vitamina, vitaminc, vitaminb6, vitaminb12, calcium
                            , iodine, iron);
                    rowItems.add(item);

                    Adapter_dessert adapter = new Adapter_dessert(getApplicationContext(), rowItems);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                            TextView text_name = (TextView) view.findViewById(R.id.text_name);
                            TextView text_calories = (TextView) view.findViewById(R.id.text_calories);
                            TextView text_calcium = (TextView) view.findViewById(R.id.text_calcium);
                            TextView text_protein = (TextView) view.findViewById(R.id.text_protein);
                            TextView text_vitamina = (TextView) view.findViewById(R.id.text_vitamina);
                            TextView text_vitaminc = (TextView) view.findViewById(R.id.text_vitaminc);
                            TextView text_vitaminb6 = (TextView) view.findViewById(R.id.text_vitaminb6);
                            TextView text_vitaminb12 = (TextView) view.findViewById(R.id.text_vitaminb12);
                            TextView text_iodine = (TextView) view.findViewById(R.id.text_iodine);
                            TextView text_iron = (TextView) view.findViewById(R.id.text_iron);

                            s_name = text_name.getText().toString().trim();
                            s_calories = text_calories.getText().toString().trim();
                            s_calcium = text_calcium.getText().toString().trim();
                            s_protein = text_protein.getText().toString().trim();
                            s_vitamina = text_vitamina.getText().toString().trim();
                            s_vitaminc = text_vitaminc.getText().toString().trim();
                            s_vitaminb6 = text_vitaminb6.getText().toString().trim();
                            s_vitaminb12 = text_vitaminb12.getText().toString().trim();
                            s_iodine = text_iodine.getText().toString().trim();
                            s_iron = text_iron.getText().toString().trim();

                             Intent newActivity = new Intent(Page_dessert.this, DialogActivity_1.class);
                             newActivity.putExtra("name", s_name);
                            newActivity.putExtra("calories", s_calories);
                            newActivity.putExtra("calcium", s_calcium);
                            newActivity.putExtra("protein", s_protein);
                            newActivity.putExtra("vitamina", s_vitamina);
                            newActivity.putExtra("vitaminc", s_vitaminc);
                            newActivity.putExtra("vitaminb6", s_vitaminb6);
                            newActivity.putExtra("vitaminb12", s_vitaminb12);
                            newActivity.putExtra("iodine", s_iodine);
                            newActivity.putExtra("iron", s_iron);

                           startActivity(newActivity);



                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
             Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

    }
}
