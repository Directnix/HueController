package com.hemantithide.huecontroller.API;

import com.hemantithide.huecontroller.Model.Light;

/**
 * Created by Nick van Endhoven, 2119719 on 15-Nov-17.
 */

public interface IApi{
    void setBrightness(Light light, int bri);
    void setHue(Light light, int hue);
    void setSaturation(Light light, int sat);
    void setOn(Light light, boolean on);

    void getLights();
}