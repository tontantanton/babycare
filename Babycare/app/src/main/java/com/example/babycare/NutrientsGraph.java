package com.example.babycare;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.Calculator.CalToday;
import com.example.babycare.Calculator.CalTodayAdapter;
import com.example.babycare.Calculator.FragmentCaltoday;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xw.repo.BubbleSeekBar;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class NutrientsGraph  extends AppCompatActivity {

    ////////////////////////////////////////////////////////
    FirebaseUser firebaseUser;

    String userID;
    TextView text_user_id;
    TextView txtsumcal;
    TextView date;
    ////////////////////////////////////////////////////
///////////////////////////////////////////////////
    //////////////////////////////////////////////////////

    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    List<Model_1> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;

    String Database_Path = "Calculators";

    ///////////////////////////////


    String strsum;
    String strDate;
    float quannum;


    float sum;


    float protein;
    float calories;
    float calcium;
    float iodine;
    float iron;
    float vitamina;
    float vitaminb6;
    float vitaminb12;
    float vitaminc;


    float result_calories;
    float result_protein;
    float result_calcium;
    float result_iodine;
    float result_iron;
    float result_vitamina;
    float result_vitaminb6;
    float result_vitaminb12;
    float result_vitaminc;

    String s_result_f_calories;
    String s_result_f_protein;
    String s_result_f_calcium;
    String s_result_f_iodine;
    String s_result_f_iron;
    String s_result_f_vitamina;
    String s_result_f_vitaminb6;
    String s_result_f_vitaminb12;
    String s_result_f_vitaminc;

   // TextView date;

    TextView text_calories;
    TextView text_protein;
    TextView text_calcium;
    TextView text_iodine;
    TextView text_iron;
    TextView text_vitamina;
    TextView text_vitaminb6;
    TextView text_vitaminb12;
    TextView text_vitaminc;
    /////////////////////////////////////////////////////////
    /////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrients_graph);






        // firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
//        intent = getIntent();
//        userID = intent.getStringExtra("id");


       // text_user_id = (TextView) findViewById(R.id.text_user_id);

       // txtsumcal = (TextView) findViewById(R.id.txtsumcal);

       // mcaltoday = (TextView) findViewById(R.id.mcaltoday);

       // date = (TextView) findViewById(R.id.date);


       // text_user_id.setText(""+userID);



        ///////////////////////////////////////
        /////////////////////////////////////////////
        ////////////////////////////////////////////////

        text_calories = (TextView) findViewById(R.id.text_calories);

        text_protein = (TextView) findViewById(R.id.text_protein);

        text_calcium = (TextView) findViewById(R.id.text_calcium);

        text_iodine = (TextView) findViewById(R.id.text_iodine);

        text_iron = (TextView) findViewById(R.id.text_iron);

        text_vitamina = (TextView) findViewById(R.id.text_vitamina);

        text_vitaminb6 = (TextView) findViewById(R.id.text_vitaminb6);

        text_vitaminb12 = (TextView) findViewById(R.id.text_vitaminb12);

        text_vitaminc = (TextView) findViewById(R.id.text_vitaminc);

        date = (TextView) findViewById(R.id.date);


        //Button
        Button gotoNext1 = (Button) findViewById(R.id.gotoNext1);
        gotoNext1.setOnClickListener (new View.OnClickListener() {

            public void onClick(View  V) {


                Intent intent = new Intent(V.getContext(), Page_new.class);
                intent.putExtra("s_text_calories", text_calories.getText().toString().trim());
                intent.putExtra("s_text_protein", text_protein.getText().toString().trim());
                intent.putExtra("s_text_calcium", text_calcium.getText().toString().trim());
                intent.putExtra("s_text_iodine", text_iodine.getText().toString().trim());
                intent.putExtra("s_text_iron", text_iron.getText().toString().trim());
                intent.putExtra("s_text_vitamina", text_vitamina.getText().toString().trim());
                intent.putExtra("s_text_vitaminb6", text_vitaminb6.getText().toString().trim());
                intent.putExtra("s_text_vitaminb12", text_vitaminb12.getText().toString().trim());
                intent.putExtra("s_text_vitaminc", text_vitaminc.getText().toString().trim());

                startActivityForResult(intent, 0);

            }

        });//Button


        //databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(NutrientsGraph.this));

        //  progressDialog = new ProgressDialog(Page_listview_300.this);

        //  progressDialog.setMessage("Loading Data from Firebase Database");

        //   progressDialog.show();

        //databaseReference = FirebaseDatabase.getInstance().ge+tReference(MainActivity.Database_Path);
        //databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        //private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://babycare-87875.firebaseio.com/");
        databaseReference = FirebaseDatabase.getInstance("https://babycare-87875.firebaseio.com").getReference(Database_Path);




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Model_1 model_1 = dataSnapshot.getValue(Model_1.class);

                    list.add(model_1);
                }

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
                strDate = mdformat.format(calendar.getTime());
                date.setText(strDate);


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model_1 calToday = dataSnapshot.getValue(Model_1.class);
//                    Log.w("", "name====" + myid);
//                    Log.d("", "calid---" + calToday.getId());
//                    Log.d("", "name---" + calToday.getName());

//                    assert calToday != null;
                    //if (calToday.getId().equals(myid) && calToday.getDate().equals(strDate)) {
                    if (calToday.getId().equals(userID) && calToday.getDate().equals(strDate)) {
                        //calnum = Float.parseFloat(calToday.getCalories());
                        calories = Float.parseFloat(calToday.getCalories());
                        quannum = Float.parseFloat(calToday.getQuantity());
                        sum = (protein * quannum) / 100;
                        strsum = String.valueOf(sum);
                        result_calories += sum;
//                        Log.d("", "test----" + calnum + "*" + quannum);
                        calToday.setCalories(strsum);
                        //   mcaltoday.add(calToday);

                    }
                    if (calToday.getId().equals(userID) && calToday.getDate().equals(strDate)) {
                        //calnum = Float.parseFloat(calToday.getCalories());
                        protein = Float.parseFloat(calToday.getProtein());
                        quannum = Float.parseFloat(calToday.getQuantity());
                        sum = (protein * quannum) / 100;
                        strsum = String.valueOf(sum);
                        result_protein += sum;
//                        Log.d("", "test----" + calnum + "*" + quannum);
                        calToday.setProtein(strsum);
                        //   mcaltoday.add(calToday);

                    }
                    if (calToday.getId().equals(userID) && calToday.getDate().equals(strDate)) {
                        //calnum = Float.parseFloat(calToday.getCalories());
                        calcium = Float.parseFloat(calToday.getCalcium());
                        quannum = Float.parseFloat(calToday.getQuantity());
                        sum = (calcium * quannum) / 100;
                        strsum = String.valueOf(sum);
                        result_calcium += sum;
//                        Log.d("", "test----" + calnum + "*" + quannum);
                        calToday.setCalcium(strsum);
                        //   mcaltoday.add(calToday);

                    }
                    if (calToday.getId().equals(userID) && calToday.getDate().equals(strDate)) {
                        //calnum = Float.parseFloat(calToday.getCalories());
                        iodine = Float.parseFloat(calToday.getIodine());
                        quannum = Float.parseFloat(calToday.getQuantity());
                        sum = (iodine * quannum) / 100;
                        strsum = String.valueOf(sum);
                        result_iodine += sum;
//                        Log.d("", "test----" + calnum + "*" + quannum);
                        calToday.setIodine(strsum);
                        //   mcaltoday.add(calToday);

                    }
                    if (calToday.getId().equals(userID) && calToday.getDate().equals(strDate)) {
                        //calnum = Float.parseFloat(calToday.getCalories());
                        iron = Float.parseFloat(calToday.getIron());
                        quannum = Float.parseFloat(calToday.getQuantity());
                        sum = (iron * quannum) / 100;
                        strsum = String.valueOf(sum);
                        result_iron += sum;
//                        Log.d("", "test----" + calnum + "*" + quannum);
                        calToday.setIron(strsum);
                        //   mcaltoday.add(calToday);

                    }
                    if (calToday.getId().equals(userID) && calToday.getDate().equals(strDate)) {
                        //calnum = Float.parseFloat(calToday.getCalories());
                        vitamina = Float.parseFloat(calToday.getVitamina());
                        quannum = Float.parseFloat(calToday.getQuantity());
                        sum = (vitamina * quannum) / 100;
                        strsum = String.valueOf(sum);
                        result_vitamina+= sum;
//                        Log.d("", "test----" + calnum + "*" + quannum);
                        calToday.setVitamina(strsum);
                        //   mcaltoday.add(calToday);

                    }
                    if (calToday.getId().equals(userID) && calToday.getDate().equals(strDate)) {
                        //calnum = Float.parseFloat(calToday.getCalories());
                        vitaminb6 = Float.parseFloat(calToday.getVitaminb6());
                        quannum = Float.parseFloat(calToday.getQuantity());
                        sum = (vitaminb6 * quannum) / 100;
                        strsum = String.valueOf(sum);
                        result_vitaminb6+= sum;
//                        Log.d("", "test----" + calnum + "*" + quannum);
                        calToday.setVitaminb6(strsum);
                        //   mcaltoday.add(calToday);

                    }
                    if (calToday.getId().equals(userID) && calToday.getDate().equals(strDate)) {
                        //calnum = Float.parseFloat(calToday.getCalories());
                        vitaminb12 = Float.parseFloat(calToday.getVitaminb12());
                        quannum = Float.parseFloat(calToday.getQuantity());
                        sum = (vitaminb12 * quannum) / 100;
                        strsum = String.valueOf(sum);
                        result_vitaminb12+= sum;
//                        Log.d("", "test----" + calnum + "*" + quannum);
                        calToday.setVitaminb12(strsum);
                        //   mcaltoday.add(calToday);

                    }
                    if (calToday.getId().equals(userID) && calToday.getDate().equals(strDate)) {
                        //calnum = Float.parseFloat(calToday.getCalories());
                        vitaminc = Float.parseFloat(calToday.getVitaminc());
                        quannum = Float.parseFloat(calToday.getQuantity());
                        sum = (vitaminc * quannum) / 100;
                        strsum = String.valueOf(sum);
                        result_vitaminc+= sum;
//                        Log.d("", "test----" + calnum + "*" + quannum);
                        calToday.setVitaminc(strsum);
                        //   mcaltoday.add(calToday);

                    }
                    /////////////////////////////////////////////////////////////////////
                    DecimalFormat df = new DecimalFormat("0.00");
                    df.setRoundingMode(RoundingMode.CEILING.HALF_UP);

                    double f_calories = Double.parseDouble(df.format(result_calories));
                    double f_protein = Double.parseDouble(df.format(result_protein));
                    double f_calcium = Double.parseDouble(df.format(result_calcium));
                    double f_iodine = Double.parseDouble(df.format(result_iodine));
                    double f_iron = Double.parseDouble(df.format(result_iron));
                    double f_vitamina = Double.parseDouble(df.format(result_vitamina));
                    double f_vitaminb6 = Double.parseDouble(df.format(result_vitaminb6));
                    double f_vitaminb12 = Double.parseDouble(df.format(result_vitaminb12));
                    double f_vitaminc = Double.parseDouble(df.format(result_vitaminc));
                    //////////////////////////////////////////////////////////////////////////////
                    s_result_f_calories = String.valueOf(f_calories);
                    text_calories.setText(s_result_f_calories);


                    s_result_f_protein = String.valueOf(f_protein);
                    text_protein.setText(s_result_f_protein);


                    s_result_f_calcium = String.valueOf(f_calcium);
                    text_calcium.setText(s_result_f_calcium);


                    s_result_f_iodine = String.valueOf(f_iodine);
                    text_iodine.setText(s_result_f_iodine);


                    s_result_f_iron = String.valueOf(f_iron);
                    text_iron.setText(s_result_f_iron);

                    s_result_f_vitamina = String.valueOf(f_vitamina);
                    text_vitamina.setText(s_result_f_vitamina);

                    s_result_f_vitaminb6 = String.valueOf(f_vitaminb6);
                    text_vitaminb6.setText(s_result_f_vitaminb6);

                    s_result_f_vitaminb12 = String.valueOf(f_vitaminb12);
                    text_vitaminb12.setText(s_result_f_vitaminb12);

                    s_result_f_vitaminc = String.valueOf(f_vitaminc);
                    text_vitaminc.setText(s_result_f_vitaminc);
                    ///////////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////////////////////////////////////
                    adapter = new Adapter_1(NutrientsGraph.this, list);

                    recyclerView.setAdapter(adapter);

                    // progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                //  progressDialog.dismiss();

            }
        });

    }
}
