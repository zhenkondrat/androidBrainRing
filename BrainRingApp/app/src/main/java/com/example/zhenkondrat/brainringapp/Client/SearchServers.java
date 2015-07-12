package com.example.zhenkondrat.brainringapp.Client;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.zhenkondrat.brainringapp.Data.ClientPublicData;
import com.example.zhenkondrat.brainringapp.Server.ServerThread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by zhEnkondrat on 24.06.2015.
 */
public class SearchServers implements Runnable  {
    private String ip;
    private Activity activiy;

    public SearchServers(Activity context){
        ClientPublicData.servers = new ArrayList<String>();
        Log.d("SearchServer", "Create");
        //ip = getLocalIpAddress();
        WifiManager myWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
        int myIp = myWifiInfo.getIpAddress();
        ip = toIP(myIp);
        ClientPublicData.clientIP = ip;
        Log.d("SearchServer", "setIP ="+ip);
        activiy = context;
    }

    public void scan()
    {
        ip = ip.substring(0, ip.lastIndexOf('.')+1);
        for(int i=2;i<255;i++)
        //for(int i=92;i<105;i++)
        {
            InetAddress serverAddr = null;
            try {
                //Log.d("connect", ip.concat(String.valueOf(i)));
                serverAddr = InetAddress.getByName(ip.concat(String.valueOf(i)));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            try {
//                Socket socket = new Socket(serverAddr, ServerThread.SERVERPORT);
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(serverAddr, ServerThread.SERVERPORT),100);
                socket.close();
                ClientPublicData.servers.add(ip.concat(String.valueOf(i)));
                ((SingInGameActivity)activiy).finish();
                Log.d("connect", ip.concat(String.valueOf(i)));
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("disconnect", ip.concat(String.valueOf(i)));
            }
        }
    }



    public static String toIP(int myIp) {

        int intMyIp3 = myIp/0x1000000;
        int intMyIp3mod = myIp%0x1000000;

        int intMyIp2 = intMyIp3mod/0x10000;
        int intMyIp2mod = intMyIp3mod%0x10000;

        int intMyIp1 = intMyIp2mod/0x100;
        int intMyIp0 = intMyIp2mod%0x100;

        return String.valueOf(intMyIp0)
                        + "." + String.valueOf(intMyIp1)
                        + "." + String.valueOf(intMyIp2)
                        + "." + String.valueOf(intMyIp3);
    }

    @Override
    public void run() {
       scan();
        try {
            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
