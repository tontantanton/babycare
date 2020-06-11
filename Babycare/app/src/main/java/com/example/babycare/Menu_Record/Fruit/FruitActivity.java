package com.example.babycare.Menu_Record.Fruit;

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

import com.example.babycare.Menu_Record.Dessert.DessertActivity;
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

public class FruitActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView recyclerView;
    DatabaseReference reference;
    ImageButton btnBack4;
    FruitAdapter adapter;
    List<FruitMenu> fruits = new ArrayList<>();

    FirebaseUser firebaseUser;
    ArrayList<String> fruitName = new ArrayList<>();
    ArrayList<String> fruitCal = new ArrayList<>();
    String choose, cal;
    String strDate;

    AutoCompleteTextView autofruit;
    private TextView textview_seekbar, persen;
    private BubbleSeekBar bubbleSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.recyclerview_fruit);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnBack4 = (ImageButton) findViewById(R.id.btn_back4);
        btnBack4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FruitActivity.this, RecordActivity.class);
                startActivity(i);
            }
        });

        autofruit = (AutoCompleteTextView)findViewById(R.id.autofruit);


        final ArrayAdapter<String> adapter_array = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, fruitName);
        autofruit.setAdapter(adapter_array);




        reference = database.getReference("Catagories").child("Fruit");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FruitMenu fruitMenu = new FruitMenu();
                    String s1 = postSnapshot.toString();
                    String[] s2 = s1.split("=");

                    if (s2[4] != null && s2[4].length() > 0 && s2[4].charAt(s2[4].length() - 1) == '}') {
                        s2[4] = s2[4].substring(0, s2[4].length() - 3);
                    }

                    Log.d("", "onDataChangeThird: =========================" + s2[4]);
                    String[] s5 = s2[3].split(",");

                    Log.d("","name=====" + s5[0]);
                    fruitMenu.setName(s5[0]);
                    fruitMenu.setCalories((s2[4]));

                    fruitName.add(s5[0]);

                    fruitCal.add(fruitMenu.getCalories());

                    fruits.add(fruitMenu);

                    adapter = new FruitAdapter(fruits);

                    adapter.setItemClickListener(new FruitAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Log.d("test", "onItemClick: index = " + position);
                            for (int i=0; i < fruitName.size(); i++){
                                if (i == position){
                                    choose = fruitName.get(i);
                                    cal = fruitCal.get(i);
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
                Log.d("","size---"+fruitName.size());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });

        autofruit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ShowDialog();

            }
        });
    }

    String keepPer;
    public void ShowDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(FruitActivity.this);
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
                Toast.makeText(FruitActivity.this, "บันทึกอาหารของลูกน้อยเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
                reference = FirebaseDatabase.getInstance().getReference();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", firebaseUser.getUid());
                hashMap.put("name", choose);
                hashMap.put("calories", cal);
                hashMap.put("date", strDate);
                hashMap.put("quantity", keepPer);

                reference.child("Calculators").push().setValue(hashMap);

                autofruit.getText().clear();
            }
        });
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LayoutInflater.from(FruitActivity.this).inflate(R.layout.activity_sidedish, null);
                autofruit.getText().clear();

            }
        });
        builder.setView(Viewlayout);
        builder.show();


    }

}

