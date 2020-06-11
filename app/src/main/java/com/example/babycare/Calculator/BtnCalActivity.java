package com.example.babycare.Calculator;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.babycare.Baby.NutrientsActivity;
import com.example.babycare.Login.FragmentDoctor;
import com.example.babycare.Login.FragmentUser;
import com.example.babycare.Login.Login2Activity;
import com.example.babycare.NutrientsGraph;
import com.example.babycare.R;
import com.example.babycare.RecordActivity;

import java.util.ArrayList;

public class BtnCalActivity extends AppCompatActivity {

    Button btnViewNutrient;
    ImageView btn_cal;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_cal);

        btn_cal = (ImageButton) findViewById(R.id.btn_back_cal);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpaper);
        btnViewNutrient = (Button) findViewById(R.id.btnViewNutrient);

        BtnCalActivity.ViewPagerAdapter adapter = new BtnCalActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentCaltoday(), "ผลการวิเคราะห์วันนี้");
        adapter.AddFragment(new FragmentCaltoweek(), "ผลการวิเคราะห์สัปดาห์");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        btn_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BtnCalActivity.this, RecordActivity.class);
                startActivity(i);
            }
        });

        btnViewNutrient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(BtnCalActivity.this, NutrientsActivity.class);
                Intent i = new Intent(BtnCalActivity.this, NutrientsGraph.class);
                startActivity(i);
            }
        });
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentList;
        private ArrayList<String> fragmentListTitle;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragmentList = new ArrayList<>();
            this.fragmentListTitle = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentListTitle.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentListTitle.get(position);
        }

        public void AddFragment(Fragment fragment, String Title) {
            fragmentList.add(fragment);
            fragmentListTitle.add(Title);
        }
    }

}
