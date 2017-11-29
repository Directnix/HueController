package com.hemantithide.huecontroller;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hemantithide.huecontroller.API.ApiHandler;
import com.hemantithide.huecontroller.API.IApiResponse;
import com.hemantithide.huecontroller.Model.Light;
import com.hemantithide.huecontroller.UI.Fragments.DetailFragment;
import com.hemantithide.huecontroller.UI.Fragments.LightFragment;

import java.util.ArrayList;

public class LightsActivity extends AppCompatActivity implements LightFragment.OnListFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener, IApiResponse {

    LightFragment frLight = new LightFragment();
    DetailFragment frDetail = new DetailFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.la_fl_lights,frLight);
        ft.commit();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
            ft2.replace(R.id.la_fl_detail, frDetail);
            ft2.commit();

            if(getIntent().getSerializableExtra("LIGHT") != null){
                frDetail.setLight((Light) getIntent().getSerializableExtra("LIGHT"));
                getIntent().removeExtra("LIGHT");
            }
        }
        ApiHandler.getInstance(MainActivity.API_ADDRESS, getApplicationContext()).setListener(this);

        if(ApiHandler.access)
            ApiHandler.getLights();
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
        frLight.updateAdapter();
    }

    // TODO: 29-11-2017 On back pressed close app 
}
