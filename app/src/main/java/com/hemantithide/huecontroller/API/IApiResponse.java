package com.hemantithide.huecontroller.API;

import com.hemantithide.huecontroller.Model.Light;

import java.util.ArrayList;

/**
 * Created by Nick van Endhoven, 2119719 on 15-Nov-17.
 */

public interface IApiResponse {
    void onLightsReceived(ArrayList<Light> lights);
    void onErrorReceived(String body);
}
