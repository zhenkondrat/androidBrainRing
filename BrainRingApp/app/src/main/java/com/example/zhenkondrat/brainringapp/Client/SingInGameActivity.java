package com.example.zhenkondrat.brainringapp.Client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.example.zhenkondrat.brainringapp.Client.data.SearchServers;
import com.example.zhenkondrat.brainringapp.Data.ClientPublicData;
import com.example.zhenkondrat.brainringapp.R;


public class SingInGameActivity extends Activity {
    private Context act;
    private boolean status=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_game);

        act = getBaseContext();

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
                if (isNetworkOnline(getBaseContext())) {
                    ClientPublicData.ss = new SearchServers(act);
                    ClientPublicData.ssThread = new Thread(ClientPublicData.ss);
                    ClientPublicData.ssThread.start();

                    new Thread(myThread).start();
                }
                else
                    Toast.makeText(getBaseContext(), "Нет подключения Wi-Fi.Сначала подключитесь к сети!", Toast.LENGTH_LONG).show();

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

    private Runnable myThread = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (ClientPublicData.ss.enabled) {
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
                //onResume();
                UpdateList();
            }
        };
    };

    public void UpdateList()
    {
        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        linLayout.removeAllViews();
        if (ClientPublicData.servers.size()==0)
        {
            TextView tw = new TextView(getBaseContext());
            tw.setText("\t Нет игр");
            linLayout.addView(tw);
        }
        else {

            LayoutInflater ltInflater = getLayoutInflater();

            for (int i = 0; i < ClientPublicData.servers.size(); i++) {
                if (ClientPublicData.servers.get(i).getIp()!="") {
                    View item = ltInflater.inflate(R.layout.item, linLayout, false);
                    TextView tv2 = (TextView) item.findViewById(R.id.tvTime);
                    item.setTag(ClientPublicData.servers.get(i).getIp());
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(SingInGameActivity.this, TeamInGameActivity.class);
                            ClientPublicData.selectServer = String.valueOf(view.getTag());
                            Log.v("push", String.valueOf(view.getTag()));
                            //intent.putExtra("id", Integer.parseInt(view.getTag().toString()));
                            startActivity(intent);
                            ClientPublicData.ss.enabled = false;
                            ClientPublicData.ss.Closed();
                            ClientPublicData.ssThread.interrupt();
                        }
                    });

                    tv2.setText( ClientPublicData.servers.get(i).getName());

                    item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
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
