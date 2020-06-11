package com.example.babycare.Calculator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.babycare.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FragmentCaltoweek extends Fragment {

    FirebaseUser firebaseUser;

    DatabaseReference reference;

    List<Calofweek> mcalofweek;

    ArrayList<Integer> a = new ArrayList<Integer>();
    ArrayList<Float> allResult = new ArrayList<Float>();
    ArrayList<Float> allcal = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> dateall = new ArrayList<String>();

    TextView txtsumcal;

    String testDate,txtresult;

    BarChart chart;
    ArrayList<BarEntry> BARENTRY;
    BarDataSet Bardataset;
    BarData data;

    float calnum, quannum, sum, sumOneweek;
    XAxis xAxis;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cal_week, container, false);

        txtsumcal = (TextView)view.findViewById(R.id.txtsumcal);

        chart = (BarChart) view.findViewById(R.id.chart1);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        chart.getAxisLeft().setDrawGridLines(false);

        chart.getLegend().setEnabled(false);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Calculators");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calofweek calofweek = dataSnapshot.getValue(Calofweek.class);
                testDate = calofweek.getDate();

                mcalofweek = new ArrayList<>();


                reference = FirebaseDatabase.getInstance().getReference("Calculators");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Calofweek calofweek = snapshot.getValue(Calofweek.class);

                            if (calofweek.getId().equals(firebaseUser.getUid())) {
                                calnum = Float.parseFloat(calofweek.getCalories());
                                quannum = Float.parseFloat(calofweek.getQuantity());
                                sum = (calnum * quannum) / 100;
                                allcal.add(sum);
                                date.add(calofweek.getDate());

                            }
                        }

                        for (int i = 0; i < date.size(); i++) {
                            if (!dateall.contains(date.get((i)))) {
                                dateall.add(date.get(i));
                            }
                        }

                        float result =0;

                        for (int i = 0; i <  dateall.size(); i++) {
                            result = 0;
                            for (int j = 0; j <date.size(); j++) {
                                if(date.get(j).equalsIgnoreCase(dateall.get(i))){
                                    result += allcal.get(j);
                                }
                            }
                            allResult.add(result);
                        }
                        Log.d("","date--------"+dateall);
                        Log.d("","sumcal--------"+allResult);

                        if (dateall.size()<7){
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(dateall));
                        } else {
                            int date1,date2,date3,date4,date5,date6,date7;
                            date1 = dateall.size()-7;
                            date2 = dateall.size()-6;
                            date3 = dateall.size()-5;
                            date4 = dateall.size()-4;
                            date5 = dateall.size()-3;
                            date6 = dateall.size()-2;
                            date7 = dateall.size()-1;

                            String showDate1 = dateall.get(date1);
                            String showDate2 = dateall.get(date2);
                            String showDate3 = dateall.get(date3);
                            String showDate4 = dateall.get(date4);
                            String showDate5 = dateall.get(date5);
                            String showDate6 = dateall.get(date6);
                            String showDate7 = dateall.get(date7);

                            ArrayList<String> show7Date = new ArrayList<>();
                            show7Date.add(showDate1);
                            show7Date.add(showDate2);
                            show7Date.add(showDate3);
                            show7Date.add(showDate4);
                            show7Date.add(showDate5);
                            show7Date.add(showDate6);
                            show7Date.add(showDate7);
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(show7Date));

                        }



                        BARENTRY = new ArrayList<>();

                        if (allResult.size()<7){
                            for (int i = 0; i < allResult.size(); i++){
                                float val = (float) allResult.get(i);
                                BARENTRY.add(new BarEntry(i, val));
                                sumOneweek += allResult.get(i);
                                Log.d("", "val<7 ----" + val);
                            }

                        }else {
                            int day1,day2,day3,day4,day5,day6,day7;
                            day1 = allResult.size()-7;
                            day2 = allResult.size()-6;
                            day3 = allResult.size()-5;
                            day4 = allResult.size()-4;
                            day5 = allResult.size()-3;
                            day6 = allResult.size()-2;
                            day7 = allResult.size()-1;

                            float sumDay1 = (float) allResult.get(day1);
                            float sumDay2 = (float) allResult.get(day2);
                            float sumDay3 = (float) allResult.get(day3);
                            float sumDay4 = (float) allResult.get(day4);
                            float sumDay5 = (float) allResult.get(day5);
                            float sumDay6 = (float) allResult.get(day6);
                            float sumDay7 = (float) allResult.get(day7);

                            ArrayList<Float> sum7Day = new ArrayList<>();
                            sum7Day.add(sumDay1);
                            sum7Day.add(sumDay2);
                            sum7Day.add(sumDay3);
                            sum7Day.add(sumDay4);
                            sum7Day.add(sumDay5);
                            sum7Day.add(sumDay6);
                            sum7Day.add(sumDay7);

                            for (int i = 0; i < sum7Day.size(); i++) {
                                BARENTRY.add(new BarEntry(i, sum7Day.get(i)));
                                sumOneweek += sum7Day.get(i);
                                Log.d("", "val  " + sum7Day.get(i));
                            }
                        }

                        DecimalFormat df = new DecimalFormat("0.00");
                        df.setRoundingMode(RoundingMode.CEILING.HALF_UP);
                        double f = Double.parseDouble(df.format(sumOneweek));
                        txtresult = String.valueOf(f);
                        txtsumcal.setText(txtresult);

                        Bardataset = new BarDataSet(BARENTRY, "");

                        data = new BarData(Bardataset);


                        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

                        chart.setData(data);
                        xAxis.setGranularity(1f);
//                        xAxis.setGranularityEnabled(true);
//                        xAxis.setCenterAxisLabels(true);
                        xAxis.setDrawGridLines(false);
                        xAxis.setAxisMaximum(7);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setTextSize(7f);

                        chart.getAxisRight().setEnabled(false);

                        chart.animateY(3000);
                        chart.invalidate();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }




}

