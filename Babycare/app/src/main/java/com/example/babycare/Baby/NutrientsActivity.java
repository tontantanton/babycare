package com.example.babycare.Baby;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;

import com.example.babycare.Calculator.CalToday;
import com.example.babycare.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NutrientsActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    List<CalToday> mcaltoday;
    String strsum, strDate;
    ArrayList<BarEntry> BARENTRY;
    BarDataSet Bardataset;
    BarData data;
    BarChart barChart;
    XAxis xAxis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrients);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Calculators");

        mcaltoday = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Calculators");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
                strDate = mdformat.format(calendar.getTime());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CalToday calToday = snapshot.getValue(CalToday.class);

                    if (calToday.getId().equals(firebaseUser.getUid()) && calToday.getDate().equals(strDate)) {
                        mcaltoday.add(calToday);
                        Toast.makeText(NutrientsActivity.this, calToday+"", Toast.LENGTH_SHORT).show();
                    }

                }
                barChart = (BarChart) findViewById(R.id.barchart);

                barChart.getDescription().setEnabled(false);
                barChart.setPinchZoom(false);

                barChart.setDrawBarShadow(false);
                barChart.setDrawGridBackground(false);

                xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);

                barChart.getAxisLeft().setDrawGridLines(false);

                barChart.getLegend().setEnabled(false);


                BARENTRY = new ArrayList<>();
                BARENTRY.add(new BarEntry(0, 10));
                BARENTRY.add(new BarEntry(1, 21));
                BARENTRY.add(new BarEntry(2, 32));
                BARENTRY.add(new BarEntry(3, 43));
                BARENTRY.add(new BarEntry(4, 54));
                BARENTRY.add(new BarEntry(5, 65));

                Bardataset = new BarDataSet(BARENTRY, "Cells");

                data = new BarData(Bardataset);

                Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

                barChart.setData(data);
                xAxis.setGranularity(1f);
                xAxis.setDrawGridLines(false);
                xAxis.setAxisMaximum(7);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(7f);

                barChart.getAxisRight().setEnabled(false);

                barChart.animateY(3000);
                barChart.invalidate();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
