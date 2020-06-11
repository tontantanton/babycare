package com.example.babycare.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.babycare.R;

public class MainActivity extends AppCompatActivity {

    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "เข้าสู่ระบบ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Login2Activity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "สมัครสมาชิก", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }

}
