package com.example.jeonsang_gyu.test1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG="MainActivity";
    private TextView tvClickMe;
    ConnectivityManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);




        WifiManager wifiManager=(WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        manager=(ConnectivityManager)getApplicationContext().getSystemService((Context.CONNECTIVITY_SERVICE));
        final NetworkInfo wifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        tvClickMe=(TextView)findViewById(R.id.tvClickMe);

        tvClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wifi.isConnected())
                {

                    Toast.makeText(getApplicationContext(),"Wifi is connected",Toast.LENGTH_LONG).show();

                    Intent BottomIntent= new Intent(MainActivity.this,BottomNavigation.class);
                    startActivity(BottomIntent);
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                    //finish();
                }
                else
                {

                    Toast.makeText(getApplicationContext(),"Wifi is not connected...please check your network",Toast.LENGTH_LONG).show();
                }


            }
        });



    }
}
