package com.example.babycare.Menu_Record.Mommenu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.Page_add;
import com.example.babycare.Page_detail;
import com.example.babycare.Page_search;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddmenumomActivity extends AppCompatActivity {

    private EditText edittxt, edtamount, edtName;
    private TextView namemenu,txtsum;
    private ImageButton ok;
    private AutoCompleteTextView automenu;
    private Button btnfinish;

    FirebaseUser firebaseUser;

    DatabaseReference reference,reference2;
    String userID;

    AddmenumomAdapter addmenumomAdapter;
    List<Addmenu> mAddmenus;

    ArrayList<String> nameMenumom = new ArrayList<>();
    ArrayList<String> calMenumom = new ArrayList<>();

    ArrayList<String> proMenumom = new ArrayList<>();

    ArrayList<String> additem = new ArrayList<>();
    ArrayList<Float> amountCal = new ArrayList<>();
    Intent intent;
//    ArrayAdapter<String> adapter;

    float sumCal, amount, calnum;







    String stramount,strname,strresult, menuname, menucal;

    ListView recyclerView;


    //    private static final String[] menuFood = new String[]{
//            "เนื้อไก่", "เนื้อหมู", "เนื้อปลา", "เนื้อวัว", "เนื้อแกะ"
//    };
    DatabaseReference mDatabase;
    DatabaseReference mDatabase2;

    //////////////////////////////////////////////////
    float protein_number;
    float sum_protein;

    String string_protein;
    String menuPro;
    TextView text_sum_protein;

    TextView text_show;
    private String total_amount;
////////////////////////////////////////////////////
int maxid = 0;

String total_calories;
TextView text_total_calories;

   private  DatabaseReference mDatabaseNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmenumom);


        Button b_Page_search = (Button) findViewById(R.id.b_Page_search);
        b_Page_search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {


                // Intent intent = new Intent(V.getContext(), Pesquisa.class);
                Intent intent = new Intent(V.getContext(), Page_search.class);
                // intent.putExtra("s_text_1", retrieveName.getText().toString().trim());
                startActivityForResult(intent, 0);


            }

        });




        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().getRoot().child("sum1");
        dbNode.setValue(null);
//        mDatabaseNew = FirebaseDatabase.getInstance().getReference().child("Cart");
//        mDatabaseNew.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            int sum=0;
//            for (DataSnapshot ds : dataSnapshot.getChildren()){
//
//                Map<String,Object> map = (Map<String, Object>) ds.getValue();
//                Object price = map.get("Price");
//                int pValue = Integer.parseInt(String.valueOf(price));
//                sum += pValue;
//
//                text_total_calories.setText(String.valueOf(sum));
//            }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        mDatabaseNew = FirebaseDatabase.getInstance().getReference().child("Cart");
//        mDatabaseNew.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                int sum=0;
//                for (DataSnapshot ds : dataSnapshot.getChildren()){
//
//                    Map<String,Object> map = (Map<String, Object>) ds.getValue();
//                    Object price = map.get("Price");
//                   // int pValue = Integer.parseInt(String.valueOf(price));
//                    int pValue = Integer.parseInt(String.valueOf(price));
//                    sum += pValue;
//
//                    text_total_calories.setText(String.valueOf(sum));
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


//        mDatabaseNew = FirebaseDatabase.getInstance().getReference().child("sum1");
//        mDatabaseNew.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                int sum=0;
//                for (DataSnapshot ds : dataSnapshot.getChildren()){
//
//                    Map<String,Object> map = (Map<String, Object>) ds.getValue();
//                    Object price = map.get("Cal");
//                    // int pValue = Integer.parseInt(String.valueOf(price));
//                    int pValue = Integer.parseInt(String.valueOf(price));
//                    sum += pValue;
//
//                    text_total_calories.setText(String.valueOf(sum));
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        mDatabase= FirebaseDatabase.getInstance("https://babycare-87875.firebaseio.com").getReference().child("food");
        mDatabase2= FirebaseDatabase.getInstance("https://babycare-87875.firebaseio.com").getReference().child("sum1");

        text_total_calories = (TextView) findViewById(R.id.text_total_calories);
        text_show = (TextView) findViewById(R.id.text_show);

        edtName = (EditText)findViewById(R.id.edit_name);
        edittxt = (EditText) findViewById(R.id.edit_name);
        namemenu = (TextView) findViewById(R.id.namemenu);
        automenu = (AutoCompleteTextView) findViewById(R.id.add);
        edtamount = (EditText) findViewById(R.id.amount);

        txtsum = (TextView) findViewById(R.id.sumCal);
        text_sum_protein = (TextView) findViewById(R.id.text_sum_protein);

        btnfinish = (Button)findViewById(R.id.finish);
        ok = (ImageButton) findViewById(R.id.addbtn);

        recyclerView = (ListView)findViewById(R.id.listmenu);
//        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(linearLayoutManager);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        intent = getIntent();
//        userID = intent.getStringExtra("id");


        reference = FirebaseDatabase.getInstance().getReference("sum1");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Addmenu addmenu = dataSnapshot.getValue(Addmenu.class);
//                Log.d("","name----"+addmenu.getName());
               // readMessage(firebaseUser.getUid());


//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot snapshot :
//                            dataSnapshot.getChildren()) {
//                        try {
//                            Log.d("debuug", String.valueOf(snapshot.child("Name").getValue()) + " "+ String.valueOf(snapshot.child("Cal").getValue()));
//
//
//                            text_total_calories.setText(""+total_calories);
//
//
//                        } catch (NumberFormatException e) {
//                            Log.d("debuug", "Eingabe nicht valide");
//                        }
//                    }
//                }


//                int totalPunkte = 0;
//                for(DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
//                    for(DataSnapshot ds: dSnapshot.child("sum1").getChildren()) {
//                        int punkte = ds.child("Cal").getValue(Integer.class);
//                        totalPunkte = totalPunkte + punkte;
//
//
//                        text_total_calories.setText(""+totalPunkte);
//
//
//                    }
//                }


                String totalPunkte ;
                for(DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
                    for(DataSnapshot ds: dSnapshot.child("sum1").getChildren()) {
                        String punkte = ds.child("Cal").getValue(String.class);
                       // totalPunkte = totalPunkte + punkte;


                      //  text_total_calories.setText(""+punkte);


                    }
                }

                //Log.d("TAG", totalPunkte);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference usersRef = rootRef.child("sum1");
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String total_Punkte ="0";
//                for(DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
//                    for(DataSnapshot ds: dSnapshot.child("sum1").getChildren()) {
//                        String punkte = ds.child("Cal").getValue(String.class);
//                      // total_Punkte = totalPunkte + punkte;
//
//                        text_total_calories.setText(""+punkte);
//
//                    }
//                }
//              //  Log.d("TAG", totalPunkte);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };
//        usersRef.addListenerForSingleValueEvent(eventListener);

////////////////////////////////////////////////////////////////////////////
//
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference unicaribeRef = rootRef.child("sum1");
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                int total = 0;
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                    int value = ds.getValue(Integer.class);
//                    total =+ value;
//                }
//                Log.d("TAG", String.valueOf(total));
//
//                text_total_calories.setText(""+total);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };
//        unicaribeRef.addListenerForSingleValueEvent(eventListener);



   ///////////////////////////////////////////////////////////////////////////

        reference = FirebaseDatabase.getInstance().getReference("rawmaterials2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Addmenu addmenu = dataSnapshot.getValue(Addmenu.class);
//                Log.d("","name----"+addmenu.getName());
                readMessage(firebaseUser.getUid());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final ArrayAdapter<String> adapter_array = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, nameMenumom);
        automenu.setAdapter(adapter_array);


        edittxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                namemenu.setText(edittxt.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, additem);



//        if(automenu.getText()==null){
//
//        }else{
//            automenu.setText("");
//        }



        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                additem.add(" " + automenu.getText().toString() + " ");
                adapter.notifyDataSetInvalidated();
                recyclerView.setAdapter(adapter);

                strname = automenu.getText().toString();

                stramount = edtamount.getText().toString().trim();

               // Double d_new = Double.valueOf(edtamount.getText().toString().trim());
                Double d_new = Double.valueOf(edtamount.getText().toString().trim());

                //text_show.setText(""+additem);

                amount = Float.parseFloat(stramount);
                Log.d("","str----"+strname);
                Log.d("","calUser----"+stramount);

                for (int i=0; i < nameMenumom.size(); i++){
                    if (nameMenumom.get(i).equals(strname)){
                        menucal = calMenumom.get(i);
                    }
                }
                Log.d("","cal---"+menucal);

                text_show.setText(""+menucal);


                final String cal = text_show.getText().toString();
                //int i_new = Integer.valueOf(text_show.getText().toString().trim());
                ////////////////////////


                mDatabaseNew = FirebaseDatabase.getInstance().getReference().child("sum1");
                mDatabaseNew.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int sum=0;
                        for (DataSnapshot ds : dataSnapshot.getChildren()){

                            Map<String,Object> map = (Map<String, Object>) ds.getValue();
                            Object price = map.get("Cal");
                            // int pValue = Integer.parseInt(String.valueOf(price));
                            int pValue = Integer.parseInt(String.valueOf(price));
                            sum += pValue;

                            text_total_calories.setText(String.valueOf(sum));
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




                mDatabase2.orderByChild("Name").equalTo(cal).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            Toast.makeText(Page_detail.this, "Entered information already exist", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
                        //Toast.makeText(InsertActivity.this, "Entered information is not exist", Toast.LENGTH_SHORT).show();
                        HashMap<String,String> datamap= new HashMap<String, String>();


                        datamap.put("Name","s_name");
                        datamap.put("Cal",cal);


                        // text_show.setText(""+Integer.parseInt(String.valueOf(dataSnapshot .child("Cal").getValue())));

                      //  total_calories += String.valueOf(dataSnapshot .child("Cal").getValue());

                      //  text_total_calories.setText(""+total_calories);

                        mDatabase2.push().setValue(datamap);
                        Toast.makeText(AddmenumomActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                        //  }
                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

             //   myRef.child("new1").child(String.valueOf(maxid + 1)).setValue(text_show.getText().toString());

                //////////////////////////

                calnum = Float.parseFloat(menucal);
                sumCal += (amount*calnum)/100;
                strresult = String.valueOf(sumCal);
                txtsum.setText("รวม "+strresult+" กิโลแคลอรี่");

                automenu.getText().clear();
                edtamount.getText().clear();

                //////////////////////////////////////////////////////////////////


                //  strname = automenu.getText().toString();

                // stramount = edtamount.getText().toString().trim();
                // amount = Float.parseFloat(stramount);

//
//                for (int i=0; i < nameMenumom.size(); i++){
//                    if (nameMenumom.get(i).equals(strname)){
//                        menuPro = proMenumom.get(i);
//                    }
//                }
//
//
//                protein_number = Float.parseFloat(menuPro);
//                sum_protein += (amount*protein_number)/100;
//                string_protein = String.valueOf(sum_protein);
//                text_sum_protein.setText("รวม "+string_protein+"p");

                //  automenu.getText().clear();
                //  edtamount.getText().clear();


                //////////////////////////////////////////////////////////////////
            }
        });

        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", firebaseUser.getUid());
                hashMap.put("name", edtName.getText().toString());
                hashMap.put("calories", strresult);
                hashMap.put("protein", string_protein);

                reference.child("MenuMom2").push().setValue(hashMap);


                ///////////////////////////////////////////////



                final String s_name = edtName.getText().toString();
                final String s_calories = strresult;




                mDatabase.orderByChild("Name").equalTo(s_name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            Toast.makeText(Page_detail.this, "Entered information already exist", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
                        //Toast.makeText(InsertActivity.this, "Entered information is not exist", Toast.LENGTH_SHORT).show();
                        HashMap<String,String> datamap= new HashMap<String, String>();


                        datamap.put("Name",s_name);
                        datamap.put("Cal",s_calories);


                       // text_show.setText(""+Integer.parseInt(String.valueOf(dataSnapshot .child("Cal").getValue())));




                        mDatabase.push().setValue(datamap);
                        Toast.makeText(AddmenumomActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                        //  }
                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



///////////////////////////////////////////////////////////////

                Intent i = new Intent(AddmenumomActivity.this, MommenuActivity.class);
                startActivity(i);
            }

        });


    }
    String mName;
    String mCal;

    private void readMessage(final String uid) {

        mAddmenus = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("rawmaterials2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAddmenus.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Addmenu addmenu = snapshot.getValue(Addmenu.class);
                    String s1 = snapshot.toString();
                    String[] s2 = s1.split("=");
                    String[] s3 = s2[1].split(",");
//                    Log.d("", "onDataChangeFirst: =========================" + s1);
//                    String []s4 = s3[0].split(" ");
//                    String []s4 = s3[0].split(",");

//                  String[] s5 = s2[3].split(",");
//                  Log.d("", "onDataChangeSec: =========================" + s5[0]);
                    if (s2[4] != null && s2[4].length() > 0 && s2[4].charAt(s2[4].length() - 1) == '}') {
                        s2[4] = s2[4].substring(0, s2[4].length() - 3);
                    }
//                  String []s6 = s2[4].split(",");
                    Log.d("", "onDataChangeThird: =========================" + s2[4]);
//                  addmenu.setName(s5[0]);
                    addmenu.setProtein((s2[4]));
//                    Log.w("", "name====" + uid);
//                    Log.d("","name-----"+addmenu.getName());
                    mName = addmenu.getName();
                    nameMenumom.add(mName);

                    mCal = addmenu.getCal();
                    calMenumom.add(mCal);

                    mAddmenus.add(addmenu);
//                    Log.d("","name----:"+mAddmenus);
//                    addmenumomAdapter = new AddmenumomAdapter(AddmenumomActivity.this, mAddmenus);
//                    recyclerView.setAdapter((ListAdapter) addmenumomAdapter);

                  //  total_amount += String.valueOf(dataSnapshot .child("calories").getValue());

                }
//                Log.d("","size---"+nameMenumom.size());
               // text_show.setText(""+total_amount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
