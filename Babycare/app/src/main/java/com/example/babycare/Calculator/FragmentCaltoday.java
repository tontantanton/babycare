package com.example.babycare.Calculator;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.R;
import com.example.babycare.RecordActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentCaltoday extends Fragment {
    ImageButton btnBackCal;
    Button caltoday;
    private TextView text, nametxt, cal, date, txtsumcal;

    FirebaseUser firebaseUser;

    DatabaseReference reference;
    String strsum, strDate;

    float sum;

    CalTodayAdapter calTodayAdapter;
    List<CalToday> mcaltoday;


    RecyclerView recyclerView;
    ProgressBar progressBar;
TextView text_1;
String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cal_today, container, false);


        date = (TextView) view.findViewById(R.id.date);
        txtsumcal = (TextView) view.findViewById(R.id.txtsumcal);


        text_1 = (TextView) view.findViewById(R.id.text_1);


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
//        intent = getIntent();
//        userID = intent.getStringExtra("id");
        text_1.setText(""+userID);


        reference = FirebaseDatabase.getInstance().getReference("Calculators");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                readMessage(firebaseUser.getUid());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    String ccal, cquan, txtresult;
    float calnum, quannum, result;

    private void readMessage(final String myid) {
        mcaltoday = new ArrayList<>();


        final AlertDialog.Builder dDialog = new AlertDialog.Builder(getContext());


        reference = FirebaseDatabase.getInstance().getReference("Calculators");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mcaltoday.clear();


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
                strDate = mdformat.format(calendar.getTime());
                date.setText(strDate);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CalToday calToday = snapshot.getValue(CalToday.class);
//                    Log.w("", "name====" + myid);
//                    Log.d("", "calid---" + calToday.getId());
//                    Log.d("", "name---" + calToday.getName());

//                    assert calToday != null;
                    if (calToday.getId().equals(myid) && calToday.getDate().equals(strDate)) {
                        calnum = Float.parseFloat(calToday.getCalories());
                        quannum = Float.parseFloat(calToday.getQuantity());
                        sum = (calnum * quannum) / 100;
                        strsum = String.valueOf(sum);
                        result += sum;
//                        Log.d("", "test----" + calnum + "*" + quannum);
                        calToday.setCalories(strsum);
                        mcaltoday.add(calToday);

                    }


                    DecimalFormat df = new DecimalFormat("0.00");
                    df.setRoundingMode(RoundingMode.CEILING.HALF_UP);
                    double f = Double.parseDouble(df.format(result));

                    txtresult = String.valueOf(f);
                    txtsumcal.setText(txtresult);


                    calTodayAdapter = new CalTodayAdapter(getContext(), mcaltoday);
                    recyclerView.setAdapter(calTodayAdapter);


                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    progressBar.setProgress(0);
                    progressBar.setMax(1000);
                    progressBar.setProgress((int) result);


                }

                if (result < 1000) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(getActivity());
                } else if (result > 1000) {
                    ViewDialog2 alert = new ViewDialog2();
                    alert.showDialog2(getActivity());
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public class ViewDialog {

        public void showDialog(Activity activity) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_design);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button mDialogNo = dialog.findViewById(R.id.btn_cancel);
            mDialogNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            Button mDialogOk = dialog.findViewById(R.id.btn_ok);
            mDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), RecordActivity.class);
                    startActivity(intent);

                }
            });

            dialog.show();
        }
    }


    public class ViewDialog2 {

        public void showDialog2(Activity activity) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_design2);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


            Button mDialogOk = dialog.findViewById(R.id.btn_ok);
            mDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            dialog.show();
        }
    }

}

