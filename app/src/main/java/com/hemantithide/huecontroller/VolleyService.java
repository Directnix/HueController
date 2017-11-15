package com.hemantithide.huecontroller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Nick van Endhoven, 2119719 on 15-Nov-17.
 */

public class VolleyService {

    public static VolleyService instance;
    private static VolleyListener listener;
    private static RequestQueue requestQueue;

    public static VolleyService getInstance(VolleyListener listener, Context context){
        if(instance == null)
            instance = new VolleyService(listener, context);
        return instance;
    }

    private VolleyService(VolleyListener listener, Context context){
        this.requestQueue = Volley.newRequestQueue(context);
        this.listener = listener;
    }

    private static Response.Listener<String> onSucces = (String response) ->{
        listener.onReceive(response);
    };

    private static Response.ErrorListener onError = (VolleyError response) ->{
        Log.e("VOLLEY_SERVICE", response.toString());
    };

    public static void post(String url, String body){

    }

    public static void put(String url, String body){

    }

    public static void get(String url){
        doRequest(new StringRequest(
                Request.Method.GET,
                url,
                onSucces,
                onError
        ));
    }

    private static void doRequest(StringRequest request){
        requestQueue.add(request);
    }
}

interface VolleyListener{
    void onReceive(String body);
}
