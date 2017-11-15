package com.hemantithide.huecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hemantithide.huecontroller.API.ApiHandler;
import com.hemantithide.huecontroller.API.IApi;
import com.hemantithide.huecontroller.API.IApiResponse;
import com.hemantithide.huecontroller.Model.Light;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IApiResponse {

    IApi api;
    ArrayList<Light> lights = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = ApiHandler.getInstance("http://145.49.24.217/api/",this, this);
        api.getLights();
    }

    @Override
    public void onLightsReceived(ArrayList<Light> lights) {
        Log.i("MAIN", "lights received");
        this.lights = lights;
        //TODO: update adapter
    }

    @Override
    public void onLightReceived(Light light) {
        Log.i("MAIN", "light received");
        //TODO: detail light get (for detail fragment)
    }
}
