package com.hemantithide.huecontroller.API;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.hemantithide.huecontroller.Model.Light;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nick van Endhoven, 2119719 on 15-Nov-17.
 */

public class ApiHandler implements VolleyListener {
    public static ApiHandler instance;

    static String username ="iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB";
    static String rootUrl;
    public static boolean access = false;

    Context context;

    IApiResponse listener;

    static QueuedRequest lastRequest = QueuedRequest.NONE;

    public static ApiHandler getInstance(String rootUrl, Context context) {
        if (instance == null)
            instance = new ApiHandler(rootUrl, context);
        return instance;
    }

    private ApiHandler(String rootUrl, Context context){
        this.rootUrl = rootUrl;
        this.context = context;
        VolleyService.getInstance(this, context);

        receiveUsername();
    }

    public void setListener(IApiResponse listener){
        this.listener = listener;
    }

    @Override
    public void onReceive(String body) {

        if(body.contains("unauthorized user")){
            listener.onErrorReceived(body);
            return;
        }

        switch (lastRequest){
            case USERNAME: receiveUserName(body); getLights(); access = true;
                break;
            case LIGHTS: receiveLights(body);
                break;
        }
    }

    @Override
    public void onReceiveError(String error) {
        Log.e("VOLLEY", error);
    }


    public static void receiveUsername(){
        VolleyService.doRequest(rootUrl,"{\"devicetype\":\"" + Build.MODEL + "\"}", Request.Method.POST);
        lastRequest = QueuedRequest.USERNAME;
    }

    public static void setBrightness(Light light, int bri) {
        VolleyService.doRequest(rootUrl + username + "/lights/" + light.getIndex() + "/state",
                "{\"bri\":" + bri + "}",
                Request.Method.PUT
        );
        lastRequest = QueuedRequest.SET;
    }

    public static void setHue(Light light, int hue) {
        VolleyService.doRequest(rootUrl + username + "/lights/" + light.getIndex() + "/state",
                "{\"hue\":" + hue + "}",
                Request.Method.PUT
        );
        lastRequest = QueuedRequest.SET;
    }

    public static void setSaturation(Light light, int sat) {
        VolleyService.doRequest(rootUrl + username + "/lights/" + light.getIndex() + "/state",
                "{\"sat\":" + sat + "}",
                Request.Method.PUT
        );
        lastRequest = QueuedRequest.SET;
    }

    public static void setOn(Light light, boolean on) {
        VolleyService.doRequest(rootUrl + username + "/lights/" + light.getIndex() + "/state",
                "{\"on\":" + on + "}",
                Request.Method.PUT
        );
        lastRequest = QueuedRequest.SET;
    }

    public static void getLights() {
        VolleyService.doRequest(rootUrl + username + "/lights",
                Request.Method.GET
        );
        lastRequest = QueuedRequest.LIGHTS;
    }

    private void receiveUserName(String body){
        String[] s = body.split("\"");
        Log.i("USERNAME", s[5]);

        if(username != s[5])
            username = s[5];
    }

    private void receiveLights(String body){
        ArrayList<Light> lights = new ArrayList<>();

        try {
            JSONObject response = new JSONObject(body);
            Iterator x = response.keys();
            JSONArray jLights = new JSONArray();
            List<String> keys = new ArrayList<>();

            while (x.hasNext()) {
                String res = (String) x.next();
                jLights.put(response.get(res));
                keys.add(res);
            }

            for(int i = 0; i < jLights.length(); i++){
                lights.add(new Light(
                        jLights.getJSONObject(i).getString("name"),
                        Integer.parseInt(keys.get(i)),
                        Integer.parseInt(jLights.getJSONObject(i).getJSONObject("state").getString("bri")),
                        Integer.parseInt(jLights.getJSONObject(i).getJSONObject("state").getString("hue")),
                        Integer.parseInt(jLights.getJSONObject(i).getJSONObject("state").getString("sat")),
                        Boolean.parseBoolean(jLights.getJSONObject(i).getJSONObject("state").getString("on"))
                ));
            }

            listener.onLightsReceived(lights);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

enum QueuedRequest{
    NONE, USERNAME, LIGHTS, SET
}



