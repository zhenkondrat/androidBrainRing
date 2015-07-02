package com.example.zhenkondrat.brainringapp.Server;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.zhenkondrat.brainringapp.Data.Member;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.Data.UsualRound;
import com.example.zhenkondrat.brainringapp.R;

public class RoundEditorDefault extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_editor_default);

        Button btn = (Button) findViewById(R.id.button18);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed = (EditText) findViewById(R.id.editText7);
                UsualRound ur = new UsualRound(ed.getText().toString());

                ed = (EditText) findViewById(R.id.editText8);
                ur.setCountQuestion(Integer.parseInt(ed.getText().toString()));

                CheckBox ch = (CheckBox) findViewById(R.id.checkBox7);
                ur.setNoLimit(ch.isChecked());

                ed = (EditText) findViewById(R.id.editText9);
                ur.setTimeQuestion(Integer.parseInt(ed.getText().toString()));

                ed = (EditText) findViewById(R.id.editText10);
                ur.setCostScoreTrue(Integer.parseInt(ed.getText().toString()));

                ed = (EditText) findViewById(R.id.editText12);
                ur.setCostScoreFalse(Integer.parseInt(ed.getText().toString()));

                ch = (CheckBox) findViewById(R.id.checkBox8);
                ur.setAcceptSequel(ch.isChecked());

                ed = (EditText) findViewById(R.id.editText11);
                ur.setCountTry(Integer.parseInt(ed.getText().toString()));

                ch = (CheckBox) findViewById(R.id.checkBox9);
                ur.setShowStatistic(ch.isChecked());

                PublicData.rounds.add(ur);
                PublicData.writeLog();

                finish();
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_round_editor_default, menu);
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
