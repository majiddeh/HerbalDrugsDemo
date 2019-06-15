package com.example.herbaldrugsdemo.Activities;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.herbaldrugsdemo.ApiServices;
import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.Assets.UserSharedPrefrences;
import com.example.herbaldrugsdemo.R;

public class LoginActivity extends AppCompatActivity {
    ImageView imgBackToolbarLogin;
    TextView txtRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inits();
        onclicks();

    }

    private void onclicks() {
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        imgBackToolbarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void inits() {
        imgBackToolbarLogin=findViewById(R.id.img_back_login_toolbar);
        txtRegister=findViewById(R.id.txt_register);
    }


}
