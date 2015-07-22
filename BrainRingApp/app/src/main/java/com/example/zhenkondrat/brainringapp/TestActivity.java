package com.example.zhenkondrat.brainringapp;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zhenkondrat.brainringapp.Client.ClientThread;
import com.example.zhenkondrat.brainringapp.Client.SearchServers;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.Statist.Statistic;
import com.example.zhenkondrat.brainringapp.Server.ServerThread;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public class TestActivity extends ActionBarActivity {
    boolean status = false;
    Statistic st;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        if (isNetworkOnline (getBaseContext()))
        Toast.makeText(getBaseContext(),"Y" , Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getBaseContext(),"N" , Toast.LENGTH_LONG).show();

        Button btn;
        //button create game
        btn = (Button) findViewById(R.id.button11);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager myWifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
                int myIp = myWifiInfo.getIpAddress();
                ServerThread.SERVERIP = SearchServers.toIP(myIp);
                Thread fst = new Thread(new ServerThread());
                fst.start();
            }
        });

        btn = (Button) findViewById(R.id.button12);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st = new Statistic(PublicData.rounds, PublicData.clients);
                st.clearScore();
                st.setScore(0,0,5);
                st.setContext(getBaseContext());
                st.saveToFileObj("file", st);

            }
        });

        btn = (Button) findViewById(R.id.button13);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ClientThread.connected) {
                    WifiManager myWifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);

                    WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
                    int myIp = myWifiInfo.getIpAddress();
                    //ip = SearchServers.toIP(myIp);
                    String s = "";
                    s = SearchServers.toIP(myIp);
                    ClientThread.serverIpAddress = s;
                    Log.d("-------", s);
                    if (!ClientThread.serverIpAddress.equals("")) {
                        Thread cThread = new Thread(new ClientThread());
                        cThread.start();
                    }
                }
            }
        });

        btn = (Button) findViewById(R.id.button14);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st=new Statistic();
                st.setContext(getBaseContext());
                st.loadFromFile("file");
            }
        });
    }

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
        getMenuInflater().inflate(R.menu.menu_test, menu);
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

    // GETS THE IP ADDRESS OF YOUR PHONE'S NETWORK
    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) { return inetAddress.getHostAddress().toString(); }
                }
            }
        } catch (SocketException ex) {
            Log.e("ServerActivity", ex.toString());
        }
        return null;
    }
}
