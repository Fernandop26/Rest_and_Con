package com.example.appmovil_rest_con;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MovementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movement);
        getSupportActionBar().hide();
    }
}
