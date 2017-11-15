package com.hemantithide.huecontroller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.hemantithide.huecontroller.API.ApiHandler;
import com.hemantithide.huecontroller.Model.Light;

import java.util.ArrayList;

/**
 * Created by Nick van Endhoven, 2119719 on 15-Nov-17.
 */

public class LightsAdapter extends ArrayAdapter<Light> {

    public LightsAdapter(@NonNull Context context,  ArrayList<Light> lights) {
        super(context, 0, lights);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Light l = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lv_lights, parent, false);


        TextView tvName = convertView.findViewById(R.id.tv_name);
        tvName.setText(l.getName());

        Switch swOn = convertView.findViewById(R.id.sw_on);
        swOn.setChecked(l.isOn());
        swOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiHandler.setOn(l, swOn.isChecked());
            }
        });

        SeekBar sbBri =  convertView.findViewById(R.id.sb_bri);
        sbBri.setProgress(l.getBrightness());
        sbBri.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ApiHandler.setBrightness(l, seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar sbHue =  convertView.findViewById(R.id.sb_hue);
        sbHue.setProgress(l.getHue());
        sbHue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ApiHandler.setHue(l, seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar sbSat =  convertView.findViewById(R.id.sb_sat);
        sbSat.setProgress(l.getSaturation());
        sbSat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ApiHandler.setSaturation(l, seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return convertView;
    }
}
