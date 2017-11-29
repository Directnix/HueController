package com.hemantithide.huecontroller.UI.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.hemantithide.huecontroller.API.ApiHandler;
import com.hemantithide.huecontroller.LightsActivity;
import com.hemantithide.huecontroller.Model.Light;
import com.hemantithide.huecontroller.R;


public class DetailFragment extends Fragment {

    private Light light;
    private OnFragmentInteractionListener mListener;

    SeekBar skBri;
    SeekBar skSat;
    SeekBar skHue;

    int hue;
    int sat;
    int bri;

    ImageView ivColor;

    boolean orientationMode = false;

    public DetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getView().setVisibility(View.INVISIBLE);

        if(light != null)
            updateUi(light);
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public void updateUi(Light light){

        getView().setVisibility(View.VISIBLE);

        hue = light.getHue();
        sat = light.getSaturation();
        bri = light.getBrightness();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Button btnBack = getView().findViewById(R.id.fa_da_btn_back);
            btnBack.setOnClickListener(view -> {
                Intent i = new Intent(getContext(), LightsActivity.class);
                startActivity(i);
            });
        }

        ivColor = getView().findViewById(R.id.fr_de_iv_color);

        if(light.isOn())
            ivColor.setColorFilter(light.toHSVColor());
        else
            ivColor.setColorFilter(Color.BLACK);


        orientationMode = false;
        this.light = light;

        TextView tvName = getView().findViewById(R.id.fa_det_name);
        tvName.setText(light.getName());

        skBri = getView().findViewById(R.id.fa_det_sb_bri);
        skBri.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(light.isOn()) {
                    bri = seekBar.getProgress();
                    ivColor.setColorFilter(toHSVColor(hue, sat, bri));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                light.setBrightness(seekBar.getProgress());
                ApiHandler.setBrightness(light, seekBar.getProgress());
                mListener.onFragmentInteraction(light);
            }
        });

        skSat = getView().findViewById(R.id.fa_det_sb_sat);

        skSat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(light.isOn()) {
                    sat = seekBar.getProgress();
                    ivColor.setColorFilter(toHSVColor(hue, sat, bri));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                light.setSaturation(seekBar.getProgress());
                ApiHandler.setSaturation(light,seekBar.getProgress());
                mListener.onFragmentInteraction(light);
            }
        });

        skHue = getView().findViewById(R.id.fa_det_sb_hue);

        skHue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(light.isOn()) {
                    hue = seekBar.getProgress();
                    ivColor.setColorFilter(toHSVColor(hue, sat, bri));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                light.setHue(seekBar.getProgress());
                ApiHandler.setHue(light, seekBar.getProgress());
                mListener.onFragmentInteraction(light);
            }
        });

        Switch swOn = getView().findViewById(R.id.fa_det_switch);

        swOn.setOnClickListener(view -> {
            ApiHandler.setOn(light, swOn.isChecked());
            light.setOn(swOn.isChecked());
            mListener.onFragmentInteraction(light);
            if(light.isOn())
                ivColor.setColorFilter(light.toHSVColor());
            else
                ivColor.setColorFilter(Color.BLACK);
            power();
        });

        skBri.setProgress(light.getBrightness());
        skSat.setProgress(light.getSaturation());
        skHue.setProgress(light.getHue());
        swOn.setChecked(light.isOn());

        power();
    }

    private void power(){
        if(light.isOn() || !orientationMode){
            skBri.setEnabled(true);
            skSat.setEnabled(true);
            skHue.setEnabled(true);
        }else{
            skBri.setEnabled(false);
            skSat.setEnabled(false);
            skHue.setEnabled(false);
        }
    }

    public int toHSVColor(int hue, int sat, int bri){
        return Color.HSVToColor(
                new float[]{
                        (float) hue /65280f * 360,
                        (float) sat /254f,
                        (float) bri /254f
                }
        );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Light light);
    }
}
