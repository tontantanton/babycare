package com.example.babycare.Menu_Record.Mommenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.Calculator.CalToday;
import com.example.babycare.Calculator.CalTodayAdapter;
import com.example.babycare.Page_search;
import com.example.babycare.Recipes;
import com.example.babycare.RecordActivity;
import com.example.babycare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MommenuActivity extends AppCompatActivity {

    ImageButton btnBack1;
    Button addmenu;
    RecyclerView recyclerView;
    FirebaseUser firebaseUser;

    DatabaseReference reference;

    List<MomMenu> mMommenu = new ArrayList<>();
    ArrayList<String> MommenuName = new ArrayList<>();
    ArrayList<String> MommenuCal = new ArrayList<>();
    MommenuAdapter mommenuAdapter;
    AutoCompleteTextView automenumom;


    String choose, cal;
    String strname, strcal, strDate;

    String strpro;
    String pro;

    /////////////////////
    ////////////////////////
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeRef = mRootRef.child("MenuMom");

    List<Recipes> rowItems;

    String protein;
    String vitamina;
    String vitaminc;
    String vitaminb6;
    String vitaminb12;
    String calcium;
    String iodine;
    String iron;

    /////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mommenu);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        getData();

        recyclerView = findViewById(R.id.recyclerview_mom);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnBack1 = (ImageButton)findViewById(R.id.btn_back1);
        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MommenuActivity.this, RecordActivity.class);
                startActivity(i);
            }
        });

        automenumom = (AutoCompleteTextView)findViewById(R.id.automenumom);


        final ArrayAdapter<String> adapter_array = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, MommenuName);
        automenumom.setAdapter(adapter_array);




        addmenu = (Button)findViewById(R.id.addmemumom);
        addmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i = new Intent(MommenuActivity.this, AddmenumomActivity.class);
                Intent i = new Intent(MommenuActivity.this, Page_search.class);
                startActivity(i);
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("MenuMom");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMommenu.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MomMenu momMenu = snapshot.getValue(MomMenu.class);

                    if (momMenu.getId().equals(firebaseUser.getUid()) ) {

                        Log.w("","name===="+firebaseUser.getUid());
                        Log.d("","calid---"+momMenu.getId());
                        Log.d("","name---"+momMenu.getName());
                        strname = momMenu.getName();
                        momMenu.setName(strname);
                        MommenuName.add(strname);
                        strcal = momMenu.getCalories();
                        MommenuCal.add(strcal);

                      //  strpro = momMenu.getProtein();
                       // MommenuCal.add(strpro);

                        Log.d("","name"+strname);
                        mMommenu.add(momMenu);

                        mommenuAdapter = new MommenuAdapter(mMommenu);
                        mommenuAdapter.setItemClickListener(new MommenuAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Log.d("test", "onItemClick: index = " + position); //menuที่เลืออยู่ช่องไหน
                                for (int i=0; i < MommenuName.size(); i++){
                                    if (i == position){
                                        choose = MommenuName.get(i);
                                        cal = MommenuCal.get(i);
                                    }
                                }
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
                                strDate = mdformat.format(calendar.getTime());
                                ShowDialog();
                            }
                        });

                    }

                    recyclerView.setAdapter(mommenuAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        automenumom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ShowDialog();

            }
        });


    }

    private TextView textview_seekbar, persen;
    private BubbleSeekBar bubbleSeekBar;
    private String numper;


    public void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MommenuActivity.this);
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View Viewlayout = inflater.inflate(R.layout.activity_bubbleseek_bar,
                (ViewGroup) findViewById(R.id.layout_seekbar));

        persen = (TextView) Viewlayout.findViewById(R.id.textview_persen);

        builder.setView(Viewlayout);



        bubbleSeekBar = (BubbleSeekBar) Viewlayout.findViewById(R.id.seekbar);
        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                persen.setText("" + progress + "%");
                numper = String.valueOf(progress);
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });

        builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MommenuActivity.this, "บันทึกอาหารของลูกน้อยเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();

                reference = FirebaseDatabase.getInstance().getReference();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", firebaseUser.getUid());
                hashMap.put("name", choose);
                hashMap.put("calories", cal);
                hashMap.put("protein", protein);
                        hashMap.put("vitamina", vitamina);
                        hashMap.put("vitaminc", vitaminc);
                        hashMap.put("vitaminb6", vitaminb6);
                        hashMap.put("vitaminb12", vitaminb12);
                        hashMap.put("calcium", calcium);
                        hashMap.put("iodine", iodine);
                        hashMap.put("iron", iron);
                hashMap.put("date", strDate);
                hashMap.put("quantity", numper);

                reference.child("Calculators").push().setValue(hashMap);

                automenumom.getText().clear();
            }
        });





        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LayoutInflater.from(MommenuActivity.this).inflate(R.layout.activity_dessert, null);
                automenumom.getText().clear();

            }
        });
        builder.setView(Viewlayout);
        builder.show();


    }
    public void getData() {
        mRecipeRef.orderByChild("protein").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rowItems = new ArrayList<Recipes>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    final String recipeKey = postSnapshot.getKey();
//                    final String name = postSnapshot.child("name").getValue(String.class);
//                    final String calories = postSnapshot.child("calories").getValue(String.class);
//                    final String protein = postSnapshot.child("protein").getValue(String.class);
//                    final String vitamina = postSnapshot.child("vitamina").getValue(String.class);
//                    final String vitaminc = postSnapshot.child("vitaminc").getValue(String.class);
//                    final String vitaminb6 = postSnapshot.child("vitaminb6").getValue(String.class);
//                    final String vitaminb12 = postSnapshot.child("vitaminb12").getValue(String.class);
//                    final String calcium = postSnapshot.child("calcium").getValue(String.class);
//                    final String iodine = postSnapshot.child("iodine").getValue(String.class);
//                    final String iron = postSnapshot.child("iron").getValue(String.class);




                    protein = postSnapshot.child("protein").getValue(String.class);
                    vitamina = postSnapshot.child("vitamina").getValue(String.class);
                    vitaminc = postSnapshot.child("vitaminc").getValue(String.class);
                    vitaminb6 = postSnapshot.child("vitaminb6").getValue(String.class);
                    vitaminb12 = postSnapshot.child("vitaminb12").getValue(String.class);
                    calcium = postSnapshot.child("calcium").getValue(String.class);
                    iodine = postSnapshot.child("iodine").getValue(String.class);
                    iron = postSnapshot.child("iron").getValue(String.class);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        }


}
