package com.hemantithide.huecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hemantithide.huecontroller.API.ApiHandler;
import com.hemantithide.huecontroller.API.IApiResponse;
import com.hemantithide.huecontroller.Model.Light;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IApiResponse {

    ArrayList<Light> lights = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiHandler.getInstance("http://145.49.24.217/api/",this, this);
    }

    @Override
    public void onLightsReceived(ArrayList<Light> lights) {
        for(Light l: lights)
            Log.i("MAIN", l.toString());

        this.lights = lights;
        //TODO: update adapter
    }
}
