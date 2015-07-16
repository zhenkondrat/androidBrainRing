package com.example.zhenkondrat.brainringapp.Client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhenkondrat.brainringapp.Data.ClientPublicData;
import com.example.zhenkondrat.brainringapp.Data.Servers;
import com.example.zhenkondrat.brainringapp.R;
import com.example.zhenkondrat.brainringapp.Server.ServerThread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
    private Context activiy;
    private String line = null;
    public static boolean enabled = true;

    public SearchServers(Context context){
        ClientPublicData.servers = new ArrayList<Servers>();
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
        enabled=true;
        ip = ip.substring(0, ip.lastIndexOf('.')+1);
        for(int i=1;i<255;i++)
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

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                        .getOutputStream())), true);
                out.println("connect");

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while ((line = in.readLine()) != null)
                {
                    Log.d("Searchsever", line);
                    break;
                }
                socket.close();
                ClientPublicData.servers.add(new Servers(line, ip.concat(String.valueOf(i))));//ip.concat(String.valueOf(i)));
                //((SingInGameActivity)activiy).finish();
                //UpdateList();
                //Toast.makeText(activiy,ip.concat(String.valueOf(i)), Toast.LENGTH_LONG).show();
                Log.d("connect", ip.concat(String.valueOf(i)));
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("disconnect", ip.concat(String.valueOf(i)));
            }
        }
        enabled=false;
        //Toast.makeText(activiy,"Серверов не найдено, проверте подключение!", Toast.LENGTH_LONG).show();
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
