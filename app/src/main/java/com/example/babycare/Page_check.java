package com.example.babycare;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Page_check extends AppCompatActivity {

    private EditText editTextName;
    private Button checkData;
    DatabaseReference reference;
    private TextView retrieveName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_check);


        editTextName = (EditText) findViewById(R.id.check);
        checkData = (Button) findViewById(R.id.data_check);
        retrieveName = (TextView) findViewById(R.id.name_search);

        //reference = FirebaseDatabase.getInstance().getReference().child("food");
        reference = FirebaseDatabase.getInstance("https://babycare-87875.firebaseio.com").getReference().child("food");

        Button b_action_1 = (Button) findViewById(R.id.b_action_1);
        b_action_1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {


                onBackPressed();


            }

        });


        Button b_action_2 = (Button) findViewById(R.id.b_action_2);
        b_action_2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {


                // Intent intent = new Intent(V.getContext(), Pesquisa.class);
                Intent intent = new Intent(V.getContext(), Page_listview_4.class);
                intent.putExtra("s_text_1", retrieveName.getText().toString().trim());
                startActivityForResult(intent, 0);


            }

        });



        final Intent intent = getIntent();
        String s_text_1 = intent.getStringExtra("s_text_1");
        editTextName.setText("" + s_text_1);

        checkDataForExist();
    }

    private void checkDataForExist() {

        checkData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String checkName = editTextName.getText().toString();
                //  final String searchName=retrieveName.getText().toString();

                reference.orderByChild("name").equalTo(checkName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(Page_check.this, "Entered name is exist already", Toast.LENGTH_SHORT).show();
                            retrieveName.setText(checkName);


                        } else {
                            Toast.makeText(Page_check.this, "Entered name is not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

//    @Override
//    public void onBackPressed() {
////        backpress = (backpress + 1);
//       //Toast.makeText(getApplicationContext(), " no ", Toast.LENGTH_SHORT).show();
////
////        if (backpress > 1) {
////            this.finish();
////        }
//
////        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////               launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//
////        Intent intent = new Intent(Intent.ACTION_MAIN);
////        intent.addCategory(Intent.CATEGORY_HOME);
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        startActivity(intent);
//
//    }

    @Override
    public void onBackPressed() {
        //Intent intent=new Intent(currentclass.this,nextActivity.class);
        //startActivity(intent);
        finish();


    }
}
