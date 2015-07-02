package com.example.zhenkondrat.brainringapp.Client;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.zhenkondrat.brainringapp.Data.ClientPublicData;
import com.example.zhenkondrat.brainringapp.Data.Leader;
import com.example.zhenkondrat.brainringapp.Data.Member;
import com.example.zhenkondrat.brainringapp.R;

public class TeamActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        EditText ed = (EditText) findViewById(R.id.editText2);
        ed.setText(ClientPublicData.member.getName());

        CheckBox ch = (CheckBox) findViewById(R.id.checkBox4);
        ch.setChecked(ClientPublicData.member.isLight());

        ch = (CheckBox) findViewById(R.id.checkBox5);
        ch.setChecked(ClientPublicData.member.isBlock());

        ch = (CheckBox) findViewById(R.id.checkBox6);
        ch.setChecked(ClientPublicData.member.isSound());

        ch = (CheckBox) findViewById(R.id.checkBox3);
        ch.setChecked(ClientPublicData.member.isBlick());

        Button btn = (Button) findViewById(R.id.button10);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Member member =  ClientPublicData.member;

                EditText ed = (EditText) findViewById(R.id.editText2);
                member.setName(ed.getText().toString());

                CheckBox ch = (CheckBox) findViewById(R.id.checkBox4);
                member.setLight(ch.isChecked());

                ch = (CheckBox) findViewById(R.id.checkBox5);
                member.setBlock(ch.isChecked());

                ch = (CheckBox) findViewById(R.id.checkBox6);
                member.setSound(ch.isChecked());

                ch = (CheckBox) findViewById(R.id.checkBox3);
                member.setBlick(ch.isChecked());

                ClientPublicData.member = member;

                finish();
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_team, menu);
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
