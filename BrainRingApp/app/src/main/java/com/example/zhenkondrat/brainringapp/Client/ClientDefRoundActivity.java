package com.example.zhenkondrat.brainringapp.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhenkondrat.brainringapp.Data.ClientToServer;
import com.example.zhenkondrat.brainringapp.Data.Command;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.Data.ServerToClient;
import com.example.zhenkondrat.brainringapp.Data.UsualRound;
import com.example.zhenkondrat.brainringapp.R;
import com.example.zhenkondrat.brainringapp.Server.CreateGameActivity;

public class ClientDefRoundActivity extends Activity {
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off
    private CountDownTimer countDownTimer; // built in android class
    private TextView textViewShowTime; // will show the time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_def_round);
        ClientServer.context=this;
        textViewShowTime = (TextView) findViewById(R.id.time);

        Button btn;
        //button create game
        btn = (Button) findViewById(R.id.button28);
        btn.setEnabled(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick(View view) {
                ClientToServer.command = Command.say;
                Thread cThread = new Thread(new ClientToServer());
                cThread.start();
            }
        });

    }

    public void setTimer(int time) {
        totalTimeCountInMilliseconds = /*60 * */ time * 1000;//sec
        timeBlinkInMilliseconds = 30 * 1000;
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
//                   timeBlinkInMilliseconds textViewShowTime.setTextAppearance(getApplicationContext(),
//                            R.style.Base_TextAppearance_AppCompat_Display4);
                    // change the style of the textview .. giving a red
                    // alert style

                    if (blink) {
                        textViewShowTime.setVisibility(View.VISIBLE);
                        // if blink is true, textview will be visible
                    } else {
                        textViewShowTime.setVisibility(View.INVISIBLE);
                    }

                    blink = !blink; // toggle the value of blink
                }

                textViewShowTime.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                textViewShowTime.setText("Время истекло!");
                textViewShowTime.setVisibility(View.VISIBLE);
                //buttonStartTime.setVisibility(View.VISIBLE);
                //buttonStopTime.setVisibility(View.GONE);
                //edtTimerValue.setVisibility(View.VISIBLE);
            }

        }.start();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_client_def_round, menu);
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
