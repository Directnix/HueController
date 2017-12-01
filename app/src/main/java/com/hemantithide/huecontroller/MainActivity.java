package com.hemantithide.huecontroller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity{

    //public static String API_ADDRESS = "http://192.168.1.179/api/"; // LA 134
    public static String API_ADDRESS;
    public static String USER;

    final String IP_KEY = "com.example.app.ip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.app", getApplicationContext().MODE_PRIVATE);

        String ip = prefs.getString(IP_KEY, new String());
        EditText etIp = findViewById(R.id.ma_et_ipadres);
        etIp.setText(ip);

        Button btnGo = findViewById(R.id.btn_go);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etIp = findViewById(R.id.ma_et_ipadres);
                API_ADDRESS = "http://" + etIp.getText() + "/api/";
                prefs.edit().putString(IP_KEY, String.valueOf(etIp.getText())).apply();
                Intent i = new Intent(getApplicationContext(), LightsActivity.class);
                startActivity(i);
            }
        });

        Button btnGoLa134 = findViewById(R.id.btn_go_la134);
        btnGoLa134.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API_ADDRESS = "http://192.168.1.179/api/";
                Intent i = new Intent(getApplicationContext(), LightsActivity.class);
                startActivity(i);
            }
        });

        Button btnGoLaCafe = findViewById(R.id.btn_go_cafe);
        btnGoLaCafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API_ADDRESS = "http://145.48.205.33/api/";
                USER = "iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB";
                Intent i = new Intent(getApplicationContext(), LightsActivity.class);
                startActivity(i);
            }
        });

    }

}
