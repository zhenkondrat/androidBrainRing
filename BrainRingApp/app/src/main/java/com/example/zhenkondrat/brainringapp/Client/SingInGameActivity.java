package com.example.zhenkondrat.brainringapp.Client;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhenkondrat.brainringapp.Data.ClientPublicData;
import com.example.zhenkondrat.brainringapp.R;
import com.example.zhenkondrat.brainringapp.Server.CreateGameActivity;

import java.util.ArrayList;


public class SingInGameActivity extends Activity {
    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_game);

        act = this;

        Button btn;
        //button create game
        btn = (Button) findViewById(R.id.button7);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingInGameActivity.this, TeamActivity.class);

                startActivity(intent);
            }
        });

        //button search
        btn = (Button) findViewById(R.id.button8);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SearchServers ss = new SearchServers(getBaseContext());
                Thread cThread = new Thread(new SearchServers(act));
                cThread.start();
                //ss.scan();
            }
        });
    }

    public void UpdateList()
    {
        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        linLayout.removeAllViews();
        if (ClientPublicData.servers.size()==0)
        {
            TextView tw = new TextView(getBaseContext());
            tw.setText("\t Server not find");
            linLayout.addView(tw);
        }
        else {

            LayoutInflater ltInflater = getLayoutInflater();

            for (int i = 0; i < ClientPublicData.servers.size(); i++) {
                if (ClientPublicData.servers.get(i)!="") {
                    View item = ltInflater.inflate(R.layout.item, linLayout, false);
                    //TextView tv1 = (TextView) item.findViewById(R.id.tvName);
                    TextView tv2 = (TextView) item.findViewById(R.id.tvTime);
                    item.setTag(ClientPublicData.servers.get(i));
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(SingInGameActivity.this, TeamInGameActivity.class);
                            ClientPublicData.selectServer = String.valueOf(view.getTag());
                            Log.v("push", String.valueOf(view.getTag()));
                            //intent.putExtra("id", Integer.parseInt(view.getTag().toString()));
                            startActivity(intent);
                        }
                    });

                    //tv1.setText("Name Of Game");
                    tv2.setText("IP: " + ClientPublicData.servers.get(i));

                    item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    //item.setBackgroundColor(colors[0]);
                    linLayout.addView(item);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //display sunshy all time
        if (ClientPublicData.member.isLight())
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        UpdateList();
        Log.v("---", "show list");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sing_in_game, menu);
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
