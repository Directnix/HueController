package com.hemantithide.huecontroller;

import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.hemantithide.huecontroller.UI.Fragments.LightFragment;
import com.hemantithide.huecontroller.Model.Light;

public class MainActivity extends FragmentActivity implements LightFragment.OnListFragmentInteractionListener {

    public static final String API_ADDRESS = "http://192.168.1.179/api/";
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onListFragmentInteraction(Light light) {
        //TODO: update detail fragment

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //TODO: goto activity detail activity
        }
    }

    @Override
    public void onConfigurationChanged(Configuration _newConfig) {
        orientation = _newConfig.orientation;

        super.onConfigurationChanged(_newConfig);
    }
}
