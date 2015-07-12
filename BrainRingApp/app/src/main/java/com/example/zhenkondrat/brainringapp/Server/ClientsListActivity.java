package com.example.zhenkondrat.brainringapp.Server;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhenkondrat.brainringapp.Client.ClientServer;
import com.example.zhenkondrat.brainringapp.Client.SearchServers;
import com.example.zhenkondrat.brainringapp.Client.SingInGameActivity;
import com.example.zhenkondrat.brainringapp.Data.ClientPublicData;
import com.example.zhenkondrat.brainringapp.Data.Command;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.Data.ServerToClient;
import com.example.zhenkondrat.brainringapp.R;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientsListActivity extends ActionBarActivity {
    int i;//index from cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_list);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clients_list, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //display sunshy all time
//        if (PublicData.leader.isLight())
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        else
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PublicData.UpdateClientsInList();
        ServerToClient.command = Command.call_clients;
        Thread cThread = new Thread(new ServerToClient());
        cThread.start();
        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        linLayout.removeAllViews();
        if (PublicData.clients.size()==0)
        {
            TextView tw = new TextView(getBaseContext());
            tw.setText("\t Not yet clients");
            linLayout.addView(tw);
        }
        else {

            LayoutInflater ltInflater = getLayoutInflater();



            for (i = 0; i < PublicData.clients.size(); i++) {
                if (PublicData.clients.get(i).getName()!="") {
                    View item = ltInflater.inflate(R.layout.itemclient, linLayout, false);
                    TextView tv1 = (TextView) item.findViewById(R.id.tvIDnew);
                  //  TextView tv2 = (TextView) item.findViewById(R.id.tvType);
                    item.setTag(i);
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Intent intent = new Intent(SingInGameActivity.this, TeamInGameActivity.class);
//                            ClientPublicData.selectServer = String.valueOf(view.getTag());
//                            Log.v("push", String.valueOf(view.getTag()));
//                            //intent.putExtra("id", Integer.parseInt(view.getTag().toString()));
//                            startActivity(intent);
                        }
                    });

                    tv1.setText(PublicData.clients.get(i).getName());
                  //  tv2.setText(PublicData.clients.get(i).getIp());

                    Button btn = (Button) item.findViewById(R.id.button20);
                    btn.setTag(i);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PublicData.clients.remove(Integer.parseInt(view.getTag().toString()));
                            Log.v("delete el", String.valueOf(Integer.parseInt(view.getTag().toString())));
                            onResume();
                        }
                    });

                    btn = (Button) item.findViewById(R.id.button21);
                    btn.setTag(i);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PublicData.clients.get(Integer.parseInt(view.getTag().toString())).setZayavka(3);
                            Log.v("green el", String.valueOf(Integer.parseInt(view.getTag().toString())));
                            onResume();
                        }
                    });

                    btn = (Button) item.findViewById(R.id.button22);
                    btn.setTag(i);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PublicData.clients.get(Integer.parseInt(view.getTag().toString())).setZayavka(1);
                            Log.v("cyan el", String.valueOf(Integer.parseInt(view.getTag().toString())));
                            onResume();
                        }
                    });



                    switch (PublicData.clients.get(i).getZayavka())
                    {
                        case 1:
                            item.setBackgroundColor( Color.BLUE);
                            break;
                        case 2:
                            item.setBackgroundColor( Color.RED);
                            break;
                        case 3:
                            item.setBackgroundColor( Color.GREEN);
                            break;
                    }

                    item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    //item.setBackgroundColor(colors[0]);
                    linLayout.addView(item);
                }
            }
        }
        Log.v("---", "show list");
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
