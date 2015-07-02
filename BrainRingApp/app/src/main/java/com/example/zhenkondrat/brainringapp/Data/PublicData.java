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

    public static void writeLog()
    {
        Log.v("publicData", "-=-=-");
        Log.v("Leader", leader.getGameName());
        Log.v("CountRounD", String.valueOf( rounds.size()));
        Log.v("CountClient", String.valueOf( clients.size()));
    }
}
