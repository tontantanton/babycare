package com.example.babycare.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.babycare.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Login2Activity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_taplayout);


        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        viewPager = (ViewPager)findViewById(R.id.viewpaper);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentUser(),"ผู้ใช้ทั่วไป");
        adapter.AddFragment(new FragmentDoctor(),"คุณหมอ");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragmentList ;
        private ArrayList<String> fragmentListTitle ;

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

        public void AddFragment (Fragment fragment,String Title){
            fragmentList.add(fragment);
            fragmentListTitle.add(Title);
        }
    }

}
