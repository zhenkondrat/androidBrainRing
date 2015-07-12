package com.example.zhenkondrat.brainringapp.Data;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by zhEnkondrat on 02.07.2015.
 */
public class PublicData {
    public static ArrayList<Round> rounds = new ArrayList<Round>();
    public static ArrayList<Member> members = new ArrayList<Member>();
    public static Leader leader = new Leader("None");
    public static ArrayList<Client> clients = new ArrayList<Client>();
    public static int currentRound = 0;
    public static int currentQuestion = 0;

    public static void writeLog()
    {
        Log.v("publicData", "-=-=-");
        Log.v("Leader", leader.getGameName());
        Log.v("CountRounD", String.valueOf( rounds.size()));
        Log.v("CountClient", String.valueOf( clients.size()));
    }

    public  static void UpdateClientsInList()
    {
        for (int i = 0; i < PublicData.clients.size(); i++){
            for (int j = i+1; j < PublicData.clients.size(); j++)
                if (PublicData.clients.get(i).getIp().equals(PublicData.clients.get(j).getIp()))
                {
                    PublicData.clients.remove(i);Log.v("UpdateClientsInList", "-=-=-");
                    UpdateClientsInList();
                    return;
                }
        }
    }
}
