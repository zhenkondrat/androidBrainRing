package com.example.zhenkondrat.brainringapp.Client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.zhenkondrat.brainringapp.Client.data.ClientToServer;
import com.example.zhenkondrat.brainringapp.Data.ClientPublicData;
import com.example.zhenkondrat.brainringapp.Data.Command;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.Data.VaBankRound;
import com.example.zhenkondrat.brainringapp.R;

public class ClientVaBankActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_va_bank);

        //dialog
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                ClientVaBankActivity.this);

        // Встановлення заголовка
        alertDialog2.setTitle("Раунд ва-банк");

        final EditText ball =new EditText(ClientVaBankActivity.this);
        alertDialog2.setView(ball);
        // Встановлення повідомлення
        try {

            alertDialog2.setMessage("Введите кол-во баллов от "+ String.valueOf (((VaBankRound)PublicData.rounds.get(PublicData.currentRound)).getMinBet())+
            " до "+String.valueOf (((VaBankRound)PublicData.rounds.get(PublicData.currentRound)).getMaxBet()));

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
                        try {
                            int x = Integer.parseInt(ball.getText().toString());
                            ClientToServer.data = String.valueOf(x);
                            ClientToServer.command = Command.set_ball;
                            Thread cThread = new Thread(new ClientToServer());
                            cThread.start();
                        } catch (Exception ex) {
                            Log.v("","is not digit");
                        }
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


    @Override
    protected void onResume() {
        super.onResume();
        //display sunshy all time
        if (ClientPublicData.member.isLight())
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_client_va_bank, menu);
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
