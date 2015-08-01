package com.example.zhenkondrat.brainringapp.Data;

import com.example.zhenkondrat.brainringapp.Client.data.ClientToServer;
import com.example.zhenkondrat.brainringapp.Client.data.SearchServers;

import java.util.ArrayList;

/**
 * Created by zhEnkondrat on 02.07.2015.
 */
public class ClientPublicData {
    public static ArrayList<Servers> servers = new ArrayList<Servers>();
    public static Member member = new Member("None");
    public static String selectServer;
    public static String clientIP = "0.0.0.0";

    public static SearchServers ss=null;
    public static Thread ssThread = null;

    public static ClientToServer cts = null;
    public static Thread ctsThread = null;
}
