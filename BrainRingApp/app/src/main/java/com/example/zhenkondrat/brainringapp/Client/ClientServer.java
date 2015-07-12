package com.example.zhenkondrat.brainringapp.Client;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

import com.example.zhenkondrat.brainringapp.Data.Client;
import com.example.zhenkondrat.brainringapp.Data.PublicData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import com.example.zhenkondrat.brainringapp.Data.Command;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by zhEnkondrat on 06.07.2015.
 */
public class ClientServer implements Runnable {
    // DEFAULT IP
    public static String SERVERIP = "192.168.231.101";
    public static TeamInGameActivity context;
    public String line = null;

    // DESIGNATE A PORT
    public static final int SERVERPORT = 4445;

    private Handler handler = new Handler();

    private ServerSocket serverSocket;

    public void run() {

        try {
            if (SERVERIP != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("Listening on IP: ", SERVERIP);
                    }
                });

                Log.v("Listening on IP1: ", SERVERIP);
                serverSocket = new ServerSocket(4445);
                while (true) {
                    // LISTEN FOR INCOMING CLIENTS
                    final Socket client = serverSocket.accept();
                    line = null;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("CLConnected.", "true");
                            Log.v("CLclientIP", client.getInetAddress().toString());
                            Log.v("CLserverIP", serverSocket.getInetAddress().toString());
                            Log.v("CLserverIP2", SERVERIP);
                        }
                    });
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                        while ((line = in.readLine()) != null) {
                            Log.v("ServerActivity-----", line);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                            break;
                        }
                        Intent intent = null;
                        if(line!=null)
                            switch (line)
                            {
                                case "red":
                                    break;
                                case "you_blue":
                                    intent = new Intent( this.context, ClientVaBankActivity.class);
//                                    ClientPublicData.selectServer = String.valueOf(view.getTag());
                                    Log.v("aaaaaa", "aaaaaaaa");
//                                    //intent.putExtra("id", Integer.parseInt(view.getTag().toString()));
                                    context.startActivity(intent);
                                    break;
                                case "you_green":
                                    intent = new Intent( this.context, ClientDefRoundActivity.class);
//                                    ClientPublicData.selectServer = String.valueOf(view.getTag());
                                    Log.v("aaaaaa", "aaaaaaaa");
//                                    //intent.putExtra("id", Integer.parseInt(view.getTag().toString()));
                                    context.startActivity(intent);
                                    break;
                                case "remoney":
                                    break;
                                case "say":
                                    Log.d("ClientServerActivity", "Say");
                                    break;
                                case "start":
                                    break;
                            }

                         //break;
                    } catch (Exception e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("","Oops. Connection interrupted. Please reconnect your phones.");
                            }
                        });
                        e.printStackTrace();
                    }
                }
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("","Couldn't detect internet connection.");
                    }
                });
            }
        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.v("","Error");
                }
            });
            e.printStackTrace();
        }
    }



    public String getIP(){
        WifiManager myWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
        int myIp = myWifiInfo.getIpAddress();

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

}
