package com.hemantithide.huecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hemantithide.huecontroller.API.ApiHandler;
import com.hemantithide.huecontroller.API.IApiResponse;
import com.hemantithide.huecontroller.Model.Light;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IApiResponse {

    ArrayList<Light> lights = new ArrayList<>();

    ArrayAdapter<Light> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvLights = (ListView) findViewById(R.id.lv_lights);
        adapter = new LightsAdapter(getApplicationContext(), lights);
        lvLights.setAdapter(adapter);

        ApiHandler.getInstance("http://145.49.24.217/api/",this, this);
    }

    @Override
    public void onLightsReceived(ArrayList<Light> lights) {
        this.lights.clear();
        this.lights.addAll(lights);
        adapter.notifyDataSetChanged();
    }
}
