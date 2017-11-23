package com.hemantithide.huecontroller.UI.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.hemantithide.huecontroller.API.ApiHandler;
import com.hemantithide.huecontroller.Model.Light;
import com.hemantithide.huecontroller.R;


public class DetailFragment extends Fragment {

    private Light light;
    private OnFragmentInteractionListener mListener;

    SeekBar skBri;
    SeekBar skSat;
    SeekBar skHue;

    boolean orientationMode = false;

    public DetailFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

    public void updateUi(Light light){
        Log.e("UPDATE DETAIL UI", "ACK");

        orientationMode = false;
        this.light = light;

        TextView tvName = getView().findViewById(R.id.fa_det_name);
        tvName.setText(light.getName());

        ImageButton ibColor = getView().findViewById(R.id.fa_det_color);
        ibColor.setColorFilter(light.toHSVColor());

        ibColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orientationMode = !orientationMode;
                power();
            }
        });

        skBri = getView().findViewById(R.id.fa_det_sb_bri);
        skBri.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                light.setBrightness(seekBar.getProgress());
                ApiHandler.setBrightness(light, seekBar.getProgress());
                mListener.onFragmentInteraction(light);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skSat = getView().findViewById(R.id.fa_det_sb_sat);

        skSat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                light.setSaturation(seekBar.getProgress());
                ApiHandler.setSaturation(light,seekBar.getProgress());
                mListener.onFragmentInteraction(light);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skHue = getView().findViewById(R.id.fa_det_sb_hue);

        skHue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                light.setHue(seekBar.getProgress());
                ApiHandler.setHue(light, seekBar.getProgress());
                mListener.onFragmentInteraction(light);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Switch swOn = getView().findViewById(R.id.fa_det_switch);

        swOn.setOnClickListener(view -> {
            ApiHandler.setOn(light, swOn.isChecked());
            light.setOn(swOn.isChecked());
            mListener.onFragmentInteraction(light);
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


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Light light);
    }
}
