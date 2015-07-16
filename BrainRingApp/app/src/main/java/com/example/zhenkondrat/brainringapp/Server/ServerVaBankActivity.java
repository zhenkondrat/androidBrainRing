package com.example.zhenkondrat.brainringapp.Server;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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

import com.example.zhenkondrat.brainringapp.Client.SearchServers;
import com.example.zhenkondrat.brainringapp.Client.TeamInGameActivity;
import com.example.zhenkondrat.brainringapp.Data.ClientPublicData;
import com.example.zhenkondrat.brainringapp.Data.Command;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.Data.ServerToClient;
import com.example.zhenkondrat.brainringapp.R;

public class ServerVaBankActivity extends ActionBarActivity {
    private boolean observer = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_va_bank);
        PublicData.currentRound = getIntent().getExtras().getInt("id");

        PublicData.currentScores.clear();
        for (int i = 0; i < PublicData.clients.size(); i++) {
            PublicData.currentScores.add(0);
        }

        Button btn = (Button)findViewById(R.id.button29);
        getSupportActionBar().setTitle("Ва-банк");
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerToClient.command = Command.start_vabank_round;
                Thread cThread = new Thread(new ServerToClient());
                cThread.start();
            }
        };

        btn.setOnClickListener(ocl);

        new Thread(myThread).start();
    }

    private Runnable myThread = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (observer) {
                try {
                    myHandle.sendMessage(myHandle.obtainMessage());
                    Thread.sleep(1000);
                } catch (Throwable t) {
                }
            }
            try {
                this.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        Handler myHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                UpdateList();
            }
        };
    };

    public void UpdateList()
    {
        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        linLayout.removeAllViews();
        if (PublicData.currentScores.size()==0)
        {
            TextView tw = new TextView(getBaseContext());
            tw.setText("\t Not element");
            linLayout.addView(tw);
        }
        else {

            LayoutInflater ltInflater = getLayoutInflater();

            for (int i = 0; i < PublicData.currentScores.size(); i++) {
                if (PublicData.currentScores.get(i)!=0) {
                    View item = ltInflater.inflate(R.layout.itemteamscore, linLayout, false);
                    TextView tv1 = (TextView) item.findViewById(R.id.Team);
                    TextView tv2 = (TextView) item.findViewById(R.id.score);
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
                    tv2.setText( String.valueOf(PublicData.currentScores.get(i)));

                    item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    //item.setBackgroundColor(colors[0]);
                    linLayout.addView(item);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_server_va_bank, menu);
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
