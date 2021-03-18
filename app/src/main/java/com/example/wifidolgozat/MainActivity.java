package com.example.wifidolgozat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class MainActivity extends AppCompatActivity {

    private TextView text;
    private BottomNavigationView bottomNavigationView;
    private WifiManager wifi;
    private WifiInfo wifiInfo;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.wifi_on:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        text.setText("Nincs jogosultság a wifi állapot módosítására");
                        Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                        startActivityForResult(panelIntent, 0);
                    }

                        wifi.setWifiEnabled(true);
                        text.setText("Wifi bekapcsolva");
                        break;
                    case R.id.wifi_off:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                            text.setText("Nincs jogosultság a wifi állapot módosítására");
                            Intent panelIntent = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
                            startActivityForResult(panelIntent, 0);
                        };
                        wifi.setWifiEnabled(false);
                        text.setText("Wifi kikapcsolva");
                        break;
                    case R.id.wifi_info:
                        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo netinfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                        if (netinfo.isConnected())
                        {
                            int ip_number = wifiInfo.getIpAddress();
                            String ip = Formatter.formatIpAddress(ip_number);
                            text.setText("IP cím: "+ip);
                        }
                        else
                        {
                            text.setText("Nem csatlakozik wifi hálózathoz");
                        };
                        break;
                }

                return true;
            }
        });

    }


    private void init() {
        text = findViewById(R.id.textview);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifi.getConnectionInfo();
    }
}