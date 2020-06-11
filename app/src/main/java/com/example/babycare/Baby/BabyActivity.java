package com.example.babycare.Baby;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.babycare.Chat.User.UserChat;
import com.example.babycare.ChatActivity;
import com.example.babycare.Content.ImagesActivity;
import com.example.babycare.Menu_recommand.MenuActivity;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;
//import com.example.babycare.Menu_recommand.MenuActivity;

public class BabyActivity extends AppCompatActivity {

    private TextView userUname, userEmail, userName, userBd, userWeight, userHeight, userGender;

    CircleImageView image_profile;

    StorageReference storageReference;
    private static final int IMAGE_REQEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    //add Firebase Database stuff
    private FirebaseAuth mAuth;

    private DatabaseReference profileUserRef;
    private String userID;

    private String currentUserId;

    private ListView mListView;

    Button btn_edit, btn_graph;


    private String myusername, myemail, myname, mybd, myweight, myheight, mygender;


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

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby);

        BottomNavigationView navView = findViewById(R.id.navigation6);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().findItem(R.id.navigation_baby).setChecked(true);


        image_profile = findViewById(R.id.profile_image);
        mAuth = FirebaseAuth.getInstance();

        currentUserId = mAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        userName = (TextView) findViewById(R.id.name);
        userBd = (TextView) findViewById(R.id.age);


        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
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


//                    Log.d("","date----------"+monthtoday+"--"+dateOfYourBirth.get(Calendar.MONTH));

                    dateOfYourBirth.add(Calendar.YEAR, yourAge);
                    dateOfYourBirth.add(Calendar.MONTH, yourAgeMonth);
//                    if (Calendar.getInstance().before(dateOfYourBirth)) {
//                        yourAge--;
//                    }
                    strBuild.append(yourAge + " ปี " + yourAgeMonth + " เดือน");
                    userBd.setText(strBuild);
                    Log.d("", "age-------" + strBuild);

                    userName.setText("น้อง " + myname);
                }
                User user = (User) dataSnapshot.getValue(User.class);

                if (user.getImageURL().equals("default")) {
                    image_profile.setImageResource(R.mipmap.ic_launcher_round);
                } else {
                    Glide.with(BabyActivity.this).load(user.getImageURL()).into(image_profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BabyActivity.this, EditProfile.class);
                //intent.putExtra("key",key);
                intent.putExtra("username", myusername);
                intent.putExtra("email", myemail);
                intent.putExtra("name", myname);
                intent.putExtra("birthday", mybd);
                intent.putExtra("weight", myweight);
                intent.putExtra("height", myheight);
                intent.putExtra("gender", mygender);

                startActivity(intent);
            }
        });

        btn_graph = (Button) findViewById(R.id.btn_graph);
        btn_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myheight==null && myweight==null){
                    Toast.makeText(getApplicationContext(), "กรุณาเกรอกน้ำหนักและส่วนสูง", Toast.LENGTH_SHORT).show();
                } else {
                    if (mygender.equals("หญิง")) {
                        Intent intent = new Intent(BabyActivity.this, GraphGirl.class);
                        intent.putExtra("weight", myweight);
                        intent.putExtra("height", myheight);
                        intent.putExtra("gender", mygender);

                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(BabyActivity.this, GraphBoy.class);
                        intent.putExtra("weight", myweight);
                        intent.putExtra("height", myheight);
                        intent.putExtra("gender", mygender);

                        startActivity(intent);
                    }
                }



            }
        });

    }


}

