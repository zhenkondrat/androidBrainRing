package com.example.zhenkondrat.brainringapp.Server;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.Data.UsualRound;
import com.example.zhenkondrat.brainringapp.Data.VaBankRound;
import com.example.zhenkondrat.brainringapp.R;

public class RoundEditorVaBank extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_editor_va_bank);


        Button btn = (Button) findViewById(R.id.button17);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed = (EditText) findViewById(R.id.editText3);
                VaBankRound vbr = new VaBankRound(ed.getText().toString());

                ed = (EditText) findViewById(R.id.editText4);
                vbr.setTimeQuestion(Integer.parseInt(ed.getText().toString()));

                ed = (EditText) findViewById(R.id.editText5);
                vbr.setMaxBet(Integer.parseInt(ed.getText().toString()));

                ed = (EditText) findViewById(R.id.editText6);
                vbr.setMinBet(Integer.parseInt(ed.getText().toString()));

                PublicData.rounds.add(vbr);
                PublicData.writeLog();

                finish();
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_round_editor_va_bank, menu);
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
