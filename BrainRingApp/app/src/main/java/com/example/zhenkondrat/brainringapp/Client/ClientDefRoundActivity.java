package com.example.zhenkondrat.brainringapp.Client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhenkondrat.brainringapp.Client.data.ClientServer;
import com.example.zhenkondrat.brainringapp.Client.data.ClientToServer;
import com.example.zhenkondrat.brainringapp.Data.ClientPublicData;
import com.example.zhenkondrat.brainringapp.Data.Command;
import com.example.zhenkondrat.brainringapp.R;

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
                //stopTimer();

                ClientToServer.command = Command.say;
                Thread cThread = new Thread(new ClientToServer());
                cThread.start();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //display sunshy all time
        if (ClientPublicData.member.isLight())
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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

    public void stopTimer(){
        countDownTimer.cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            //onBackPressed();
//            //dialog
//            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
//                    ClientDefRoundActivity.this);
//
//            // Встановлення заголовка
//            alertDialog2.setTitle("Exit");
//
//            // Встановлення повідомлення
//            try {
//
//                alertDialog2.setMessage("You want to exit?");
//
//            } catch (Exception e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//            // Встановлення іконки
//            //alertDialog2.setIcon(R.drawable.delete);
//
//
//            // Встановлення події при негативній умові
//            alertDialog2.setNegativeButton("OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//            //Показуємо діалог
//            alertDialog2.show();
//
//            //end dialog
//        }
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
