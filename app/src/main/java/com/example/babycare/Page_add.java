package com.example.babycare;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.Menu_Record.Mommenu.AddmenumomActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Page_add  extends AppCompatActivity {
    FirebaseUser firebaseUser;
    TextView text_user_id;
    String userID;
///////////////////////////////////////
DatabaseReference mDatabase;
/////////////////////////////
//////////////////////
Date today = Calendar.getInstance().getTime();//getting date

    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter = new SimpleDateFormat("dd / MM / yyyy ");
    //formating according to my need
    String date = formatter.format(today);

    TextView t_date;
    ////////////////////

    EditText ed_name;
    EditText ed_calories;
    EditText ed_protein;
    EditText ed_vitamina;
    EditText ed_vitaminc;
    EditText ed_vitaminb6;
    EditText ed_vitaminb12;
    EditText ed_calcium;
    EditText ed_iodine;
    EditText ed_iron;


    /////////////

    Button b_action_1;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_add);


        // firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
//        intent = getIntent();
//        userID = intent.getStringExtra("id");


        text_user_id = (TextView) findViewById(R.id.text_user_id);

        text_user_id.setText("" + userID);

        t_date = (TextView) findViewById(R.id.t_date);
        t_date.setText(date);


        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_calories = (EditText) findViewById(R.id.ed_calories);
        ed_protein = (EditText) findViewById(R.id.ed_protein);
        ed_vitamina = (EditText) findViewById(R.id.ed_vitamina);
        ed_vitaminc = (EditText) findViewById(R.id.ed_vitaminc);
        ed_vitaminb6 = (EditText) findViewById(R.id.ed_vitaminb6);
        ed_vitaminb12 = (EditText) findViewById(R.id.ed_vitaminb12);
        ed_calcium = (EditText) findViewById(R.id.ed_calcium);
        ed_iodine = (EditText) findViewById(R.id.ed_iodine);
        ed_iron = (EditText) findViewById(R.id.ed_iron);

        ///////////////////////////////////////////////////////////////////

      mDatabase= FirebaseDatabase.getInstance("https://babycare-87875.firebaseio.com").getReference().child("food");


        Button b_action_1 = (Button) findViewById(R.id.b_action_1);
        b_action_1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {

               insertInformation();


            }

        });

       // b_action_1 = (Button)findViewById(R.id.b_action_1);



  }


    private void insertInformation() {


        Toast.makeText(Page_add.this, "Data inserted", Toast.LENGTH_SHORT).show();

                final String s_name = ed_name.getText().toString();
                final String s_calories = ed_calories.getText().toString();

        final String s_protein = ed_protein.getText().toString();
        final String s_vitamina = ed_vitamina.getText().toString();
        final String s_vitaminc = ed_vitaminc.getText().toString();
        final String s_vitaminb6 = ed_vitaminb6.getText().toString();
        final String s_vitaminb12 = ed_vitaminb12.getText().toString();
        final String s_calcium = ed_calcium.getText().toString();
        final String s_iodine = ed_iodine.getText().toString();
        final String s_iron = ed_iron.getText().toString();


                //final String s_date = "22 / 05 / 2019";
               // final String s_date = t_date.getText().toString();

               // final String s_quantity = ed_quantity.getText().toString();
               // final String s_id= text_user_id.getText().toString();




                mDatabase.orderByChild("name").equalTo(s_name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            Toast.makeText(Page_detail.this, "Entered information already exist", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
                        //Toast.makeText(InsertActivity.this, "Entered information is not exist", Toast.LENGTH_SHORT).show();
                        HashMap<String,String> datamap= new HashMap<String, String>();


                        datamap.put("name",s_name);
                        datamap.put("calories",s_calories);
                       datamap.put("protein",s_protein);
                       datamap.put("vitamina",s_vitamina);
                       datamap.put("vitaminc",s_vitaminc);
                        datamap.put("vitaminb6",s_vitaminb6);
                        datamap.put("vitaminb12",s_vitaminb12);
                        datamap.put("calcium",s_calcium);
                        datamap.put("iodine",s_iodine);
                        datamap.put("iron",s_iron);




                        mDatabase.push().setValue(datamap);
                        Toast.makeText(Page_add.this, "Data inserted", Toast.LENGTH_SHORT).show();
                        //  }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

//        b_action_1.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            reference = FirebaseDatabase.getInstance().getReference();
//            HashMap<String, String> hashMap = new HashMap<>();
//          //  hashMap.put("id", firebaseUser.getUid());
//            hashMap.put("Name", ed_name.getText().toString());
//            hashMap.put("Cal", ed_calories.getText().toString());
//
//            reference.child("food").push().setValue(hashMap);
//
//
//            ///////////////////////////////////////////////
//
//
//
//            final String s_name = ed_name.getText().toString();
//            final String s_calories = ed_calories.getText().toString();
//
//
//
//
//            mDatabase.orderByChild("Name").equalTo(s_name).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
////                        if(dataSnapshot.exists()){
////                            Toast.makeText(Page_detail.this, "Entered information already exist", Toast.LENGTH_SHORT).show();
////                        }
////                        else{
//                    //Toast.makeText(InsertActivity.this, "Entered information is not exist", Toast.LENGTH_SHORT).show();
//                    HashMap<String,String> datamap= new HashMap<String, String>();
//
//
//                    datamap.put("Name",s_name);
//                    datamap.put("Cal",s_calories);
//
//
//
//                    mDatabase.push().setValue(datamap);
//                    Toast.makeText(Page_add.this, "Data inserted", Toast.LENGTH_SHORT).show();
//                    //  }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//
//
/////////////////////////////////////////////////////////////////
//
//        }
//      });

}




