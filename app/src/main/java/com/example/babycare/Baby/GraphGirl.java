package com.example.babycare.Baby;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.babycare.Model.User;
import com.example.babycare.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class GraphGirl extends AppCompatActivity implements OnChartValueSelectedListener {

    LineChart chart;

    CircleImageView image_profile;

    TextView txt_w, txt_h, userName, userBd;
    Button button,btn_edit;
    ImageButton back;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef,myRef;
    private String currentUserId;
    private String myweight, myheight, mygender,myusername,mybd, myname, myemail;
    private float weight, height;
    List<Graph> graphList = new ArrayList<>();


    ArrayList<ILineDataSet> dataSets;
    LineData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph_girl);



        back = (ImageButton)findViewById(R.id.btn_back_edit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GraphGirl.this, BabyActivity.class);
                startActivity(i);
            }
        });

        chart = findViewById(R.id.line_chart);
        chart.setOnChartValueSelectedListener(this);
        //chart.setDrawGridBackground(true);
        chart.setBackgroundColor(Color.WHITE);

        chart.setGridBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(true);

        chart.setDrawBorders(true);
        chart.getDescription().setEnabled(false);
        chart.setNoDataText("No chart data available. Use the menu to add entries and data sets!");

        chart.setPinchZoom(false);

        Legend l = chart.getLegend();
        l.setEnabled(false);



        LimitLine upper_limit = new LimitLine(30f, "อ้วน");
        upper_limit.setLineColor(Color.WHITE);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(30f);

        LimitLine center = new LimitLine(17f, "สมส่วน");
        center.setLineColor(Color.WHITE);
        center.enableDashedLine(10f, 10f, 0f);
        center.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        center.setTextSize(10f);

        LimitLine lower_limit = new LimitLine(1.7f, "ผอม");
        lower_limit.setLineColor(Color.WHITE);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        lower_limit.setTextSize(30f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMaximum(120);
        xAxis.setAxisMinimum(45);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaximum(35);
        leftAxis.setAxisMinimum(0);


// reset all limit lines to avoid overlapping lines
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(upper_limit);
        leftAxis.addLimitLine(lower_limit);

//leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

// limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        chart.getAxisRight().setEnabled(false);


        image_profile = findViewById(R.id.profile_image);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        userName = (TextView)findViewById(R.id.name);
        userBd = (TextView)findViewById(R.id.age);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        txt_w = (TextView)findViewById(R.id.txt_w);
        txt_h = (TextView)findViewById(R.id.txt_h);

        btn_edit = (Button)findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphGirl.this, EditProfile.class);
                //intent.putExtra("key",key);
                intent.putExtra("username",myusername);
                intent.putExtra("email",myemail);
                intent.putExtra("name",myname);
                intent.putExtra("birthday",mybd);
                intent.putExtra("weight",myweight);
                intent.putExtra("height",myheight);
                intent.putExtra("gender",mygender);

                startActivity(intent);
            }
        });



        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    myusername = dataSnapshot.child("username").getValue().toString();
                    myemail = dataSnapshot.child("email").getValue().toString();
                    myname = dataSnapshot.child("name").getValue().toString();
                    mybd = dataSnapshot.child("birthday").getValue().toString();
                    myweight = dataSnapshot.child("weight").getValue().toString();
                    myheight = dataSnapshot.child("height").getValue().toString();
                    mygender = dataSnapshot.child("gender").getValue().toString();

                    String b = mybd;
                    Log.d("", "bd==" + b);
                    String x[] = b.split("/");
                    Log.d("", "year=" + x.toString());
                    int year = Integer.parseInt(x[2]);
                    Log.d("", "year==" + year);
                    int month = Integer.parseInt(x[1]);
                    Log.d("", "month==" + month);
                    int day = Integer.parseInt(x[0]);
                    Log.d("", "day==" + day);


                    StringBuilder strBuild = new StringBuilder();
                    Calendar dateOfYourBirth = new GregorianCalendar(year, month, day);
                    int yourAge = Calendar.getInstance().get(Calendar.YEAR) - dateOfYourBirth.get(Calendar.YEAR);
                    int monthtoday = (Calendar.getInstance().get(Calendar.MONTH) + 1);
                    int yourAgeMonth = monthtoday - dateOfYourBirth.get(Calendar.MONTH);


                    dateOfYourBirth.add(Calendar.YEAR, yourAge);
                    dateOfYourBirth.add(Calendar.MONTH, yourAgeMonth);
                    strBuild.append(yourAge + " ปี " + yourAgeMonth + " เดือน");
                    userBd.setText(strBuild);
                    userName.setText(myname);


                    weight = Float.parseFloat(myweight);
                    height = Float.parseFloat(myheight);
                    txt_w.setText(""+weight);
                    txt_h.setText(""+height);

                    User user = dataSnapshot.getValue(User.class);

                    if(user.getImageURL().equals("default")){
                        image_profile.setImageResource(R.mipmap.ic_launcher_round);
                    }else{
                        Glide.with(GraphGirl.this).load(user.getImageURL()).into(image_profile);
                    }

                    LineDataSet lineDtaSet1 = new LineDataSet(dataValues1(),"Data set2");
                    LineDataSet lineDtaSet2 = new LineDataSet(dataValues2(),"Data set3");


                    lineDtaSet1.setColor(Color.rgb(255, 128, 0));
                    lineDtaSet1.setLineWidth(1.5f);

                    lineDtaSet2.setColor(Color.rgb(153, 0, 153));
                    lineDtaSet2.setLineWidth(1.5f);

                    dataSets = new ArrayList<>();
                    dataSets.add(lineDtaSet1);
                    dataSets.add(lineDtaSet2);


                    ArrayList<Entry> dataValsX = new ArrayList<>();
                    dataValsX.add(new Entry(height, weight));

                    LineDataSet lineDtaSet3 = new LineDataSet(dataValsX,"ลูกน้อย");
                    lineDtaSet3.setLineWidth(2.5f);
                    lineDtaSet3.setCircleRadius(7f);
                    //lineDtaSet3.setColor(Color.RED);
                    lineDtaSet3.setCircleColor(Color.RED);
                    //lineDtaSet3.setHighLightColor(Color.rgb(190, 190, 190));
                    lineDtaSet3.setAxisDependency(YAxis.AxisDependency.LEFT);
                    lineDtaSet3.setLabel("ลูกน้อย");

                    //lineDtaSet3.setValueTextSize(10f);
                    dataSets.add(lineDtaSet3);



                    data = new LineData(dataSets);

                    chart.setData(data);

                    lineDtaSet1.setDrawCircles(false);
                    lineDtaSet2.setDrawCircles(false);
                    lineDtaSet3.setDrawCircleHole(false);

                    lineDtaSet1.setDrawValues(false);
                    lineDtaSet2.setDrawValues(false);
                    lineDtaSet3.setDrawValues(false);



                    chart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private  ArrayList<Entry> dataValues1(){
        final ArrayList<Entry> dataVals = new ArrayList<>();

        myRef = FirebaseDatabase.getInstance().getReference("GirlGraphLower");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                graphList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Graph graph = new Graph();
                    String s1 = snapshot.toString();
                    String[] s2 = s1.split("=");

                    if (s2[4] != null && s2[4].length() > 0 && s2[4].charAt(s2[4].length() - 1) == '}') {
                        s2[4] = s2[4].substring(0, s2[4].length() - 3);
                    }

                    String[] s5 = s2[3].split(",");

                    if (s2[2].equals(" {weight")){
                        graph.setHeight(s2[4]);
                        graph.setWeight(s5[0]);
                        Log.d("","h--"+Float.parseFloat(s2[4]));
                        Log.d("","w--"+Float.parseFloat(s5[0]));

                        dataVals.add(new Entry(Float.parseFloat(s2[4]), Float.parseFloat(s5[0])));

                        graphList.add(graph);
                    } else {
                        graph.setHeight(s5[0]);
                        graph.setWeight(s2[4]);
                        Log.d("","h--"+Float.parseFloat(s5[0]));
                        Log.d("","w--"+Float.parseFloat(s2[4]));

                        dataVals.add(new Entry(Float.parseFloat(s5[0]), Float.parseFloat(s2[4])));

                        graphList.add(graph);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return  dataVals;
    }

    private  ArrayList<Entry> dataValues2(){
        final ArrayList<Entry> dataVals = new ArrayList<>();

        myRef = FirebaseDatabase.getInstance().getReference("GirlGraphUpper");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                graphList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Graph graph = new Graph();
                    String s1 = snapshot.toString();
                    String[] s2 = s1.split("=");

                    if (s2[4] != null && s2[4].length() > 0 && s2[4].charAt(s2[4].length() - 1) == '}') {
                        s2[4] = s2[4].substring(0, s2[4].length() - 3);
                    }

                    String[] s5 = s2[3].split(",");
                    if (s2[2].equals(" {weight")){
                        graph.setHeight(s2[4]);
                        graph.setWeight(s5[0]);
                        Log.d("","h--"+Float.parseFloat(s2[4]));
                        Log.d("","w--"+Float.parseFloat(s5[0]));

                        dataVals.add(new Entry(Float.parseFloat(s2[4]), Float.parseFloat(s5[0])));

                        graphList.add(graph);
                    } else {
                        graph.setHeight(s5[0]);
                        graph.setWeight(s2[4]);
                        Log.d("","h--"+Float.parseFloat(s5[0]));
                        Log.d("","w--"+Float.parseFloat(s2[4]));

                        dataVals.add(new Entry(Float.parseFloat(s5[0]), Float.parseFloat(s2[4])));

                        graphList.add(graph);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return  dataVals;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}