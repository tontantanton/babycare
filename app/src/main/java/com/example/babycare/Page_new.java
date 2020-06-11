package com.example.babycare;


import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

//import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Page_new extends AppCompatActivity {

    DatabaseReference reference;

    //TextView text_1;
    String userID;

    TextView text_user_id;
    TextView text_2;
    DatabaseReference mDatabase;
    TextView text_new;

    ArrayList<BarEntry> BARENTRY;
    BarDataSet Bardataset;
    BarData data;
    BarChart barChart;
    XAxis xAxis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_new);

        barChart = (BarChart) findViewById(R.id.barchart);

        barChart.getDescription().setEnabled(false); // hide 1.2.3 bottom xAxis
        barChart.setPinchZoom(false);

        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getLegend().setEnabled(false);

        text_user_id = (TextView) findViewById(R.id.text_user_id);

        final Intent intent = getIntent();
        String s_text_calories = intent.getStringExtra("s_text_calories");
        String s_text_protein = intent.getStringExtra("s_text_protein");
        String s_text_calcium = intent.getStringExtra("s_text_calcium");
        String s_text_iodine = intent.getStringExtra("s_text_iodine");
        String s_text_iron = intent.getStringExtra("s_text_iron");
        String s_text_vitamina = intent.getStringExtra("s_text_vitamina");
        String s_text_vitaminb6 = intent.getStringExtra("s_text_vitaminb6");
        String s_text_vitaminb12 = intent.getStringExtra("s_text_vitaminb12");
        String s_text_vitaminc = intent.getStringExtra("s_text_vitaminc");

        float f1 = Float.parseFloat(s_text_calories);//ตรงนี้ไงที่เราไม่เอามาแสดง
        float f2 = Float.parseFloat(s_text_protein);
        float f3 = Float.parseFloat(s_text_calcium);
        float f4 = Float.parseFloat(s_text_iodine);
        float f5 = Float.parseFloat(s_text_iron);
        float f6 = Float.parseFloat(s_text_vitamina);
        float f7 = Float.parseFloat(s_text_vitaminb6);
        float f8 = Float.parseFloat(s_text_vitaminb12);
        float f9 = Float.parseFloat(s_text_vitaminc);

        BARENTRY = new ArrayList<>();

       // BARENTRY.add(new BarEntry(0, (12));
        BARENTRY.add(new BarEntry(0, (float) f2 ));
        BARENTRY.add(new BarEntry(1, (float) f3));
        BARENTRY.add(new BarEntry(2, (float) f4));
        BARENTRY.add(new BarEntry(3, (float) f5));
        BARENTRY.add(new BarEntry(4, (float) f6));
        BARENTRY.add(new BarEntry(5, (float) f7));
        BARENTRY.add(new BarEntry(6, (float) f8));
        BARENTRY.add(new BarEntry(7, (float) f9));

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        //dataSet.setColors(colors);

        Bardataset = new BarDataSet(BARENTRY, "Cells");
        data = new BarData(Bardataset);
        Bardataset.setColors(colors);
        barChart.setData(data);

        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(8);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(7f);
        xAxis.setDrawLabels(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.animateY(3000);
        barChart.invalidate();

        ///////////////////////////////////////////////


    }
}
