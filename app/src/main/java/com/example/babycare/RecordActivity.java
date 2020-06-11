package com.example.babycare;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.Baby.BabyActivity;
import com.example.babycare.Calculator.BtnCalActivity;
import com.example.babycare.Chat.User.UserChat;
import com.example.babycare.Content.ImagesActivity;
import com.example.babycare.Menu_Record.Dessert.DessertActivity;
import com.example.babycare.Menu_Record.Drink.DrinkActivity;
import com.example.babycare.Menu_Record.Fastfood.FastfoodActivity;
import com.example.babycare.Menu_Record.Fruit.FruitActivity;
import com.example.babycare.Menu_Record.Meat.MeatActivity;
import com.example.babycare.Menu_Record.Mommenu.MommenuActivity;
import com.example.babycare.Menu_Record.Sidedish.SidedishActivity;
import com.example.babycare.Menu_Record.Snacks.SnacksActivity;
import com.example.babycare.Menu_recommand.MenuActivity;
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


public class RecordActivity extends AppCompatActivity {


    private CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8;
    private Button btncal ;
    private ImageView recordVoice;
    String choose, cal;
    String strDate;
    RecordAdapter adapter;
    String str;


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

    public void gotoMenumom() {
        Intent intent = new Intent(this, MommenuActivity.class);
        startActivity(intent);
    }

    public void gotoFastfood() {
        Intent intent = new Intent(this, Page_fastfood.class);
        startActivity(intent);
    }

    public void gotoDrink() {
        Intent intent = new Intent(this, Page_drink.class);
        startActivity(intent);
    }

    public void gotoFruit() {
        Intent intent = new Intent(this, Page_fruit.class);
        startActivity(intent);
    }

    public void gotoSidedish() {
        Intent intent = new Intent(this, Page_sidedish.class);
        startActivity(intent);
    }

    public void gotoMeat() {
        Intent intent = new Intent(this, Page_meat.class);
        startActivity(intent);
    }

    public void gotoDessert() {
        //Intent intent = new Intent(this, DessertActivity.class);
        Intent intent = new Intent(this, Page_dessert.class);
        startActivity(intent);
    }

    public void gotoSnacks() {
        Intent intent = new Intent(this, Page_snack.class);
        startActivity(intent);
    }

    public void gotoCalculator() {
        Intent intent = new Intent(this, BtnCalActivity.class);
        startActivity(intent);
    }

    public void gotoRecordVoice() {
        Intent intent = new Intent(this, RecordVoiceActivity.class);
        startActivity(intent);
    }

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    List<RecordMenu> mRecord;

    ArrayList<String> allNamemenu = new ArrayList<>();
    ArrayList<String> allCalmenu = new ArrayList<>();
    AutoCompleteTextView autoallmenu;

    String stramount, strname, menuname, menucal;

    ListView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);



        Button b_Page_add = (Button) findViewById(R.id.b_Page_add);
        b_Page_add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {


                // Intent intent = new Intent(V.getContext(), Pesquisa.class);
                Intent intent = new Intent(V.getContext(), Page_add.class);
               // intent.putExtra("s_text_1", retrieveName.getText().toString().trim());
                startActivityForResult(intent, 0);


            }

        });



        BottomNavigationView navView = findViewById(R.id.navigation4);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().findItem(R.id.navigation_calculator).setChecked(true);

        btncal = (Button) findViewById(R.id.btncal);
        btncal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCalculator();
            }
        });

        cardView1 = (CardView) findViewById(R.id.card_view1);
        cardView2 = (CardView) findViewById(R.id.card_view2);
        cardView3 = (CardView) findViewById(R.id.card_view3);
        cardView4 = (CardView) findViewById(R.id.card_view4);
        cardView5 = (CardView) findViewById(R.id.card_view5);
        cardView6 = (CardView) findViewById(R.id.card_view6);
        cardView7 = (CardView) findViewById(R.id.card_view7);
        cardView8 = (CardView) findViewById(R.id.card_view8);
        recordVoice = (ImageView) findViewById(R.id.recordVoice);



        autoallmenu = (AutoCompleteTextView) findViewById(R.id.autoallmenu);

        final ArrayAdapter<String> adapter_array = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, allNamemenu);
        autoallmenu.setAdapter(adapter_array);


        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMenumom();
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFastfood();
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDrink();
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFruit();
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSidedish();
            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMeat();
            }
        });
        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDessert();
            }
        });
        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSnacks();
            }
        });
        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSnacks();
            }
        });
        recordVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRecordVoice();
            }
        });

//        recyclerView = (ListView)findViewById(R.id.listmenu);
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        linearLayoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(linearLayoutManager);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        intent = getIntent();
//        userID = intent.getStringExtra("id");

        mRecord = new ArrayList<RecordMenu>();
        reference = FirebaseDatabase.getInstance().getReference("menus");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRecord.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RecordMenu record = new RecordMenu();
                    String s1 = snapshot.toString();
                    String[] s2 = s1.split("=");
                    String[] s3 = s2[1].split(",");

                    if (s2[4] != null && s2[4].length() > 0 && s2[4].charAt(s2[4].length() - 1) == '}') {
                        s2[4] = s2[4].substring(0, s2[4].length() - 3);
                    }

                    String[] s5 = s2[3].split(",");
//                    Log.d("", "onDataChangeThird: =========================" + s2[4]);
                    record.setName(s5[0]);
                    record.setCalories((s2[4]));
//                    Log.w("", "name====" + uid);
//                    Log.d("","name-----"+addmenu.getName());

//                    Log.d("", "name: =========================" + s5[0]);
                    allNamemenu.add(s5[0]);

                    allCalmenu.add(s2[4]);

                    mRecord.add(record);
//                    Log.d("","name----:"+mAddmenus);
//                    addmenumomAdapter = new AddmenumomAdapter(AddmenumomActivity.this, mAddmenus);
//                    recyclerView.setAdapter((ListAdapter) addmenumomAdapter);




                }
//                Log.d("","size---"+allNamemenu.get(allNamemenu.size()-1));
//                Log.d("","size---"+allCalmenu);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        autoallmenu.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                str = String.valueOf(autoallmenu.getText());
                Log.d("test", "name = " + str);

                for (int i=0; i < allNamemenu.size(); i++){
                    if (str.equals(allNamemenu.get(i))){
                        choose = allNamemenu.get(i);
                        cal = allCalmenu.get(i);

                    }
                    position++;

                }
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
                strDate = mdformat.format(calendar.getTime());
                ShowDialog();
            }
        });



    }

    private TextView textview_seekbar, persen;
    private BubbleSeekBar bubbleSeekBar;
    private String numper;


    public void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
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
                Toast.makeText(RecordActivity.this, "บันทึกอาหารของลูกน้อยเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();

                reference = FirebaseDatabase.getInstance().getReference();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", firebaseUser.getUid());
                hashMap.put("name", choose);
                hashMap.put("calories", cal);
                hashMap.put("date", strDate);
                hashMap.put("quantity", numper);

                reference.child("Calculators").push().setValue(hashMap);
                autoallmenu.getText().clear();
            }
        });


        builder.setNegativeButton("แก้ไข", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LayoutInflater.from(RecordActivity.this).inflate(R.layout.activity_dessert, null);
                autoallmenu.getText().clear();

            }
        });
        builder.setView(Viewlayout);
        builder.create();
        builder.show();

    }
}
