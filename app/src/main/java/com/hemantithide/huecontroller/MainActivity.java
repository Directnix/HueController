package com.hemantithide.huecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    IApi api;
    ArrayList<Light> lights = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = ApiHandler.getInstance(this);

    }

}
