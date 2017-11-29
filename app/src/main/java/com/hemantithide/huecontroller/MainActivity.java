package com.hemantithide.huecontroller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.hemantithide.huecontroller.API.ApiHandler;
import com.hemantithide.huecontroller.API.IApiResponse;
import com.hemantithide.huecontroller.UI.Fragments.DetailFragment;
import com.hemantithide.huecontroller.UI.Fragments.LightFragment;
import com.hemantithide.huecontroller.Model.Light;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity implements LightFragment.OnListFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener, IApiResponse {

    //public static String API_ADDRESS = "http://192.168.1.179/api/"; // LA 134
    public static String API_ADDRESS = "http://192.168.2.52/api/" ;

    LightFragment frLight = new LightFragment();
    DetailFragment frDetail = new DetailFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ma_fl_lights,frLight);
        ft.commit();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
            ft2.replace(R.id.ma_fl_detail, frDetail);
            ft2.commit();

            if(getIntent().getSerializableExtra("LIGHT") != null){
                frDetail.updateUi((Light) getIntent().getSerializableExtra("LIGHT"));
                getIntent().removeExtra("LIGHT");
            }
        }

        if(ApiHandler.access)
            ApiHandler.getLights();

        ApiHandler.getInstance(MainActivity.API_ADDRESS, getApplicationContext()).setListener(this);
    }

    public void onLightsReceived(ArrayList<Light> lights) {
        for(Light l: lights)
            Log.i("LIGHT", l.toString());

        frLight.setLights(lights);
    }

    @Override
    public void onErrorReceived(String body) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Press the link button on the bridge and try again");
        dlgAlert.setTitle("Unauthorized user");
        dlgAlert.setPositiveButton("Try again",
                (dialog, which) -> ApiHandler.receiveUsername());
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
        return;
    }

    @Override
    public void onListFragmentInteraction(Light light) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            frDetail.updateUi(light);
        }else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Intent i = new Intent(getApplicationContext(), DetailActivity.class);
            i.putExtra("LIGHT", light);
            startActivity(i);
        }
    }

    @Override
    public void onFragmentInteraction(Light light) {
        Log.i("DETAIL", "ACK");
        frLight.updateAdapter();
    }
}
