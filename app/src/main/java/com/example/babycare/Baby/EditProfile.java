package com.example.babycare.Baby;

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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.babycare.Model.User;
import com.example.babycare.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private EditText inputUsername, inputEmail, inputName, inputDate, inputWeight, inputHeight;
    private Button btnUpdate;
    private ImageButton back;

    private RadioGroup radioGroup;
    private RadioButton radioButtonG, radioButtonB, radioSexButton;

    FirebaseAuth auth;
    DatabaseReference reference;

    private String currentUserId;

    CircleImageView image_profile;

    StorageReference storageReference;
    private static final int IMAGE_REQEST =1;
    private Uri imageUri;
    private StorageTask uploadTask;

    //private String key;
    private String username;
    private String email;
    private String name;
    private String bd;
    private String weight;
    private String height;
    private String gender;

    boolean a = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        image_profile = findViewById(R.id.profile_image);
        back = (ImageButton)findViewById(R.id.btn_back_edit);

        Intent i = getIntent();
        username = i.getStringExtra("username");
        email = i.getStringExtra("email");
        name = i.getStringExtra("name");
        bd = i.getStringExtra("birthday");
        weight = i.getStringExtra("weight");
        height = i.getStringExtra("height");
        gender = i.getStringExtra("gender");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditProfile.this, BabyActivity.class);
                startActivity(i);
            }
        });

        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.getImageURL().equals("default")){
                    image_profile.setImageResource(R.mipmap.ic_launcher_round);
                } else {
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(image_profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

        inputUsername = (EditText) findViewById(R.id.username);
        inputEmail = (EditText) findViewById(R.id.email);
        inputName = (EditText) findViewById(R.id.name);
        inputDate = (EditText) findViewById(R.id.bdate);
        inputWeight = (EditText) findViewById(R.id.weight);
        inputHeight = (EditText) findViewById(R.id.height);
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        radioButtonG = (RadioButton) findViewById(R.id.rbg);
        radioButtonB = (RadioButton) findViewById(R.id.rbb);

        btnUpdate = (Button) findViewById(R.id.btn_update);

        inputUsername.setText(username);
        inputEmail.setText(email);
        inputName.setText(name);
        inputDate.setText(bd);
        inputWeight.setText(weight);
        inputHeight.setText(height);

        if(gender.equals("หญิง")){
            radioButtonG.setChecked(true);
        }else{
            radioButtonB.setChecked(true);
        }




        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);

                String gender = (String) radioSexButton.getText();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "กรุณากรอกช่ื่อผู้ใช้", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "กรุณากรอกอีเมล", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "กรุณากรอกชื่อของลูกน้อย", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(bd)){
                    Toast.makeText(getApplicationContext(), "กรุณาเลือกวันเกิดของลูกน้อย", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    EditCurrentPost(username, email, name, bd, weight, height, gender);
                }


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
//            bdate.setText(formattedDate);
        }
    }

    private void EditCurrentPost(String username, String email, String name, String bd, String weight, String height, String gender) {
        reference.child("username").setValue(inputUsername.getText().toString());
        reference.child("email").setValue(inputEmail.getText().toString());
        reference.child("name").setValue(inputName.getText().toString());
        reference.child("birthday").setValue(inputDate.getText().toString());
        reference.child("weight").setValue(inputWeight.getText().toString());
        reference.child("height").setValue(inputHeight.getText().toString());
        reference.child("gender").setValue(gender);
        Toast.makeText(EditProfile.this, "แก้ไขข้อมูลเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditProfile.this, BabyActivity.class));
    }


    private void openImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
        pd.setMessage("Uploading");
//        pd.show();

        if (imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(auth.getUid());
                        HashMap<String , Object> map = new HashMap<>();
                        map.put("imageURL", mUri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }
}
