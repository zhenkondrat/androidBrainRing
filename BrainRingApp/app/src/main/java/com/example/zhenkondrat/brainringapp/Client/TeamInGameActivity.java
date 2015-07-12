package com.example.zhenkondrat.brainringapp.Client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhenkondrat.brainringapp.Data.ClientPublicData;
import com.example.zhenkondrat.brainringapp.R;
import com.example.zhenkondrat.brainringapp.Server.RoundEditor;


public class TeamInGameActivity extends Activity {

    private TeamInGameActivity tiga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_in_game);
//set team name
        tiga = this;
        TextView tv = (TextView)findViewById(R.id.textView3);
        tv.setText(ClientPublicData.member.getName());
//send mess
        Button btn = (Button) findViewById(R.id.button9);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (!ClientThread.connected) {
                    String s = "";
                    s = ClientPublicData.selectServer;
                    ClientThread.serverIpAddress = s;
                    Log.d("TeamInGame IPserv", s);
                    if (!ClientThread.serverIpAddress.equals("")) {
                        Thread cThread = new Thread(new ClientThread());
                        ClientThread.data = ClientPublicData.member.getName()+"@"+ClientPublicData.clientIP;
                        cThread.start();
                    }
                }
                ClientServer.context = tiga;//getBaseContext();
                Thread fst = new Thread(new ClientServer());
                fst.start();
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_team_in_game, menu);
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
