package com.hemantithide.huecontroller;

import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hemantithide.huecontroller.API.ApiHandler;
import com.hemantithide.huecontroller.API.IApiResponse;
import com.hemantithide.huecontroller.Fragments.LightFragment;
import com.hemantithide.huecontroller.Model.Light;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements LightFragment.OnListFragmentInteractionListener {

    public static final String API_ADDRESS = "http://145.49.24.217/api/";
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onListFragmentInteraction(Light light) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //TODO: update detail fragment
        }

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //TODO: intent to detail activity
        }
    }

    @Override
    public void onConfigurationChanged(Configuration _newConfig) {
        orientation = _newConfig.orientation;

        super.onConfigurationChanged(_newConfig);
    }
}
