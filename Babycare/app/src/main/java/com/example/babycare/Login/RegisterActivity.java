package com.example.babycare.Login;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.babycare.Content.ImagesActivity;
import com.example.babycare.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputUsername, inputEmail, inputPassword, inputName, inputDate, inputWeight, inputHeight;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private RadioGroup radioGroup;
    private RadioButton radioButtonG, radioButtonB, radioSexButton;
    CircleImageView image_profile;

    StorageReference storageReference;
    private static final int IMAGE_REQEST =1;
    private Uri imageUri;
    private StorageTask uploadTask;

    boolean a = false;

    private FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        inputUsername = (EditText) findViewById(R.id.username);
        inputName = (EditText) findViewById(R.id.name);

        inputDate = (EditText) findViewById(R.id.bdate);
        inputWeight = (EditText) findViewById(R.id.weight);
        inputHeight = (EditText) findViewById(R.id.height);
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        radioButtonG = (RadioButton) findViewById(R.id.rbg);
        //radioButtonB = (RadioButton) findViewById(R.id.rbb);

        radioButtonG.setChecked(true);



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = inputUsername.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String name = inputName.getText().toString().trim();
                String bdate = inputDate.getText().toString().trim();
                String weight = inputWeight.getText().toString().trim();
                String height = inputHeight.getText().toString().trim();

                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioSexButton = (RadioButton) findViewById(selectedId);
                String gender = (String) radioSexButton.getText();
                /*Toast.makeText(RegisterActivity.this,
                        gender, Toast.LENGTH_SHORT).show();*/


                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "กรุณากรอกชื่อผู้ใช้", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "กรุณากรอกอีเมล", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "กรุณากรอกรหัสผ่าน", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "รหัสผ่านของคุณสั้นเกินไป อย่างน้อยต้องมี 6 ตัว", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "กรุณากรอกชื่อของลูกน้อย", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(bdate)){
                    Toast.makeText(getApplicationContext(), "กรุณาเลือกวันเกิดของลูกน้อย", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    register(username, email, password, name, bdate, weight, height, gender);
                }


                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {

                                } else {
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });



        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=true;
                DialogFragment dFragment = new DatePickerFragment();
                dFragment.show(getSupportFragmentManager(), "Date Picker");
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT,this,year,month,day);

            dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());

            return  dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            // Do something with the chosen date
            EditText bdate = (EditText) getActivity().findViewById(R.id.bdate);

            // Create a Date variable/object with user chosen date
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            // Format the date using style and locale
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
            String formattedDate = df.format(chosenDate);

            // Display the chosen date to app interface
            bdate.setText(day+"/"+(month+1)+"/"+year);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private void register(final String username, final String email, final String password, final String name, final String bdate, final String weight, final String height, final String gender){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("imageURL", "default");
                            hashMap.put("username", username);
                            hashMap.put("email", email);
                            hashMap.put("password", password);
                            hashMap.put("name", name);
                            hashMap.put("birthday", bdate);
                            hashMap.put("weight", weight);
                            hashMap.put("height", height);
                            hashMap.put("gender", gender);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "สมัครสมาชิกสำเร็จ", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this, ImagesActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "ไม่สามารถทำการสมัครสมาชิกได้จากอีเมล์และรหัสผ่านนี้", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}