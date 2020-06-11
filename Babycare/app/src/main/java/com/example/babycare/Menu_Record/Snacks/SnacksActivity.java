package com.example.babycare.Menu_Record.Snacks;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.Menu_Record.Dessert.DessertAdapter;
import com.example.babycare.Menu_Record.Dessert.DessertMenu;
import com.example.babycare.Menu_Record.Fruit.FruitActivity;
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

public class SnacksActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView recyclerView;
    DatabaseReference reference;
    SnacksAdapter adapter;
    private List<SnacksMenu> snacks = new ArrayList<>();

    FirebaseUser firebaseUser;
    ArrayList<String> snacksName = new ArrayList<>();
    ArrayList<String> snacksCal = new ArrayList<>();
    String choose, cal;
    String strDate;

    AutoCompleteTextView autosanck;


    private TextView textview_seekbar, persen;
    private BubbleSeekBar bubbleSeekBar;


    ImageButton btnBack8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.recyclerview_snacks);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnBack8 = (ImageButton)findViewById(R.id.btn_back8);
        btnBack8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SnacksActivity.this, RecordActivity.class);
                startActivity(i);
            }
        });

        autosanck = (AutoCompleteTextView)findViewById(R.id.autosnacks);


        final ArrayAdapter<String> adapter_array = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, snacksName);
        autosanck.setAdapter(adapter_array);


        reference = database.getReference("Catagories").child("Snack");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SnacksMenu snacksMenu = new SnacksMenu();
                    String s1 = postSnapshot.toString();
                    String[] s2 = s1.split("=");

                    if (s2[4] != null && s2[4].length() > 0 && s2[4].charAt(s2[4].length() - 1) == '}') {
                        s2[4] = s2[4].substring(0, s2[4].length() - 3);
                    }

                    Log.d("", "onDataChangeThird: =========================" + s2[4]);
                    String[] s5 = s2[3].split(",");

                    Log.d("","name=====" + s5[0]);
                    snacksMenu.setName(s5[0]);
                    snacksMenu.setCalories((s2[4]));

                    snacksName.add(s5[0]);

                    snacksCal.add(snacksMenu.getCalories());

                    snacks.add(snacksMenu);

                    adapter = new SnacksAdapter(snacks);

                    adapter.setItemClickListener(new SnacksAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Log.d("test", "onItemClick: index = " + position);
                            for (int i=0; i < snacksName.size(); i++){
                                if (i == position){
                                    choose = snacksName.get(i);
                                    cal = snacksCal.get(i);
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
                Log.d("","size---"+snacksName.size());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });

        autosanck.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ShowDialog();

            }
        });
    }

    String keepPer;
    public void ShowDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(SnacksActivity.this);
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
                keepPer = String.valueOf(progress);
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
                Toast.makeText(SnacksActivity.this, "บันทึกอาหารของลูกน้อยเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
                reference = FirebaseDatabase.getInstance().getReference();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", firebaseUser.getUid());
                hashMap.put("name", choose);
                hashMap.put("calories", cal);
                hashMap.put("date", strDate);
                hashMap.put("quantity", keepPer);

                reference.child("Calculators").push().setValue(hashMap);

                autosanck.getText().clear();
            }
        });
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LayoutInflater.from(SnacksActivity.this).inflate(R.layout.activity_snacks, null);
                autosanck.getText().clear();

            }
        });
        builder.setView(Viewlayout);
        builder.show();


    }


}