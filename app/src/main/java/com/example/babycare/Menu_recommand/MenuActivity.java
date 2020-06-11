package com.example.babycare.Menu_recommand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.babycare.Baby.BabyActivity;
import com.example.babycare.Chat.User.UserChat;
import com.example.babycare.Content.ImagesActivity;
import com.example.babycare.R;
import com.example.babycare.RecordActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    MenurecAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseMenus;
    private ArrayList<Menureccommand> menureccommandList;
    List<Benner> mBenner;
    private FirebaseDatabase database;

    String num;
//    private String currentUserId;
//    private FirebaseAuth mAuth;
//    private TextView name;
//    private ImageView pic;
//    String namemenu;


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

    public void gotoMain() {
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
    }

    public void gotoChat() {
        Intent intent = new Intent(this, UserChat.class);
        startActivity(intent);
    }

    public void gotoCal() {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }

    public void gotoMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void gotoBaby() {
        Intent intent = new Intent(this, BabyActivity.class);
        startActivity(intent);
    }


    HashMap<String, String> image_list;
    SliderLayout mSlider;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        BottomNavigationView navView = findViewById(R.id.navigation5);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().findItem(R.id.navigation_menu).setChecked(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_menu);
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mProgressCircle = findViewById(R.id.progress_circle_menu);
        menureccommandList = new ArrayList<>();


        FirebaseDatabase database = FirebaseDatabase.getInstance();

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        String uId = mDatabaseMenus.push().getKey();

        mDatabaseMenus = FirebaseDatabase.getInstance().getReference("menuRecs");
        mDatabaseMenus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Menureccommand menurec = postSnapshot.getValue(Menureccommand.class);
//                    String [] s1 = postSnapshot.toString().split(",");
//                    String [] s2 = s1[3].split("=");
////                    String [] s3 = s2[0].split("=");
//                    if (s2[1] != null && s2[1].length() > 0 && s2[1].charAt(s2[1].length()-1) == '}'){
//                        s2[1] = s2[1].substring(0, s2[1].length()-2);
//                    }
//
////                    mDatabaseMenus.child("menuRecs").setValue(menurec);
////                    Log.d("ii","========================"+menurec);
                    menureccommandList.add(menurec);
                }
                mAdapter = new MenurecAdapter(MenuActivity.this, menureccommandList);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MenuActivity.this, "================" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });


        setupSlider();

    }

    private void setupSlider() {
        mSlider = (SliderLayout) findViewById(R.id.slider_image);
        mBenner = new ArrayList<>();
        image_list = new HashMap<>();

        final DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Benner");

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final Benner benner = postSnapshot.getValue(Benner.class);
                    mBenner.add(benner);
                    image_list.put(benner.getName(), benner.getImage());


                for (String key : image_list.keySet()) {
//                    String[] keySplit = key.split("_");
//                    String nameoffood = keySplit[0];
//                    String idoffood = keySplit[1];

                    final TextSliderView textSliderView = new TextSliderView(getBaseContext());
                    textSliderView.description(benner.getName())
                            .image(image_list.get(key))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Log.d("","onclick====="+benner.getName());
//                                    Intent intent = new Intent(MenuActivity.this, FoodDetailActivity.class);
//                                    intent.putExtras(textSliderView.getBundle());
//                                    startActivity(intent);

                                }
                            });

//                    textSliderView.bundle(new Bundle());
//                    textSliderView.getBundle().putString("FoodId", idoffood);

                    mSlider.addSlider(textSliderView);

//                    reference2.removeEventListener(this);
                }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(400);


    }

    @Override
    protected void onStop() {
        super.onStop();
        mSlider.startAutoCycle();
    }
}
