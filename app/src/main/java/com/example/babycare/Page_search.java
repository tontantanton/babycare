package com.example.babycare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.Menu_Record.Mommenu.AddmenumomActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Page_search extends AppCompatActivity {
    FirebaseUser firebaseUser;


    String userID;
    ////////////////////////////////////////////////////////
    TextView text_user_id;
    /////////////////////////////////////////////


    ////////////////////////
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    // DatabaseReference mRecipeRef = mRootRef.child("recipes");
    DatabaseReference mRecipeRef = mRootRef.child("rawmaterials2");

    ListView listView;
    EditText searchField;
    Button button;
    List<Recipes> rowItems;
    ArrayList<String> searchedIngredients;

    private static final String TAG = "Chiz";


    ////////////////////////////
    private DatabaseReference mDatabaseNew;

    DatabaseReference reference;

    EditText ed_name_menu;
    DatabaseReference mDatabase2;
    DatabaseReference mDatabase3;
    DatabaseReference mDatabase4;


    TextView text_total_calories;
    TextView text_total_protein;
    TextView text_total_vitamina;
    TextView text_total_vitaminc;
    TextView text_total_vitaminb6;
    TextView text_total_vitaminb12;
    TextView text_total_calcium;
    TextView text_total_iodine;
    TextView text_total_iron;


    String s_calories;
    String s_protein;
    String s_vitamina;
    String s_vitaminc;
    String s_vitaminb6;
    String s_vitaminb12;
    String s_calcium;
    String s_iodine;
    String s_iron;


    TextView text_s_calories;
    TextView text_s_protein;
    TextView text_s_vitamina;
    TextView text_s_vitaminc;
    TextView text_s_vitaminb6;
    TextView text_s_vitaminb12;
    TextView text_s_calcium;
    TextView text_s_iodine;
    TextView text_s_iron;
    /////////////////////////
EditText ed_text_1;


/////////////////////////

   // float amount, calnum;
   // float calories_number;
   float  calories_number;
    float sumCal;
    float amount;
    TextView text_s_result_calories;
    String  s_result_calories;
/////////////////////////
    float amount_2;
    float cal_2;
    double sum_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_search);

        ed_name_menu = (EditText) findViewById(R.id.ed_name_menu);




// add menu mom bt
        Button b_action_1 = (Button) findViewById(R.id.b_action_1);
        b_action_1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {


//                reference = FirebaseDatabase.getInstance().getReference();
//                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put("id", firebaseUser.getUid());
//                hashMap.put("name", ed_name_menu.getText().toString()).trim();
//                hashMap.put("calories", text_total_calories.getText().toString()).trim();
//                hashMap.put("protein", text_total_protein.getText().toString()).trim();
//                hashMap.put("vitamina", text_total_vitamina.getText().toString()).trim();
//                hashMap.put("vitaminc", text_total_vitaminc.getText().toString()).trim();
//                hashMap.put("vitaminb6", text_total_vitaminb6.getText().toString()).trim();
//                hashMap.put("vitaminb12", text_total_vitaminb12.getText().toString()).trim();
//                hashMap.put("calcium", text_total_calcium.getText().toString()).trim();
//                hashMap.put("iodine", text_total_iodine.getText().toString()).trim();
//                hashMap.put("iron", text_total_iron.getText().toString()).trim();
//
//                reference.child("MenuMom2").push().setValue(hashMap);

                ////////////////////////////////////////

                mDatabase3 = FirebaseDatabase.getInstance("https://babycare-87875.firebaseio.com").getReference().child("MenuMom");


                //  text_calories.setText("" + s_calories);
                //  text_protein.setText("" + s_protein);
//                            text_vitamina.setText("" + s_vitamina);
//                            text_vitaminc.setText("" + s_vitaminc);
//                            text_vitaminb6.setText("" + s_vitaminb6);
//                            text_vitaminb12.setText("" + s_vitaminb12);
//                            text_calcium.setText("" + s_calcium);
//                            text_iodine.setText("" + s_iodine);
//                            text_iron.setText("" + s_iron);

                //                           final String cal = text_calories.getText().toString();
                //  protein = text_protein.getText().toString();



                mDatabase3.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            Toast.makeText(Page_detail.this, "Entered information already exist", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
                        //Toast.makeText(InsertActivity.this, "Entered information is not exist", Toast.LENGTH_SHORT).show();
                        HashMap<String, String> datamap = new HashMap<String, String>();

                        String s_name = ed_name_menu.getText().toString().trim();
                        String ss_calories = text_total_calories.getText().toString().trim();
                        String ss_protein = text_total_protein.getText().toString().trim();
                        String ss_vitamina = text_total_vitamina.getText().toString().trim();
                        String ss_vitaminc = text_total_vitaminc.getText().toString().trim();
                        String ss_vitaminb6 = text_total_vitaminb6.getText().toString().trim();
                        String ss_vitaminb12 = text_total_vitaminb12.getText().toString().trim();
                        String ss_calcium = text_total_calcium.getText().toString().trim();
                        String ss_iodine = text_total_iodine.getText().toString().trim();
                        String ss_iron = text_total_iron.getText().toString().trim();

                       datamap.put("name", s_name);
//                        datamap.put("calories", s_calories);
//                        datamap.put("protein", s_protein);
//                        datamap.put("vitamina", s_vitamina);
//                        datamap.put("vitaminc", s_vitaminc);
//                        datamap.put("vitaminb6", s_vitaminb6);
//                        datamap.put("vitaminb12", s_vitaminb12);
//                        datamap.put("calcium", s_calcium);
//                        datamap.put("iodine", s_iodine);
//                        datamap.put("iron", s_iron);
//                        datamap.put("id", userID);
                        datamap.put("calories", ss_calories);
                        datamap.put("protein", ss_protein);
                        datamap.put("vitamina", ss_vitamina);
                        datamap.put("vitaminc", ss_vitaminc);
                        datamap.put("vitaminb6", ss_vitaminb6);
                        datamap.put("vitaminb12", ss_vitaminb12);
                        datamap.put("calcium", ss_calcium);
                        datamap.put("iodine", ss_iodine);
                        datamap.put("iron", ss_iron);
                        datamap.put("id", userID);


                        // text_show.setText(""+Integer.parseInt(String.valueOf(dataSnapshot .child("Cal").getValue())));

                        //  total_calories += String.valueOf(dataSnapshot .child("Cal").getValue());

                        //  text_total_calories.setText(""+total_calories);

                        mDatabase3.push().setValue(datamap);
                        Toast.makeText(Page_search.this, "Data inserted", Toast.LENGTH_SHORT).show();
                        //  }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                // Intent intent = new Intent(getApplicationContext(), SpecificRecipe.class);
                // intent.putExtra("url", recipe_url);
                //  startActivity(intent);
                //  text_total_calories.setText(String.valueOf(calories));


                //  Toast.makeText(Page_search.this, ""+calories, Toast.LENGTH_SHORT).show();






                /////////////////////////////////////////
                mDatabase4 = FirebaseDatabase.getInstance("https://babycare-87875.firebaseio.com").getReference().child("food");


                //  text_calories.setText("" + s_calories);
                //  text_protein.setText("" + s_protein);
//                            text_vitamina.setText("" + s_vitamina);
//                            text_vitaminc.setText("" + s_vitaminc);
//                            text_vitaminb6.setText("" + s_vitaminb6);
//                            text_vitaminb12.setText("" + s_vitaminb12);
//                            text_calcium.setText("" + s_calcium);
//                            text_iodine.setText("" + s_iodine);
//                            text_iron.setText("" + s_iron);

                //                           final String cal = text_calories.getText().toString();
                //  protein = text_protein.getText().toString();



                mDatabase4.orderByChild("Name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            Toast.makeText(Page_detail.this, "Entered information already exist", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
                        //Toast.makeText(InsertActivity.this, "Entered information is not exist", Toast.LENGTH_SHORT).show();
                        HashMap<String, String> datamap = new HashMap<String, String>();

                        String s_name = ed_name_menu.getText().toString().trim();
                        String ss_calories = text_total_calories.getText().toString().trim();
                        String ss_protein = text_total_protein.getText().toString().trim();
                        String ss_vitamina = text_total_vitamina.getText().toString().trim();
                        String ss_vitaminc = text_total_vitaminc.getText().toString().trim();
                        String ss_vitaminb6 = text_total_vitaminb6.getText().toString().trim();
                        String ss_vitaminb12 = text_total_vitaminb12.getText().toString().trim();
                        String ss_calcium = text_total_calcium.getText().toString().trim();
                        String ss_iodine = text_total_iodine.getText().toString().trim();
                        String ss_iron = text_total_iron.getText().toString().trim();

                        datamap.put("name", s_name);
//                        datamap.put("calories", s_calories);
//                        datamap.put("protein", s_protein);
//                        datamap.put("vitamina", s_vitamina);
//                        datamap.put("vitaminc", s_vitaminc);
//                        datamap.put("vitaminb6", s_vitaminb6);
//                        datamap.put("vitaminb12", s_vitaminb12);
//                        datamap.put("calcium", s_calcium);
//                        datamap.put("iodine", s_iodine);
//                        datamap.put("iron", s_iron);
//                        datamap.put("id", userID);
                        datamap.put("calories", ss_calories);
                        datamap.put("protein", ss_protein);
                        datamap.put("vitamina", ss_vitamina);
                        datamap.put("vitaminc", ss_vitaminc);
                        datamap.put("vitaminb6", ss_vitaminb6);
                        datamap.put("vitaminb12", ss_vitaminb12);
                        datamap.put("calcium", ss_calcium);
                        datamap.put("iodine", ss_iodine);
                        datamap.put("iron", ss_iron);
                      //  datamap.put("id", userID);


                        // text_show.setText(""+Integer.parseInt(String.valueOf(dataSnapshot .child("Cal").getValue())));

                        //  total_calories += String.valueOf(dataSnapshot .child("Cal").getValue());

                        //  text_total_calories.setText(""+total_calories);

                        mDatabase4.push().setValue(datamap);
                        Toast.makeText(Page_search.this, "Data inserted", Toast.LENGTH_SHORT).show();
                        //  }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                /////////////////////////////////////////////


            }

        });


        // firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
//        intent = getIntent();
//        userID = intent.getStringExtra("id");


        text_user_id = (TextView) findViewById(R.id.text_user_id);


        text_user_id.setText("" + userID);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
////////////////////////////////////////////////////////////////////////////////

        listView = (ListView) findViewById(R.id.recipe_list);
        searchField = (EditText) findViewById(R.id.search_field);
        button = (Button) findViewById(R.id.search_button);


        text_total_calories = (TextView) findViewById(R.id.text_total_calories);
        text_total_protein = (TextView) findViewById(R.id.text_total_protein);
        text_total_vitamina = (TextView) findViewById(R.id.text_total_vitamina);
        text_total_vitaminc = (TextView) findViewById(R.id.text_total_vitaminc);
        text_total_vitaminb6 = (TextView) findViewById(R.id.text_total_vitaminb6);
        text_total_vitaminb12 = (TextView) findViewById(R.id.text_total_vitaminb12);
        text_total_calcium = (TextView) findViewById(R.id.text_total_calcium);
        text_total_iodine = (TextView) findViewById(R.id.text_total_iodine);
        text_total_iron = (TextView) findViewById(R.id.text_total_iron);

        text_s_calories = (TextView) findViewById(R.id.text_s_calories);
        text_s_protein = (TextView) findViewById(R.id.text_s_protein);
        text_s_vitamina = (TextView) findViewById(R.id.text_s_vitamina);
        text_s_vitaminc = (TextView) findViewById(R.id.text_s_vitaminc);
        text_s_vitaminb6 = (TextView) findViewById(R.id.text_s_vitaminb6);
        text_s_vitaminb12 = (TextView) findViewById(R.id.text_s_vitaminb12);
        text_s_calcium = (TextView) findViewById(R.id.text_s_calcium);
        text_s_iodine = (TextView) findViewById(R.id.text_s_iodine);
        text_s_iron = (TextView) findViewById(R.id.text_s_iron);


        ed_text_1 = (EditText) findViewById(R.id.ed_text_1);

       text_s_result_calories = (TextView) findViewById(R.id.text_s_result_calories);

//      String  amount  = ed_text_1.getText().toString().trim();
//      Float amount2 = Float.parseFloat(amount);
//       // Float  calories_number  = text_s_calories.getText().toString().trim();
//      //  String  s_result_calories;
//
//        calories_number = Float.parseFloat(text_s_calories.getText().toString().trim());
//        sumCal += (amount2*calories_number)/100;
//
//        s_result_calories = String.valueOf(sumCal);
//        text_s_result_calories.setText(""+s_result_calories);
     //   double num1  = Double.parseDouble(ed_text_1.getText().toString().trim()); //amount ที่กรอก

      //  double num2 = 2.0; //


       // double s_result_calories  = (num1 * num2)/100;

        // s_result_calories = String.valueOf(sumCal);
      //  text_s_result_calories.setText(""+s_result_calories);
//       // final String  amount    = "10";
//        final String  amount    =  ed_text_1.getText().toString().trim();
//        final String  cal_1     = text_s_calories.getText().toString().trim();
//        amount_2 =  Float.parseFloat(amount);
//        cal_2     = Float.parseFloat(cal_1);
//        sum_2 = (amount_2*cal_2)/100;
//
//        text_s_result_calories.setText(""+sum_2);

        this.getData();


        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().getRoot().child("sum1");

        dbNode.setValue(null); // clear table

        //show
        mDatabaseNew = FirebaseDatabase.getInstance().getReference().child("sum1");
        mDatabaseNew.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int sum_calories = 0;
                int sum_protein = 0;
                int sum_vitamina = 0;
                int sum_vitaminc = 0;
                int sum_vitaminb6 = 0;
                int sum_vitaminb12 = 0;
                int sum_calcium = 0;
                int sum_iodine = 0;
                int sum_iron = 0;


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object calories = map.get("calories");
                    Object protein = map.get("protein");
                    Object vitamina = map.get("vitamina");
                    Object vitaminc = map.get("vitaminc");
                    Object vitaminb6 = map.get("vitaminb6");
                    Object vitaminb12 = map.get("vitaminb12");
                    Object calcium = map.get("calcium");
                    Object iodine = map.get("iodine");
                    Object iron = map.get("iron");


                    int p_calories = Integer.parseInt(String.valueOf(calories));
                    int p_protein = Integer.parseInt(String.valueOf(protein));
                    int p_vitamina = Integer.parseInt(String.valueOf(vitamina));
                    int p_vitaminc = Integer.parseInt(String.valueOf(vitaminc));
                    int p_vitaminb6 = Integer.parseInt(String.valueOf(vitaminb6));
                    int p_vitaminb12 = Integer.parseInt(String.valueOf(vitaminb12));
                    int p_calcium = Integer.parseInt(String.valueOf(calcium));
                    int p_iodine = Integer.parseInt(String.valueOf(iodine));
                    int p_iron = Integer.parseInt(String.valueOf(iron));

                    sum_calories += p_calories;
                    sum_protein += p_protein;
                    sum_vitamina += p_vitamina;
                    sum_vitaminc += p_vitaminc;
                    sum_vitaminb6 += p_vitaminb6;
                    sum_vitaminb12 += p_vitaminb12;
                    sum_calcium += p_calcium;
                    sum_iodine += p_iodine;
                    sum_iron += p_iron;


                    text_total_calories.setText(String.valueOf(sum_calories));
                    text_total_protein.setText(String.valueOf(sum_protein));
                    text_total_vitamina.setText(String.valueOf(sum_vitamina));
                    text_total_vitaminc.setText(String.valueOf(sum_vitaminc));
                    text_total_vitaminb6.setText(String.valueOf(sum_vitaminb6));
                    text_total_vitaminb12.setText(String.valueOf(sum_vitaminb12));
                    text_total_calcium.setText(String.valueOf(sum_calcium));
                    text_total_iodine.setText(String.valueOf(sum_iodine));
                    text_total_iron.setText(String.valueOf(sum_iron));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





//        mDatabaseNew = FirebaseDatabase.getInstance().getReference().child("sum1");
//        mDatabaseNew.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                int sum_calories = 0;
//                int sum_protein = 0;
//                int sum_vitamina = 0;
//                int sum_vitaminc = 0;
//                int sum_vitaminb6 = 0;
//                int sum_vitaminb12 = 0;
//                int sum_calcium = 0;
//                int sum_iodine = 0;
//                int sum_iron = 0;
//
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
//                    Object calories = map.get("Cal");
//                    Object protein = map.get("protein");
//                    Object vitamina = map.get("vitamina");
//                    Object vitaminb6 = map.get("vitaminb6");
//                    Object vitaminb12 = map.get("vitmainb12");
//                    Object calcium = map.get("calcium");
//                    Object vitaminc = map.get("vitaminc");
//                    Object iodine = map.get("iodine");
//                    Object iron = map.get("iron");
//
//
//                    // int pValue = Integer.parseInt(String.valueOf(price));
//                    int p_calories = Integer.parseInt(String.valueOf(calories));
//                    int p_protein = Integer.parseInt(String.valueOf(protein));
//                    int p_vitamina = Integer.parseInt(String.valueOf(vitamina));
//                    int p_vitaminc = Integer.parseInt(String.valueOf(vitaminc));
//                    int p_vitaminb6 = Integer.parseInt(String.valueOf(vitaminb6));
//                    int p_vitaminb12 = Integer.parseInt(String.valueOf(vitaminb12));
//                    int p_calcium = Integer.parseInt(String.valueOf(calcium));
//                    int p_iodine = Integer.parseInt(String.valueOf(iodine));
//                    int p_iron = Integer.parseInt(String.valueOf(iron));
//                    sum_calories += p_calories;
//                    sum_protein += p_protein;
//                    sum_vitamina += p_vitamina;
//                    sum_vitaminc += p_vitaminc;
//                    sum_vitaminb6 += p_vitaminb6;
//                    sum_vitaminb12 += p_vitaminb12;
//                    sum_calcium += p_calcium;
//                    sum_iodine += p_iodine;
//                    sum_iron += p_iron;
//
//                    text_total_calories.setText(String.valueOf(sum_calories));
//                    text_total_protein.setText(String.valueOf(sum_protein));
//                    text_total_vitamina.setText(String.valueOf(sum_vitamina));
//                    text_total_vitaminc.setText(String.valueOf(sum_vitaminc));
//                    text_total_vitaminb6.setText(String.valueOf(sum_vitaminb6));
//                    text_total_vitaminb12.setText(String.valueOf(sum_vitaminb12));
//                    text_total_calcium.setText(String.valueOf(sum_calcium));
//                    text_total_iodine.setText(String.valueOf(sum_iodine));
//                    text_total_iron.setText(String.valueOf(sum_iron));
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    public void getData() {
        mRecipeRef.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rowItems = new ArrayList<Recipes>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final String recipeKey = postSnapshot.getKey();
                    final String name = postSnapshot.child("name").getValue(String.class);
                    final String calories = postSnapshot.child("calories").getValue(String.class);
                    final String protein = postSnapshot.child("protein").getValue(String.class);
                    final String vitamina = postSnapshot.child("vitamina").getValue(String.class);
                    final String vitaminc = postSnapshot.child("vitaminc").getValue(String.class);
                    final String vitaminb6 = postSnapshot.child("vitaminb6").getValue(String.class);
                    final String vitaminb12 = postSnapshot.child("vitaminb12").getValue(String.class);
                    final String calcium = postSnapshot.child("calcium").getValue(String.class);
                    final String iodine = postSnapshot.child("iodine").getValue(String.class);
                    final String iron = postSnapshot.child("iron").getValue(String.class);


                    Recipes item = new Recipes(recipeKey, name, calories, protein, vitamina, vitaminc, vitaminb6, vitaminb12, calcium
                            , iodine, iron);
                    rowItems.add(item);

                    RecipeListAdapter adapter = new RecipeListAdapter(getApplicationContext(), rowItems);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                   //  Intent newActivity = new Intent(Page_search.this, DialogActivity_1.class);
                   // newActivity.putExtra("name", name);
                   // startActivity(newActivity);


                            TextView text_calories = (TextView) view.findViewById(R.id.text_calories);
                            TextView text_protein = (TextView) view.findViewById(R.id.text_protein);
                            TextView text_vitamina = (TextView) view.findViewById(R.id.text_vitamina);
                            TextView text_vitaminc = (TextView) view.findViewById(R.id.text_vitaminc);
                            TextView text_vitaminb6 = (TextView) view.findViewById(R.id.text_vitaminb6);
                            TextView text_vitaminb12 = (TextView) view.findViewById(R.id.text_vitaminb12);
                            TextView text_calcium = (TextView) view.findViewById(R.id.text_calcium);
                            TextView text_iodine = (TextView) view.findViewById(R.id.text_iodine);
                            TextView text_iron = (TextView) view.findViewById(R.id.text_iron);



                            //  String s_calories = text_calories.getText().toString().trim();
                            s_calories = text_calories.getText().toString().trim();
                            s_protein = text_protein.getText().toString().trim();
                            s_vitamina = text_vitamina.getText().toString().trim();
                            s_vitaminc = text_vitaminc.getText().toString().trim();
                            s_vitaminb6 = text_vitaminb6.getText().toString().trim();
                            s_vitaminb12 = text_vitaminb12.getText().toString().trim();
                            s_calcium = text_calcium.getText().toString().trim();
                            s_iodine = text_iodine.getText().toString().trim();
                            s_iron = text_iron.getText().toString().trim();

                            // TextView text_protein = (TextView) view.findViewById(R.id.text_protein);
//                            TextView text_vitamina = (TextView) view.findViewById(R.id.text_vitamina);
//                            TextView text_vitaminc = (TextView) view.findViewById(R.id.text_vitaminc);
//                            TextView text_vitaminb6 = (TextView) view.findViewById(R.id.text_vitaminb6);
//                            TextView text_vitaminb12 = (TextView) view.findViewById(R.id.text_vitaminb12);
//                            TextView text_calcium = (TextView) view.findViewById(R.id.text_calcium);
//                            TextView text_iodine = (TextView) view.findViewById(R.id.text_iodine);
//                            TextView text_iron = (TextView) view.findViewById(R.id.text_iron);

                            // Toast.makeText(Page_search.this, ""+s_calories, Toast.LENGTH_SHORT).show();

                            text_s_calories.setText(s_calories);
                            text_s_protein.setText(s_protein);
                            text_s_vitamina.setText(s_vitamina);
                            text_s_vitaminc.setText(s_vitaminc);
                            text_s_vitaminb6.setText(s_vitaminb6);
                            text_s_vitaminb12.setText(s_vitaminb12);
                            text_s_calcium.setText(s_calcium);
                            text_s_iodine.setText(s_iodine);
                            text_s_iron.setText(s_iron);


                          //  final String  amount    = "10";
                            final String  amount    =         ed_text_1.getText().toString().trim();
                            final String  cal_1     =   text_s_calories.getText().toString().trim();
                            amount_2 =  Float.parseFloat(amount);
                            cal_2     = Float.parseFloat(cal_1);
                            sum_2 = (amount_2*cal_2)/100;

                            text_s_result_calories.setText(""+sum_2);





                            //       String calories = text_calories.getText().toString().trim();
                            //     s_calories = text_calories.getText().toString().trim();
                            //  s_protein = text_protein.getText().toString().trim();
//                            s_vitamina = text_vitamina.getText().toString().trim();
//                            s_vitaminc = text_vitaminc.getText().toString().trim();
//                            s_vitaminb6 = text_vitaminb6.getText().toString().trim();
//                            s_vitaminb12 = text_vitaminb12.getText().toString().trim();
//                            s_calcium = text_calcium.getText().toString().trim();
//                            s_iodine = text_iodine.getText().toString().trim();
//                            s_iron = text_iron.getText().toString().trim();

                            ed_text_1.getText().clear();
                           // text_s_calories.getText().clear();
                            text_s_calories.setText("");
                            //แปลกมากเขียนเหมือนกันก้ไม่ได้
//                            text_s_calories.post(() -> text_s_calories.getText().clear());
//                            text_s_calories.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    text_s_calories.getText().clear();
//                                }
//                            });
                      mDatabase2 = FirebaseDatabase.getInstance("https://babycare-87875.firebaseio.com").getReference().child("sum1");


                            //  text_calories.setText("" + s_calories);
                            //  text_protein.setText("" + s_protein);
//                            text_vitamina.setText("" + s_vitamina);
//                            text_vitaminc.setText("" + s_vitaminc);
//                            text_vitaminb6.setText("" + s_vitaminb6);
//                            text_vitaminb12.setText("" + s_vitaminb12);
//                            text_calcium.setText("" + s_calcium);
//                            text_iodine.setText("" + s_iodine);
//                            text_iron.setText("" + s_iron);

                            //                           final String cal = text_calories.getText().toString();
                            //  protein = text_protein.getText().toString();

                            mDatabase2.orderByChild("Name").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            Toast.makeText(Page_detail.this, "Entered information already exist", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
                                    //Toast.makeText(InsertActivity.this, "Entered information is not exist", Toast.LENGTH_SHORT).show();
                                    HashMap<String, String> datamap = new HashMap<String, String>();


                                    datamap.put("name", "s_name");
                                    datamap.put("calories", s_calories);
                                    datamap.put("protein", s_protein);
                                    datamap.put("vitamina", s_vitamina);
                                    datamap.put("vitaminc", s_vitaminc);
                                    datamap.put("vitaminb6", s_vitaminb6);
                                    datamap.put("vitaminb12", s_vitaminb12);
                                    datamap.put("calcium", s_calcium);
                                    datamap.put("iodine", s_iodine);
                                    datamap.put("iron", s_iron);


                                    // text_show.setText(""+Integer.parseInt(String.valueOf(dataSnapshot .child("Cal").getValue())));

                                    //  total_calories += String.valueOf(dataSnapshot .child("Cal").getValue());

                                    //  text_total_calories.setText(""+total_calories);



                                    mDatabase2.push().setValue(datamap);
                                    Toast.makeText(Page_search.this, "Data inserted", Toast.LENGTH_SHORT).show();
                                    //  }
                                }


                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            // Intent intent = new Intent(getApplicationContext(), SpecificRecipe.class);
                            // intent.putExtra("url", recipe_url);
                            //  startActivity(intent);
                            //  text_total_calories.setText(String.valueOf(calories));


                            //  Toast.makeText(Page_search.this, ""+calories, Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });





    }


//    public void searchIngredient(View view){
//        hideKeypad();
//        listView.setAdapter(null);
//        getSearchedIngredient();
//
//        mRecipeRef.orderByChild("title").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                rowItems = new ArrayList<Recipes>();
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    final String recipeKey = postSnapshot.getKey();
//                    final String title = postSnapshot.child("title").getValue(String.class);
//                    final String url = postSnapshot.child("url").getValue(String.class);
//                    final String image_url = postSnapshot.child("image_url").getValue(String.class);
//                    DatabaseReference mIngredientRef = mRootRef.child("recipes/"+recipeKey+"/content/ingredients");
//
//                    Log.d(TAG, "recipeKey: "+recipeKey);
//                    Log.d(TAG, "title: "+title);
//                    Log.d(TAG, "url: "+url);
//                    Log.d(TAG, "image_url: "+image_url);
//                    Log.d(TAG, "-----");
//
//                    mIngredientRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            int size = searchedIngredients.size();
//                            int count = 0;
//                            for (DataSnapshot ingSnapshot: dataSnapshot.getChildren()) {
//                                String ingr = ingSnapshot.getValue().toString().toLowerCase();
//                                ingr = ingr.replace("{ingredient=","");
//                                ingr = ingr.replaceAll("\\}", "");
//
//                                for(int a = 0; a <size; a++){
//                                    String search = searchedIngredients.get(a);
//                                    if(ingr.contains(search.toLowerCase())) {
//                                        count++;
//                                        Log.d(TAG, "test: ingredient found");
//                                    }
//                                }
//                            }
//                            Log.d(TAG, "title: "+title+"-size: "+size+"-count: "+count);
//                            if(count >= size) {
//                                Recipes item = new Recipes(title, url, image_url, recipeKey);
//                                rowItems.add(item);
//
//                                RecipeListAdapter adapter = new RecipeListAdapter(getApplicationContext(), rowItems);
//                                listView.setAdapter(adapter);
//
//                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                        TextView text = (TextView) view.findViewById(R.id.Text2);
//                                        String recipe_url = text.getText().toString().trim();
//
//                                       // Intent intent = new Intent(getApplicationContext(), SpecificRecipe.class);
//                                       // intent.putExtra("url", recipe_url);
//                                      //  startActivity(intent);
//                                    }
//                                });
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                        }
//                    });
//
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//            }
//        });
//    }

    public void getSearchedIngredient() {
        String string = searchField.getText().toString();
        Log.d(TAG, "test: comma separated string: " + string);

        searchedIngredients = new ArrayList<String>(Arrays.asList(string.split(",|\\, |\\ , |\\ ,")));
        Log.d(TAG, "test: ArrayList size: " + searchedIngredients.size());
    }

    public void hideKeypad() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

    public void clearSearchText(View view) {
        searchField.setText("");
        listView.setAdapter(null);
        getData();
    }
}
