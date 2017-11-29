package com.hemantithide.huecontroller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;

import com.android.volley.Request;
import com.hemantithide.huecontroller.API.ApiHandler;
import com.hemantithide.huecontroller.API.VolleyService;
import com.hemantithide.huecontroller.UI.Fragments.DetailFragment;
import com.hemantithide.huecontroller.UI.Fragments.LightFragment;
import com.hemantithide.huecontroller.Model.Light;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends FragmentActivity implements LightFragment.OnListFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener {

    //public static String API_ADDRESS = "http://192.168.1.179/api/"; // LA 134
    //public static String API_ADDRESS;
    public static String API_ADDRESS = "http://145.48.205.33/api/" ;
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LightFragment frLight = (LightFragment) getSupportFragmentManager().findFragmentById(R.id.ma_fr_light);
        ApiHandler.getInstance(MainActivity.API_ADDRESS, frLight, getApplicationContext());
    }

    @Override
    public void onListFragmentInteraction(Light light) {
        DetailFragment frDetail = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.ma_fr_det);
        frDetail.updateUi(light);

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //TODO: go to activity detail activity
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration _newConfig) {
//        orientation = _newConfig.orientation;
//        super.onConfigurationChanged(_newConfig);
//    }

    @Override
    public void onFragmentInteraction(Light light) {
        Log.i("DETAIL", "ACK");
        LightFragment frLight = (LightFragment) getSupportFragmentManager().findFragmentById(R.id.ma_fr_light);
        frLight.updateAdapter();
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i("IP", "***** IP=" + ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("IP", ex.toString());
        }
        return null;
    }
}
