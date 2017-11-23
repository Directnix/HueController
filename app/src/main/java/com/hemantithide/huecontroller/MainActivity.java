package com.hemantithide.huecontroller;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.hemantithide.huecontroller.UI.Fragments.DetailFragment;
import com.hemantithide.huecontroller.UI.Fragments.LightFragment;
import com.hemantithide.huecontroller.Model.Light;

public class MainActivity extends FragmentActivity implements LightFragment.OnListFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener {

    public static final String API_ADDRESS = "http://192.168.1.179/api/";
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onListFragmentInteraction(Light light) {
        DetailFragment frDetail = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.ma_fr_det);
        frDetail.updateUi(light);

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //TODO: go to activity detail activity
        }
    }

    @Override
    public void onConfigurationChanged(Configuration _newConfig) {
        orientation = _newConfig.orientation;
        super.onConfigurationChanged(_newConfig);
    }

    @Override
    public void onFragmentInteraction(Light light) {
        Log.i("DETAIL", "ACK");
        LightFragment frLight = (LightFragment) getSupportFragmentManager().findFragmentById(R.id.ma_fr_light);
        frLight.updateAdapter();
    }
}
