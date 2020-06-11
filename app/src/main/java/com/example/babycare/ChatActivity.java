package com.example.babycare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.babycare.Baby.BabyActivity;
import com.example.babycare.Content.ImagesActivity;
//import com.example.babycare.Menu_recommand.MenuActivity;

public class ChatActivity extends AppCompatActivity {




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    gotoMain();
                    return true;
                case R.id.navigation_chat:
                    gotoChat();
                    return true;
                case R.id.navigation_calculator:
                    gotoCal();
                    return true;
                case R.id.navigation_menu:
                    gotoMenu();
                    return true;
                case R.id.navigation_baby:
                    gotoBaby();
                    return true;
            }
            return false;
        }
    };
    public void gotoMain(){
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
    }
    public void gotoChat(){
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
    public void gotoCal(){
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }
    public void gotoMenu(){
//        Intent intent = new Intent(this, MenuActivity.class);
//        startActivity(intent);
    }
    public void gotoBaby(){
        Intent intent = new Intent(this, BabyActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        BottomNavigationView navView = findViewById(R.id.navigation3);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().findItem(R.id.navigation_chat).setChecked(true);
    }

}
