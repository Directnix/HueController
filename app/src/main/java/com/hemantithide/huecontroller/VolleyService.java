package com.hemantithide.huecontroller;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

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

    public static void doRequest(String url, int requestType) {
        doRequest(url, "", requestType);
    }

    public static void doRequest(String url, final String body, int requestType){
        StringRequest request = new StringRequest(
                requestType,
                url,
                response -> listener.onReceive(response),
                error -> Log.e("VOLLEY", error.toString())
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return body == null ? null : body.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    try {
                        responseString = new String(response.data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        requestQueue.add(request);
    }
}

interface VolleyListener{
    void onReceive(String body);
}
