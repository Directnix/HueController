package com.hemantithide.huecontroller;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hemantithide.huecontroller.Model.Light;
import com.hemantithide.huecontroller.UI.Fragments.DetailFragment;

public class DetailActivity extends FragmentActivity implements DetailFragment.OnFragmentInteractionListener  {

    Light light;
    DetailFragment frDetail = new DetailFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        light = (Light)getIntent().getSerializableExtra("LIGHT");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent i = new Intent(getApplicationContext(), LightsActivity.class);
            i.putExtra("LIGHT", light);
            startActivity(i);
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.da_fl_detail,frDetail);
        ft.commit();

        frDetail.setLight(light);
    }


    @Override
    public void onFragmentInteraction(Light light) {

    }
}
