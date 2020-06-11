package com.example.babycare;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DialogActivity_1 extends Activity implements View.OnClickListener {
    Button b_ok;
    Button b_cancel;
    TextView tv_name;
    TextView tv_calories;
    TextView tv_calcium;
    TextView tv_protein;
    TextView tv_vitamina;
    TextView tv_vitaminc;
    TextView tv_vitaminb6;
    TextView tv_vitaminb12;
    TextView tv_iodine;
    TextView tv_iron;


    ///////////////
    DatabaseReference reference;
    /////////////
    String s_name;
    String s_calories;
    String s_protein;
    String s_vitamina;
    String s_vitaminc;
    String s_vitaminb6;
    String s_vitaminb12;
    String s_calcium;
    String s_iodine;
    String s_iron;

    /////////////

    //////////////////////
    Date today = Calendar.getInstance().getTime();//getting date

    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter = new SimpleDateFormat("dd / MM / yyyy ");
    //formating according to my need
    String date = formatter.format(today);

    TextView t_date;
    ////////////////////
    ////////////////////////////
    FirebaseUser firebaseUser;


    String userID;
    //////////////////////
    private BubbleSeekBar bubbleSeekBar;
    private TextView text_quantity;
    private int value =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogactivity_1);

      firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
       userID = firebaseUser.getUid();


    text_quantity = (EditText) findViewById(R.id.text_quantity);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_calories = (TextView) findViewById(R.id.tv_calories);
        tv_calcium = (TextView) findViewById(R.id.tv_calcium);
        tv_protein = (TextView) findViewById(R.id.tv_protein);
        tv_vitamina = (TextView) findViewById(R.id.tv_vitamina);
        tv_vitaminc = (TextView) findViewById(R.id.tv_vitaminc);
        tv_vitaminb6 = (TextView) findViewById(R.id.tv_vitaminb6);
        tv_vitaminb12 = (TextView) findViewById(R.id.tv_vitaminb12);
        tv_iodine = (TextView) findViewById(R.id.tv_iodine);
        tv_iron = (TextView) findViewById(R.id.tv_iron);

        final Intent intent = getIntent();

//        String s_name= intent.getStringExtra("name");
//        String s_calories= intent.getStringExtra("calories");
//        String s_calcium= intent.getStringExtra("calcium");
//        String s_protein= intent.getStringExtra("protein");
//        String s_vitamina= intent.getStringExtra("vitamina");
//        String s_vitaminc= intent.getStringExtra("vitaminc");
//        String s_vitaminb6= intent.getStringExtra("vitaminb6");
//        String s_vitaminb12= intent.getStringExtra("vitaminb12");
//        String s_iodine= intent.getStringExtra("iodine");
//        String s_iron= intent.getStringExtra("iron");

        s_name= intent.getStringExtra("name");
        s_calories= intent.getStringExtra("calories");
         s_calcium= intent.getStringExtra("calcium");
        s_protein= intent.getStringExtra("protein");
         s_vitamina= intent.getStringExtra("vitamina");
        s_vitaminc= intent.getStringExtra("vitaminc");
       s_vitaminb6= intent.getStringExtra("vitaminb6");
        s_vitaminb12= intent.getStringExtra("vitaminb12");
       s_iodine= intent.getStringExtra("iodine");
        s_iron= intent.getStringExtra("iron");


        tv_name.setText("" + s_name);
        tv_calories.setText("" + s_calories);
        tv_calcium.setText("" + s_calcium);
        tv_protein.setText("" + s_protein);
        tv_vitamina.setText("" + s_vitamina);
        tv_vitaminc.setText("" + s_vitaminc);
        tv_vitaminb6.setText("" + s_vitaminb6);
        tv_vitaminb12.setText("" + s_vitaminb12);
        tv_iodine.setText("" + s_iodine);
        tv_iron.setText("" + s_iron);





        b_ok = (Button) findViewById(R.id.b_ok);
        b_cancel = (Button) findViewById(R.id.b_cancel);

        b_ok.setOnClickListener(this);
        b_cancel.setOnClickListener(this);

        bubbleSeekBar = (BubbleSeekBar) findViewById(R.id.seekbar);

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
   // private BubbleSeekBar bubbleSeekBar;
   // private String numper;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_ok:

                showToastMessage("Ok");



               final String s_quantity = text_quantity.getText().toString();


                //Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
                // Toast.makeText(DialogActivity_1.this, "" + result +("บาท"), Toast.LENGTH_LONG).show();


                reference = FirebaseDatabase.getInstance().getReference();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", userID);
                hashMap.put("name", s_name);
                hashMap.put("calories", s_calories);
                hashMap.put("protein", s_protein);
                hashMap.put("vitamina", s_vitamina);
                hashMap.put("vitaminc",s_vitaminc);
                hashMap.put("vitaminb6", s_vitaminb6);
                hashMap.put("vitaminb12", s_vitaminb12);
                hashMap.put("calcium", s_calcium);
                hashMap.put("iodine", s_iodine);
                hashMap.put("iron", s_iron);
                hashMap.put("date", date);
                hashMap.put("quantity", s_quantity);

                reference.child("Calculators").push().setValue(hashMap);

               // automenumom.getText().clear();









                this.finish();

                break;

            case R.id.b_cancel:

                showToastMessage("Cancel");
                this.finish();

                break;
        }

    }

    void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}