package com.hemantithide.huecontroller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

/**
 * Created by Nick van Endhoven, 2119719 on 15-Nov-17.
 */

public class ApiHandler implements VolleyListener, IApi {
    public static ApiHandler instance;

    public static ApiHandler getInstance(Context context) {
        if (instance == null)
            instance = new ApiHandler(context);
        return instance;
    }

    private ApiHandler(Context context){
        VolleyService.getInstance(this, context);
        VolleyService.doRequest("http://145.49.24.217/api/", Request.Method.GET);
    }

    @Override
    public void onReceive(String body) {

    }

    @Override
    public void setBrightness(Light light, int bri) {

    }

    @Override
    public void setHue(Light light, int hue) {

    }

    @Override
    public void setSaturation(Light light, int sat) {

    }

    @Override
    public void setOn(Light light, boolean on) {

    }

    @Override
    public void getLights() {

    }

    @Override
    public void getLight(Light light) {

    }
}


