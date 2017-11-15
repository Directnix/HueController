package com.hemantithide.huecontroller.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.hemantithide.huecontroller.Model.Light;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Nick van Endhoven, 2119719 on 15-Nov-17.
 */

public class ApiHandler implements VolleyListener, IApi {
    public static ApiHandler instance;

    String username;
    String rootUrl;

    IApiResponse listener;

    QueuedRequest lastRequest = QueuedRequest.NONE;

    public static ApiHandler getInstance(String rootUrl, IApiResponse listener, Context context) {
        if (instance == null)
            instance = new ApiHandler(rootUrl,listener, context);
        return instance;
    }

    private ApiHandler(String rootUrl, IApiResponse listener, Context context){
        this.rootUrl = rootUrl;
        this.listener = listener;
        VolleyService.getInstance(this, context);

        VolleyService.doRequest(rootUrl,"{\"start\":\"fire\"}", Request.Method.POST);
        lastRequest = QueuedRequest.USERNAME;
    }

    @Override
    public void onReceive(String body) {
        switch (lastRequest){
            case USERNAME: receiveUserName(body);
                break;
            case LIGHTS: receiveLights(body);
                break;
            case LIGHT: receiveLight(body);
                break;
        }
    }

    @Override
    public void setBrightness(Light light, int bri) {
        VolleyService.doRequest(rootUrl + username + "/lights/" + light.getIndex() + "/state",
                "{\"bri\":" + bri + "}",
                Request.Method.PUT
        );
        lastRequest = QueuedRequest.SET;
    }

    @Override
    public void setHue(Light light, int hue) {
        VolleyService.doRequest(rootUrl + username + "/lights/" + light.getIndex() + "/state",
                "{\"hue\":" + hue + "}",
                Request.Method.PUT
        );
        lastRequest = QueuedRequest.SET;
    }

    @Override
    public void setSaturation(Light light, int sat) {
        VolleyService.doRequest(rootUrl + username + "/lights/" + light.getIndex() + "/state",
                "{\"sat\":" + sat + "}",
                Request.Method.PUT
        );
        lastRequest = QueuedRequest.SET;
    }

    @Override
    public void setOn(Light light, boolean on) {
        VolleyService.doRequest(rootUrl + username + "/lights/" + light.getIndex() + "/state",
                "{\"on\":" + on + "}",
                Request.Method.PUT
        );
        lastRequest = QueuedRequest.SET;
    }

    @Override
    public void getLights() {
        VolleyService.doRequest(rootUrl + username + "/lights",
                Request.Method.GET
        );
        lastRequest = QueuedRequest.LIGHTS;
    }

    @Override
    public void getLight(Light light) {
        VolleyService.doRequest(rootUrl + username + "/lights/" + light.getIndex(),
                Request.Method.GET
        );
        lastRequest = QueuedRequest.LIGHT;
    }

    private void receiveUserName(String body){
        String[] s = body.split("\"");
        username = s[5];
    }

    private void receiveLights(String body){
        ArrayList<Light> lights = new ArrayList<>();

        try {
            JSONObject response = new JSONObject(body);
            Iterator x = response.keys();
            JSONArray jlights = new JSONArray();

            while (x.hasNext()){
                jlights.put(response.get((String) x.next()));
            }

            for(int i = 0; i < jlights.length(); i++){

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void receiveLight(String body){

    }
}

enum QueuedRequest{
    NONE, USERNAME, LIGHTS, LIGHT, SET
}



