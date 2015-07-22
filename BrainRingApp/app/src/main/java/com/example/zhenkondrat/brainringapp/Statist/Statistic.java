package com.example.zhenkondrat.brainringapp.Statist;

import android.content.Context;
import android.widget.Toast;

import com.example.zhenkondrat.brainringapp.Data.Client;
import com.example.zhenkondrat.brainringapp.Data.Round;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhEnkondrat on 12.07.2015.
 */
public class Statistic implements Serializable {
    private ArrayList<Client> client;
    private ArrayList<ArrayList<Integer>> score;
    private ArrayList<Round> round;
    private ArrayList<Integer> rowScore;
    private transient Context context=null;

    public Statistic(){
        this.round = null;
        this.client = null;
    }

    public Statistic(ArrayList<Round> r, ArrayList<Client> c){
        this.round = r;
        this.client = c;
        score = new ArrayList<ArrayList<Integer>>();
        clearScore();
    }

    public void clearScore()
    {
        score.clear();
        for(int i=0;i<round.size(); i++)
        {
            rowScore = new ArrayList<Integer>();
            for(int j=0;j<client.size(); j++)
            {
                rowScore.add(0);
            }
            score.add(rowScore);
        }
    }

    public void removeRound(int id)
    {
        round.remove(id);
        score.remove(id);
    }

    public void setScore(int round_id, int client_id, int value)
    {
        score.get(round_id).set(client_id, value);
    }

    public int getTotalClient( int client_id)
    {
        int sum=0;
        for(int i=0;i<round.size(); i++)
        {
           sum+=score.get(i).get(client_id);
        }
        return  sum;
    }

    public void saveToFile(String fileName)
    {
        try {

        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(this);
        os.close();
        fos.close();
        }catch(Exception ex){
            Toast.makeText(context, "Not save to file", Toast.LENGTH_LONG).show();
        }
    }
    public void saveToFileObj(String fileName, Statistic obj)
    {
        try {

            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.close();
            fos.close();
        }catch(Exception ex){
            Toast.makeText(context, "Not save to file", Toast.LENGTH_LONG).show();
        }
    }
    public void loadFromFile(String fileName)
    {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            Statistic obj = (Statistic) is.readObject();
            is.close();
            fis.close();
            try {
                this.round = obj.round;
                this.client = obj.client;
                this.score = obj.score;
            }catch(Exception ex){
                Toast.makeText(context, "Load from file, not load to object", Toast.LENGTH_LONG).show();
            }

        }catch(Exception ex){
            Toast.makeText(context, "Not load from file", Toast.LENGTH_LONG).show();
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
