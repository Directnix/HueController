package com.hemantithide.huecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements VolleyListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VolleyService.getInstance(this, getApplicationContext());
        VolleyService.get("http://145.49.24.217/api/");
    }

    @Override
    public void onReceive(String body) {
        Log.i("MAIN", body);
    }
}
