package com.example.zhenkondrat.brainringapp.Server;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhenkondrat.brainringapp.Client.data.SearchServers;
import com.example.zhenkondrat.brainringapp.Data.Command;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.Server.data.ServerToClient;
import com.example.zhenkondrat.brainringapp.Server.data.ServerThread;
import com.example.zhenkondrat.brainringapp.Statist.Statistic;
import com.example.zhenkondrat.brainringapp.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainGameActivity extends ActionBarActivity {
    private final int MENU_DEL = 1;
    private int delId=0;
    private Statistic st=null;
    int z=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        WifiManager myWifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
        int myIp = myWifiInfo.getIpAddress();
        ServerThread.SERVERIP = SearchServers.toIP(myIp);
        Log.v("MainGA: ", ServerThread.SERVERIP);
        //start server socket

        if (PublicData.serverThread!=null)
        {
            PublicData.serverThread.Closed();
            PublicData.sThread.stop();
        }
        PublicData.serverThread = new ServerThread();
        PublicData.sThread = new Thread(PublicData.serverThread);
        PublicData.sThread.start();

        ActionBar supportActionBar = getSupportActionBar();
        getSupportActionBar().setTitle("");
//       supportActionBar.show();
        supportActionBar.setLogo(R.drawable.logo);
//        supportActionBar.hide();


        ActionBar actionBar = new ActionBar() {
            @Override
            public void setCustomView(View view) {

            }

            @Override
            public void setCustomView(View view, LayoutParams layoutParams) {

            }

            @Override
            public void setCustomView(int resId) {

            }

            @Override
            public void setIcon(@DrawableRes int resId) {

            }

            @Override
            public void setIcon(Drawable icon) {

            }

            @Override
            public void setLogo(@DrawableRes int resId) {

            }

            @Override
            public void setLogo(Drawable logo) {

            }

            @Override
            public void setListNavigationCallbacks(SpinnerAdapter adapter, OnNavigationListener callback) {

            }

            @Override
            public void setSelectedNavigationItem(int position) {

            }

            @Override
            public int getSelectedNavigationIndex() {
                return 0;
            }

            @Override
            public int getNavigationItemCount() {
                return 0;
            }

            @Override
            public void setTitle(CharSequence title) {

            }

            @Override
            public void setTitle(@StringRes int resId) {

            }

            @Override
            public void setSubtitle(CharSequence subtitle) {

            }

            @Override
            public void setSubtitle(int resId) {

            }

            @Override
            public void setDisplayOptions(int options) {

            }

            @Override
            public void setDisplayOptions(int options, int mask) {

            }

            @Override
            public void setDisplayUseLogoEnabled(boolean useLogo) {

            }

            @Override
            public void setDisplayShowHomeEnabled(boolean showHome) {

            }

            @Override
            public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {

            }

            @Override
            public void setDisplayShowTitleEnabled(boolean showTitle) {

            }

            @Override
            public void setDisplayShowCustomEnabled(boolean showCustom) {

            }

            @Override
            public void setBackgroundDrawable(@Nullable Drawable d) {

            }

            @Override
            public View getCustomView() {
                return null;
            }

            @Nullable
            @Override
            public CharSequence getTitle() {
                return null;
            }

            @Nullable
            @Override
            public CharSequence getSubtitle() {
                return null;
            }

            @Override
            public int getNavigationMode() {
                return 0;
            }

            @Override
            public void setNavigationMode(int mode) {

            }

            @Override
            public int getDisplayOptions() {
                return 0;
            }

            @Override
            public Tab newTab() {
                return null;
            }

            @Override
            public void addTab(Tab tab) {

            }

            @Override
            public void addTab(Tab tab, boolean setSelected) {

            }

            @Override
            public void addTab(Tab tab, int position) {

            }

            @Override
            public void addTab(Tab tab, int position, boolean setSelected) {

            }

            @Override
            public void removeTab(Tab tab) {

            }

            @Override
            public void removeTabAt(int position) {

            }

            @Override
            public void removeAllTabs() {

            }

            @Override
            public void selectTab(Tab tab) {

            }

            @Nullable
            @Override
            public Tab getSelectedTab() {
                return null;
            }

            @Override
            public Tab getTabAt(int index) {
                return null;
            }

            @Override
            public int getTabCount() {
                return 0;
            }

            @Override
            public int getHeight() {
                return 0;
            }

            @Override
            public void show() {

            }

            @Override
            public void hide() {

            }

            @Override
            public boolean isShowing() {
                return false;
            }

            @Override
            public void addOnMenuVisibilityListener(OnMenuVisibilityListener listener) {

            }

            @Override
            public void removeOnMenuVisibilityListener(OnMenuVisibilityListener listener) {

            }
        };
        //getSupportActionBar();//ActionBar();
        actionBar.setCustomView(R.layout.action_bar_item); //load your layout
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_CUSTOM);

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayShowTitleEnabled(false);
        ViewGroup v = (ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.activity_guid, null);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v,
                new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        Gravity.END | Gravity.RIGHT));

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //onBackPressed();
            //dialog
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    MainGameActivity.this);

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

    public boolean isEndGame()
    {
        boolean res=false;

        return res;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  display sunshy all time
        if (PublicData.leader.isLight())
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        linLayout.removeAllViews();
        if (PublicData.rounds.size()==0)
        {
            TextView tw = new TextView(getBaseContext());
            tw.setText("\t Нету созданых раундов");
            linLayout.addView(tw);
        }
        else {

            LayoutInflater ltInflater = getLayoutInflater();

            for (int i = 0; i < PublicData.rounds.size(); i++) {
                if (PublicData.rounds.get(i).toString()!="") {
                    View item = ltInflater.inflate(R.layout.itemrounds, linLayout, false);
                    TextView tv1 = (TextView) item.findViewById(R.id.tvID);
                    TextView tv2 = (TextView) item.findViewById(R.id.tvType);
                    item.setTag(i);
                    registerForContextMenu(item);
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //if round is finish do nothink
                            if(PublicData.rounds.get(Integer.parseInt(view.getTag().toString())).isFin()) return;

                            Intent intent = null;
                            if(PublicData.rounds.get(Integer.parseInt(view.getTag().toString())).getClass().toString().indexOf("UsualRound")>=0) {
                                intent = new Intent(MainGameActivity.this, ServerQuestionActivity.class);
                                intent.putExtra("id", Integer.parseInt(view.getTag().toString()));
                                        z++;
                                        Log.v("defRound", "z="+String.valueOf(z));
                                ServerToClient.command = Command.start_def_round;
                                Thread cThread = new Thread(new ServerToClient());
                                cThread.start();
                            }
                            if(PublicData.rounds.get(Integer.parseInt(view.getTag().toString())).getClass().toString().indexOf("VaBank")>=0) {
                                intent = new Intent(MainGameActivity.this, ServerVaBankActivity.class);
                                intent.putExtra("id", Integer.parseInt(view.getTag().toString()));
                            }

                            startActivity(intent);
                        }
                    });

                    tv1.setText(PublicData.rounds.get(i).getNameRound());

                    if(PublicData.rounds.get(i).getClass().toString().indexOf("UsualRound")>=0)
                    tv2.setText("Default");

                    if(PublicData.rounds.get(i).getClass().toString().indexOf("VaBank")>=0)
                        tv2.setText("Va Bank");

                    if(PublicData.rounds.get(i).isFin())
                        item.setBackgroundColor(Color.RED);
                    else
                        item.setBackgroundColor(Color.GREEN);



                    item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    //item.setBackgroundColor(colors[0]);
                    linLayout.addView(item);
                }
            }
        }
        Log.v("---", "show list");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_game, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, MENU_DEL, 0, "Удалить раунд");
        delId = Integer.parseInt(v.getTag().toString());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case MENU_DEL:
                PublicData.rounds.remove(delId);
                onResume();
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.round:
                Intent intent = new Intent(MainGameActivity.this, RoundEditor.class);
                Log.v("---", "---");
                startActivity(intent);
                Log.v("---","---");
                break;
            case R.id.members:
                Intent inten = new Intent(MainGameActivity.this, ClientsListActivity.class);
                Log.v("---", "---");
                startActivity(inten);
                Log.v("---","---");
                break;
            case R.id.start:
                st = new Statistic(PublicData.rounds, PublicData.clients);
                st.clearScore();
                //st.setScore(0,0,5);
                st.setContext(getBaseContext());
                //st.saveToFileObj("file", st);
                Toast.makeText(getBaseContext(), "Новая игра начата", Toast.LENGTH_LONG).show();
                break;
            case R.id.finish:
                st.saveToFileObj("file", st);
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
