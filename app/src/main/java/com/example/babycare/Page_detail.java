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

public class Page_detail  extends AppCompatActivity {
    FirebaseUser firebaseUser;

    DatabaseReference reference;

    TextView text_1;
    TextView text_2;
    TextView text_3;
    TextView text_4;
    TextView text_5;
    TextView text_6;
    TextView text_7;
    TextView text_8;
    TextView text_9;
    TextView text_10;

    String userID;
////////////////////////////////////////////////////////
TextView text_user_id;

    DatabaseReference mDatabase;
    DatabaseReference mUserName;

    private Button save;

    //////////////////////
    Date today = Calendar.getInstance().getTime();//getting date

    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter = new SimpleDateFormat("dd / MM / yyyy ");
    //formating according to my need
    String date = formatter.format(today);

    TextView t_date;
    ////////////////////
    EditText text_quantity;
    EditText text_id;

    /////////////

    private TextView txtPassword;
    // private ClipBoardHelper helper;
    private PasswordGenerator generator;

/////////////////////////////////////
    private BubbleSeekBar bubbleSeekBar;
    private TextView persen;
    private int value =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_detail);

        Button b_action_1 = (Button) findViewById(R.id.b_action_1);
        b_action_1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {

                Intent intent = new Intent(V.getContext(), RecordActivity.class);
                startActivityForResult(intent, 0);


            }

        });

       // firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
//        intent = getIntent();
//        userID = intent.getStringExtra("id");


        text_user_id = (TextView) findViewById(R.id.text_user_id);

        text_user_id.setText(""+userID);






        ///////วันที่ ณ ป ัจจุบัน
        t_date = (TextView) findViewById(R.id.t_date);
        t_date.setText(date);






        mDatabase= FirebaseDatabase.getInstance("https://babycare-87875.firebaseio.com").getReference().child("Calculators");
        //  mUserName= (DatabaseReference) mDatabase.child("Info").child("Name");




        text_1 = (TextView) findViewById(R.id.text_1);
        text_2 = (TextView) findViewById(R.id.text_2);
        text_3 = (TextView) findViewById(R.id.text_3);
        text_4 = (TextView) findViewById(R.id.text_4);
        text_5 = (TextView) findViewById(R.id.text_5);
        text_6 = (TextView) findViewById(R.id.text_6);
        text_7 = (TextView) findViewById(R.id.text_7);
        text_8 = (TextView) findViewById(R.id.text_8);
        text_9 = (TextView) findViewById(R.id.text_9);
        text_10 = (TextView) findViewById(R.id.text_10);


        text_quantity = (EditText) findViewById(R.id.text_quantity);
        text_id = (EditText) findViewById(R.id.text_id);
        ////////////////////////////////////////////////////////////////////////////
        //helper = new ClipBoardHelper(this);
        generator = new PasswordGenerator();
        text_id.setText(generator.generate(20));
////////////////////////////////////////////////////////////////////////////////


        // Random generator = new Random();
        // String s_aaa = String.valueOf (generator.nextInt(96) + 32);


        //  text_id.setText(""+s_aaa);


        final Intent intent = getIntent();// getStringExtra form recycle adapter

        String s_text_1 = intent.getStringExtra("s_text_1");
        String s_text_2 = intent.getStringExtra("s_text_2");
        String s_text_3 = intent.getStringExtra("s_text_3");
        String s_text_4 = intent.getStringExtra("s_text_4");
        String s_text_5 = intent.getStringExtra("s_text_5");
        String s_text_6 = intent.getStringExtra("s_text_6");
        String s_text_7 = intent.getStringExtra("s_text_7");
        String s_text_8 = intent.getStringExtra("s_text_8");
        String s_text_9 = intent.getStringExtra("s_text_9");
        String s_text_10 = intent.getStringExtra("s_text_10");

        text_1.setText(""+s_text_1);
        text_2.setText(""+s_text_2);
        text_3.setText(""+s_text_3);
        text_4.setText(""+s_text_4);
        text_5.setText(""+s_text_5);
        text_6.setText(""+s_text_6);
        text_7.setText(""+s_text_7);
        text_8.setText(""+s_text_8);
        text_9.setText(""+s_text_9);
        text_10.setText(""+s_text_10);


        save=(Button)findViewById(R.id.save);
        insertInformation();



//////////////////////////////////////////////////////////////////////////



        ////////////////////////////////////////////

        bubbleSeekBar = (BubbleSeekBar) findViewById(R.id.seekbar);
      //  persen = (TextView)findViewById(R.id.textview_persen);




        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                //text_quantity.setText(""+progress+"%");
                text_quantity.setText(""+progress);
                value = progress;

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });



    }


    private void insertInformation() {

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String s_name = text_1.getText().toString();
                final String s_calories = text_2.getText().toString();
                final String s_protein = text_3.getText().toString();
                final String s_vitamina = text_4.getText().toString();
                final String s_vitaminc = text_5.getText().toString();
                final String s_vitaminb6 = text_6.getText().toString();
                final String s_vitaminb12 = text_7.getText().toString();
                final String s_calcium = text_8.getText().toString();
                final String s_iodine = text_9.getText().toString();
                final String s_iron = text_10.getText().toString();



                //final String s_date = "22 / 05 / 2019";
                final String s_date = t_date.getText().toString();
                final String s_quantity = text_quantity.getText().toString();
                final String s_id= text_user_id.getText().toString();




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
                        datamap.put("date",s_date);
                        datamap.put("quantity",s_quantity);
                        datamap.put("id",s_id);

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
                        Toast.makeText(Page_detail.this, "Data inserted", Toast.LENGTH_SHORT).show();
                        //  }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                /*

                HashMap<String,String>datamap= new HashMap<String, String>();


                datamap.put("Name",userName);
                datamap.put("Email",userEmail);
                datamap.put("phone",userPhone);
                mDatabase.push().setValue(datamap);
                Toast.makeText(InsertActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
*/





            }
        });
    }



}



