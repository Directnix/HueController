package com.hemantithide.huecontroller.Model;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by Nick van Endhoven, 2119719 on 15-Nov-17.
 */

public class Light implements Serializable{

    private String name;
    private int index;
    private int brightness;
    private int hue;
    private int saturation;
    private boolean on;

    public Light(String name, int index, int brightness, int hue, int saturation, boolean on) {
        this.name = name;
        this.index = index;
        this.brightness = brightness;
        this.hue = hue;
        this.saturation = saturation;
        this.on = on;
    }

    @Override
    public String toString() {
        return "Light{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", brightness=" + brightness +
                ", hue=" + hue +
                ", saturation=" + saturation +
                ", on=" + on +
                '}';
    }

    public int toHSVColor(){
        return Color.HSVToColor(
                new float[]{
                        (float) getHue()/65280f * 360,
                        (float)getSaturation()/254f,
                        (float)getBrightness()/254f
                }
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
