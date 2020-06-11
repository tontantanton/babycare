package com.example.babycare.Menu_Record.Dessert;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.Calculator.CalToday;
import com.example.babycare.Menu_Record.Fastfood.FastfoodActivity;
import com.example.babycare.Menu_Record.Fruit.FruitMenu;
import com.example.babycare.Model.Chat;
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

public class DessertActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView recyclerView;
    DatabaseReference myRef, reference;

    private String userid;
    Intent intent;
    ValueEventListener seenListener;

    ImageButton btnBack7;
    DessertAdapter adapter,adapters;
    List<DessertMenu> desserts = new ArrayList<>();

    ArrayList<String> arrDesId = new ArrayList<>();
    String[] strings; //ข้อความอะไรก็ได้

    FirebaseUser firebaseUser;
    ArrayList<String> dessertName = new ArrayList<>();
    ArrayList<String> dessertCal = new ArrayList<>();
    String choose, cal;
    String strDate;
    AutoCompleteTextView autodessert;
    DessertMenu dessertMenu;

    String keepPer, mName, mCal;

    ///////////////////
    ////////////////////////
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeRef = mRootRef.child("Catagories2/Dessert");

    List<Recipes> rowItems;

    String protein;
    String vitamina;
    String vitaminc;
    String vitaminb6;
    String vitaminb12;
    String calcium;
    String iodine;
    String iron;
    String name;
    String calories;

    /////////////////

    ///////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        getData();

        intent = getIntent();
        userid = intent.getStringExtra("id");

        recyclerView = findViewById(R.id.recyclerview_dessert);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnBack7 = (ImageButton) findViewById(R.id.btn_back7);
        btnBack7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DessertActivity.this, RecordActivity.class);
                startActivity(i);
            }
        });


        autodessert = (AutoCompleteTextView) findViewById(R.id.autodessert);


        final ArrayAdapter<String> adapter_array = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, dessertName);
        autodessert.setAdapter(adapter_array);


        myRef = database.getReference("Catagories2").child("Dessert");
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DessertMenu dessertMenu = new DessertMenu();
                    String s1 = postSnapshot.toString();
                    String[] s2 = s1.split("=");
                    String[] s3 = s2[1].split(",");

                    if (s2[4] != null && s2[4].length() > 0 && s2[4].charAt(s2[4].length() - 1) == '}') {
                        s2[4] = s2[4].substring(0, s2[4].length() - 3);
                    }

                    Log.d("", "onDataChangeThird: =========================" + s2[4]);
                    String[] s5 = s2[3].split(",");

                    Log.d("", "name=====" + s5[0]);
                    dessertMenu.setName(s5[0]);
                   // dessertMenu.setCalories((s2[4]));
                    dessertMenu.setCalories(calories);

                    dessertName.add(s5[0]);


                    mCal = dessertMenu.getCalories();
                    dessertCal.add(mCal);

                    desserts.add(dessertMenu);

                    adapter = new DessertAdapter(desserts);

                    adapter.setItemClickListener(new DessertAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Log.d("test", "onItemClick: index = " + position);
                            for (int i = 0; i < dessertName.size(); i++) {
                                if (i == position) {
                                    choose = dessertName.get(i);
                                    cal = dessertCal.get(i);
                                }
                            }
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
                            strDate = mdformat.format(calendar.getTime());
                            ShowDialog();
                        }

                    });


                    recyclerView.setAdapter(adapter);


                }
                Log.d("", "size---" + dessertName.size());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });

        Log.d("", "size---" + dessertName.size());


        adapter = new

                DessertAdapter(desserts);

        adapter.setItemClickListener(new DessertAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Log.d("test", "onItemClick: index = " + position);
            }

        });

        autodessert.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ShowDialog();

            }
        });


        recyclerView.setAdapter(adapter);


    }


    private TextView textview_seekbar, persen;
    private BubbleSeekBar bubbleSeekBar;
    private String numper;


    public void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DessertActivity.this);
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(LAYOUT_INFLATER_SERVICE);


        View Viewlayout = inflater.inflate(R.layout.activity_bubbleseek_bar,
                (ViewGroup) findViewById(R.id.layout_seekbar));

        persen = (TextView) Viewlayout.findViewById(R.id.textview_persen);


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

        builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DessertActivity.this, "บันทึกอาหารของลูกน้อยเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();

                reference = FirebaseDatabase.getInstance().getReference();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", firebaseUser.getUid());
                hashMap.put("name", calcium);
               // hashMap.put("calories", cal);
                hashMap.put("calories", calories);
                hashMap.put("protein", protein);
                hashMap.put("vitemina", vitamina);
                hashMap.put("vitaminc", vitaminc);
                hashMap.put("vitaminb6", vitaminb6);
                hashMap.put("vitaminb12", vitaminb12);
                hashMap.put("calcium", name);
                hashMap.put("iodine", iodine);
                hashMap.put("iron", iron);
                hashMap.put("date", strDate);
                hashMap.put("quantity", numper);

                reference.child("Calculators").push().setValue(hashMap);


                autodessert.getText().clear();
            }
        });


        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LayoutInflater.from(DessertActivity.this).inflate(R.layout.activity_dessert, null);
                autodessert.getText().clear();

            }
        });
        builder.setView(Viewlayout);
        builder.create();
        builder.show();


    }


    public void getData() {
       mRecipeRef.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
          //    mRecipeRef.orderByChild().addListenerForSingleValueEvent(new ValueEventListener() {
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
                    name = postSnapshot.child("name").getValue(String.class);
                    calories = postSnapshot.child("calories").getValue(String.class);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


}

