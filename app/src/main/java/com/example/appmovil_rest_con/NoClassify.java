package com.example.appmovil_rest_con;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NoClassify extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_no_classify);
        getSupportActionBar().hide();

        initBuscador(this);
        initHomeButton(this);
    }
}
