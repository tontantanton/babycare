package com.example.babycare.Chat.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.babycare.Baby.BabyActivity;
import com.example.babycare.ChatActivity;
import com.example.babycare.Content.ImagesActivity;
import com.example.babycare.Menu_recommand.MenuActivity;
import com.example.babycare.Model.Chat;
import com.example.babycare.Model.User;
import com.example.babycare.R;
import com.example.babycare.RecordActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserChat extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

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
        setContentView(R.layout.activity_chat);

//        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("");

        BottomNavigationView navView = findViewById(R.id.navigation3);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().findItem(R.id.navigation_chat).setChecked(true);


        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.name);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                username.setText(user.getUsername());
//                if(user.getImageURL().equals("default")){
//                    profile_image.setImageResource(R.mipmap.ic_launcher_round);
//                }else{
//                    Glide.with(UserChat.this).load(user.getImageURL()).into(profile_image);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);



        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ViewPagerAdpter viewPagerAdpter = new ViewPagerAdpter(getSupportFragmentManager());
                int unread = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && !chat.isIsseen()){
                        unread++;
                    }
                }
                if (unread == 0){
                    viewPagerAdpter.addFragment(new OnlyUserChatFragment(), "แชท");
                } else {
                    viewPagerAdpter.addFragment(new OnlyUserChatFragment(), "("+unread+") แชท");
                }
                viewPagerAdpter.addFragment(new OnlyUserFragment(), "คุณหมอ");

                viewPager.setAdapter(viewPagerAdpter);

                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    class ViewPagerAdpter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdpter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
