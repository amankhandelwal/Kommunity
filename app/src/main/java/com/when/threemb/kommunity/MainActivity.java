package com.when.threemb.kommunity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvtest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        tvtest=(TextView)findViewById(R.id.tvtest);
        tvtest.setText(ip+"\n"+imei);
        <!--<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />-->
        */
    }
    public void Search(View view)
    {
        Intent ni=new Intent(this,Search.class);
        startActivity(ni);
    }

    public void Event(View view)
    {
        Intent ni=new Intent(this,Event.class);
        startActivity(ni);
    }

}
