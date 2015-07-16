package com.example.zhenkondrat.brainringapp.Server;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.zhenkondrat.brainringapp.Data.Leader;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.R;

public class GuidActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);
        getSupportActionBar().setTitle("Настройки");

        EditText ed = (EditText) findViewById(R.id.editText13);
        ed.setText(PublicData.leader.getGameName());

        CheckBox ch = (CheckBox) findViewById(R.id.checkBox10);
        ch.setChecked(PublicData.leader.isLight());

        ch = (CheckBox) findViewById(R.id.checkBox11);
        ch.setChecked(PublicData.leader.isBlock());


        Button btn = (Button) findViewById(R.id.button19);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Leader leader = new Leader();

                EditText ed = (EditText) findViewById(R.id.editText13);
                leader.setGameName(ed.getText().toString());

                CheckBox ch = (CheckBox) findViewById(R.id.checkBox10);
                leader.setLight(ch.isChecked());

                ch = (CheckBox) findViewById(R.id.checkBox11);
                leader.setBlock(ch.isChecked());

                PublicData.leader = leader;
                PublicData.writeLog();

                finish();
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guid, menu);
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
