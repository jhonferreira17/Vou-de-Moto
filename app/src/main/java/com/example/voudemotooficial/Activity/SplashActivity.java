package com.example.voudemotooficial.Activity;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.voudemotooficial.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash);



        new Handler(getMainLooper()).postDelayed(() -> {
            finish();
            startActivity(new Intent(this, TelaInicialActivity.class));
        },3000);

    }
}