package com.example.babycare.Content;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.util.Log;
import com.example.babycare.Baby.BabyActivity;
import com.example.babycare.Chat.User.UserChat;
import com.example.babycare.Menu_Record.Dessert.DessertActivity;
import com.example.babycare.Menu_recommand.MenuActivity;
import com.example.babycare.RecordActivity;
//import com.example.babycare.Menu_recommand.MenuActivity;
import com.example.babycare.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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
        Intent intent = new Intent(this, UserChat.class);
        startActivity(intent);
    }
    public void gotoCal(){
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }
    public void gotoMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    public void gotoBaby(){
        Intent intent = new Intent(this, BabyActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    String [] s1 = postSnapshot.toString().split(",");
                    String []s2 = s1[4].split("=");
//                    Log.d("","tset----"+s1[4].split("="));
//                    String [] s3 = s2[0].split("=");
                    if (s2[1] != null && s2[1].length() > 0 && s2[1].charAt(s2[1].length() - 1) == '}') {
                        s2[1] = s2[1].substring(0, s2[1].length() - 3);
                    }

                    upload.setDescription(s2[1]);
                    Log.d("", "INSIDE1111: *******  "+s2[1]);
//                    Log.d("", "INSIDE2222: *******  "+s2[0]);
                    mUploads.add(upload);

//                    Log.d("", "onDataChange: =========================");
                }
                mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);

                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
}