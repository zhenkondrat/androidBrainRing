package com.example.zhenkondrat.brainringapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zhenkondrat.brainringapp.Client.SingInGameActivity;
import com.example.zhenkondrat.brainringapp.Server.CreateGameActivity;
import com.example.zhenkondrat.brainringapp.Server.RoundEditor;


public class MenuActivity extends Activity {
    private boolean status = false;
    Button btnWifi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btn;
        //button create game
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CreateGameActivity.class);

                startActivity(intent);
            }
        });

        //button singin game
        btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, SingInGameActivity.class);

                startActivity(intent);
            }
        });

        //button singin game
        btn = (Button) findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, TestActivity.class);

                startActivity(intent);
            }
        });

        btnWifi = (Button) findViewById(R.id.button30);
        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(!wifi.isWifiEnabled());
            }
        });
        new Thread(myThread).start();
    }

    private Runnable myThread = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    myHandle.sendMessage(myHandle.obtainMessage());
                    Thread.sleep(1000);
                } catch (Throwable t) {
                }
            }
        }

        Handler myHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                if (isNetworkOnline(getBaseContext()))
                    btnWifi.setBackgroundResource(R.drawable.network);
                else
                    btnWifi.setBackgroundResource(R.drawable.disconnect);
            }
        };
    };

    boolean isNetworkOnline (Context context) {

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
            status = true;
        } else {
            status = false;
        }
        return status;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
