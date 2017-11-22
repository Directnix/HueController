package com.hemantithide.huecontroller.UI.Fragments;

import android.content.Context;
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

        this.light = light;

        TextView tvName = getView().findViewById(R.id.fa_det_name);
        tvName.setText(light.getName());

        ImageButton ibColor = getView().findViewById(R.id.fa_det_color);
        ibColor.setColorFilter(light.toHSVColor());

        SeekBar skBri = getView().findViewById(R.id.fa_det_sb_bri);
        skBri.setProgress(light.getBrightness());

        skBri.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ApiHandler.setBrightness(light, seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar skSat = getView().findViewById(R.id.fa_det_sb_sat);
        skSat.setProgress(light.getSaturation());

        skSat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ApiHandler.setSaturation(light,seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar skHue = getView().findViewById(R.id.fa_det_sb_hue);
        skHue.setProgress(light.getHue());

        skHue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ApiHandler.setHue(light, seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Switch swOn = getView().findViewById(R.id.fa_det_switch);
        swOn.setChecked(light.isOn());

        swOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiHandler.setOn(light, swOn.isChecked());
            }
        });

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
