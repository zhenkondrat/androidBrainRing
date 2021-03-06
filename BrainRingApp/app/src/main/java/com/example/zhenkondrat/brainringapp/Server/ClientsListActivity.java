package com.example.zhenkondrat.brainringapp.Server;

import android.graphics.Color;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhenkondrat.brainringapp.Data.Command;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.Server.data.ServerToClient;
import com.example.zhenkondrat.brainringapp.R;

public class ClientsListActivity extends ActionBarActivity {
    int i;//index from cycle
    boolean observer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_list);
        getSupportActionBar().setTitle("Список клиентов");
        new Thread(myThread).start();
    }

    private Runnable myThread = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (observer) {
                try {
                    myHandle.sendMessage(myHandle.obtainMessage());
                    Thread.sleep(5000);
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
                PublicData.serverToClient.command = Command.call_clients;
                PublicData.serverToClient = new ServerToClient();
                PublicData.sTThread = new Thread(PublicData.serverToClient);
                PublicData.sTThread.start();
                UpdateList();
            }
        };
    };


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
        if (PublicData.leader.isLight())
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        PublicData.UpdateClientsInList();
        UpdateList();
        Log.v("---", "show list");
    }

private void  UpdateList(){
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

                tv1.setText(PublicData.clients.get(i).getName());

                Button btn = (Button) item.findViewById(R.id.button20);
                btn.setTag(i);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        PublicData.serverToClient.command = Command.delete_client;
                        PublicData.serverToClient.data = PublicData.clients.get(Integer.parseInt(view.getTag().toString())).getIp();
                        PublicData.stcClose();

                        PublicData.serverToClient = new ServerToClient();
                        PublicData.sTThread = new Thread(PublicData.serverToClient);
                        PublicData.sTThread.start();

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

                        PublicData.serverToClient.command = Command.accept_client;
                        PublicData.serverToClient.data = PublicData.clients.get(Integer.parseInt(view.getTag().toString())).getIp();
                        PublicData.stcClose();

                        PublicData.serverToClient = new ServerToClient();
                        PublicData.sTThread = new Thread(PublicData.serverToClient);
                        PublicData.sTThread.start();

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

                        PublicData.serverToClient.command = Command.wait_client;
                        PublicData.serverToClient.data = PublicData.clients.get(Integer.parseInt(view.getTag().toString())).getIp();
                        PublicData.stcClose();

                        PublicData.serverToClient = new ServerToClient();
                        PublicData.sTThread = new Thread(PublicData.serverToClient);
                        PublicData.sTThread.start();


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
}

    @Override
    protected void onStop()
    {
        // TODO Auto-generated method stub
        super.onStop();
        PublicData.serverToClient.command = Command.none;
        PublicData.serverToClient.data = "";
        observer=false;
        Log.v("MyApp", "onStop");
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
