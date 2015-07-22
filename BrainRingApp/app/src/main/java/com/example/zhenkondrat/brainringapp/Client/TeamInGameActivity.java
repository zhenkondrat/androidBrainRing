package com.example.zhenkondrat.brainringapp.Client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhenkondrat.brainringapp.Data.ClientPublicData;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.R;
import com.example.zhenkondrat.brainringapp.Server.RoundEditor;


public class TeamInGameActivity extends Activity {

    private TeamInGameActivity tiga;
    private Button btnExit;
    private ClientThread ct;
    private ClientServer cs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_in_game);
//set team name
        tiga = this;
        ClientServer.context=this;
        TextView tv = (TextView)findViewById(R.id.textView3);
        tv.setText(ClientPublicData.member.getName());
        btnExit = (Button) findViewById(R.id.button27);
        btnExit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ct.Closed();
                cs.Closed();
            }
        });
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
                        ct=new ClientThread();
                        Thread cThread = new Thread(ct);
                        ClientThread.data = ClientPublicData.member.getName()+"@"+ClientPublicData.clientIP;
                        cThread.start();
                    }
                }
                ClientServer.context = tiga;//getBaseContext();
                cs = new ClientServer();
                Thread fst = new Thread(cs);
                fst.start();
                Toast.makeText(getBaseContext(), "Ваша заявка на игру подана", Toast.LENGTH_LONG).show();
                btnExit.setVisibility(View.VISIBLE);
            }

        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //onBackPressed();
            //dialog
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    TeamInGameActivity.this);

            // Встановлення заголовка
            alertDialog2.setTitle("Exit");

            // Встановлення повідомлення
            try {

                alertDialog2.setMessage("You want to exit?");

            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // Встановлення іконки
            //alertDialog2.setIcon(R.drawable.delete);

            // Встановлення події на позитивну відповідь
            alertDialog2.setPositiveButton("Да",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //OnClickYes
                            onBackPressed();
                        }
                    });
            // Встановлення події при негативній умові
            alertDialog2.setNegativeButton("Нет",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            //Показуємо діалог
            alertDialog2.show();

            //end dialog
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

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
