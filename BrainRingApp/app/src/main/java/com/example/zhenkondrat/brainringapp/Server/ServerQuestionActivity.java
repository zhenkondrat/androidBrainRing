package com.example.zhenkondrat.brainringapp.Server;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhenkondrat.brainringapp.Data.Command;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.Server.data.ServerToClient;
import com.example.zhenkondrat.brainringapp.Data.UsualRound;
import com.example.zhenkondrat.brainringapp.R;

public class ServerQuestionActivity extends Activity {
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off
    private CountDownTimer countDownTimer; // built in android class
    private boolean timerCancel; //fleg if timer work calceled
    private TextView textViewShowTime; // will show the time
    private TextView num;

    private boolean observer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_question);
        PublicData.serverThread.context = this;
        PublicData.currentRound = getIntent().getExtras().getInt("id");

        textViewShowTime = (TextView) findViewById(R.id.timetv);
        num = (TextView) findViewById(R.id.textView18);
        PublicData.currentQuestion=0;
        nextQuestion();

        Button btn = (Button)findViewById(R.id.button23);

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  textViewShowTime.setTextAppearance(getApplicationContext(),
                          R.style.Base_TextAppearance_AppCompat_Inverse);

                ServerToClient.command = Command.start_def_time;
                ServerToClient.data = String.valueOf( ((UsualRound)PublicData.rounds.get(PublicData.currentRound)).getTimeQuestion());
                PublicData.serverToClient = new ServerToClient();
                PublicData.sTThread = new Thread(PublicData.serverToClient);
                PublicData.sTThread.start();
                Log.d("SQA", "st--"+ServerToClient.data);
                new Thread(myThread).start();
            }
        };

        btn.setOnClickListener(ocl);
        btn = (Button)findViewById(R.id.button24);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialog
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        ServerQuestionActivity.this);

                // Встановлення заголовка
                alertDialog2.setTitle("Подверждение");

                // Встановлення повідомлення
                try {

                    alertDialog2.setMessage("Вы увереный что хотите пропустить?");

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
                                nextQuestion();
                            }
                        });
                // Встановлення події при негативній умові
                alertDialog2.setNegativeButton("Нет",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dialog.cancel();
                                PublicData.rounds.get(PublicData.currentRound).setFinish(true);
                                finish();
                            }
                        });

                //Показуємо діалог
                alertDialog2.show();

                //end dialog

            }
        });

    }

    private Runnable myThread = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (observer) {
                try {
                    myHandle.sendMessage(myHandle.obtainMessage());
                    Thread.sleep(500);
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
                setTimer();
                startTimer();
                observer=false;
            }
        };
    };

    public void clientSay()
    {
        timerCancel = true;
    }

    public void nextQuestion()
    {
        if(((UsualRound)PublicData.rounds.get(PublicData.currentRound)).getCountQuestion()>PublicData.currentQuestion) {
            PublicData.currentQuestion++;
            num.setText(String.valueOf(PublicData.currentQuestion));
        }
        else
        {
            //dialog
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    ServerQuestionActivity.this);

            // Встановлення заголовка
            alertDialog2.setTitle("Подверждение");

            // Встановлення повідомлення
            try {

                alertDialog2.setMessage("Вы хотите добавить вопрос?");

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
                            ((UsualRound)PublicData.rounds.get(PublicData.currentRound)).setCountQuestion(((UsualRound)PublicData.rounds.get(PublicData.currentRound)).getCountQuestion()+1);
                            nextQuestion();
                        }
                    });
            // Встановлення події при негативній умові
            alertDialog2.setNegativeButton("Нет",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dialog.cancel();
                            PublicData.rounds.get(PublicData.currentRound).setFinish(true);
                            finish();
                        }
                    });

            //Показуємо діалог
            alertDialog2.show();

            //end dialog
        }
    }

    private void setTimer() {
        int time = 0;
        if (PublicData.rounds.get(PublicData.currentRound).getClass().toString().contains("UsualRound")) {
            time = ((UsualRound)PublicData.rounds.get(PublicData.currentRound)).getTimeQuestion();
        };

        totalTimeCountInMilliseconds = time * 1000;//sec

        timeBlinkInMilliseconds = 30 * 1000;
    }

    private void startTimer() {
        timerCancel = false;
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
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
                if(!timerCancel)
                textViewShowTime.setText("Время истекло!");
                textViewShowTime.setVisibility(View.VISIBLE);
            }

        }.start();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            //onBackPressed();
//            //dialog
//            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
//                    ServerQuestionActivity.this);
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
    protected void onResume() {
        super.onResume();
        //  display sunshy all time
        if (PublicData.leader.isLight())
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_server_question, menu);
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
