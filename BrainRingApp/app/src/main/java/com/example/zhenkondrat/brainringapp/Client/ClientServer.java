package com.example.zhenkondrat.brainringapp.Client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.zhenkondrat.brainringapp.Data.Client;
import com.example.zhenkondrat.brainringapp.Data.PublicData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import com.example.zhenkondrat.brainringapp.Data.Command;
import com.example.zhenkondrat.brainringapp.Server.ServerThread;

import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by zhEnkondrat on 06.07.2015.
 */
public class ClientServer implements Runnable {
    // DEFAULT IP
    public static String SERVERIP = "192.168.231.101";
    public static Activity context;
    public String line = null;
    private boolean isEnabled = true;

    // DESIGNATE A PORT
    public static final int SERVERPORT = 4445;

    private Handler handler = new Handler();

    private ServerSocket serverSocket;

    public void Closed(){
//        ClientToServer.command = Command.exit;
//        ClientToServer.data= ClientPublicData.clientIP;
//        Thread cThread = new Thread(new ClientToServer());
//        cThread.start();
        isEnabled=false;
        try {
            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void run() {

        try {
            if (SERVERIP != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                Log.v("Listening on IP1: ", SERVERIP);
                serverSocket = new ServerSocket(4445);
                while (isEnabled) {
                    // LISTEN FOR INCOMING CLIENTS
                    final Socket client = serverSocket.accept();
                    line = null;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
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
                        if(line!=null){
                            String word="";
                            if(line.indexOf("#")>0) {
                                word = line;
                                line = line.substring(0, line.indexOf("#"));
                            }

                            switch (line)
                            {
                                case "red":
                                    break;
                                case "start_vabank_round":
                                    intent = new Intent( ((TeamInGameActivity)this.context), ClientVaBankActivity.class);
                                    Log.v("aaaaaa", "aaaaaaaa");
                                    context.startActivity(intent);
                                    break;
                                case "start_def_round":
                                    intent = new Intent( ((TeamInGameActivity)this.context), ClientDefRoundActivity.class);
                                    Log.v("aaaaaa", "aaaaaaaa");
                                    context.startActivity(intent);
                                    break;
                                case "start_def_time":
                                    Log.v("start_def_time: ", "---");
                                    word = word.substring(word.indexOf("#")+1 , word.length());
                                    ((ClientDefRoundActivity)this.context).setTimer(Integer.parseInt(word));
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((ClientDefRoundActivity)ClientServer.context).startTimer();
                                            Toast.makeText( ((ClientDefRoundActivity)ClientServer.context), " start timer", Toast.LENGTH_LONG).show();
                                            try {
                                                this.finalize();
                                            } catch (Throwable throwable) {
                                                throwable.printStackTrace();
                                            }
                                        }
                                    });
                                    this.isEnabled=false;
                                    //Toast.makeText( ((ClientDefRoundActivity)this.context), word+" start timer", Toast.LENGTH_LONG).show();
                                    line=null;
                                    break;
                                case "delete_client":
                                    word = word.substring(word.indexOf("#")+1 , word.length());
                                    Toast.makeText( ((TeamInGameActivity)this.context), word+". Вашая заявка отклонена", Toast.LENGTH_LONG).show();
                                    break;
                                case "accept_client":
                                    word = word.substring(word.indexOf("#")+1 , word.length());
                                    Toast.makeText( ((TeamInGameActivity)this.context), word+". Вашая заявка принята", Toast.LENGTH_LONG).show();
                                    break;
                                case "wait_client":
                                    word = word.substring(word.indexOf("#")+1 , word.length());
                                    Toast.makeText( ((TeamInGameActivity)this.context), word+". Вашая заявка подана", Toast.LENGTH_LONG).show();
                                    break;
                                case "say_team":
                                    word = word.substring(word.indexOf("#")+1 , word.length());
                                    Toast.makeText( ((ClientDefRoundActivity)this.context), word+" say", Toast.LENGTH_LONG).show();
                                    Log.d("ClientServerActivity", "Say");
                                    break;
                                case "start":
                                    break;
                            }
                        }
                         //break;
                    } catch (Exception e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("","ClientServer. Connection interrupted. Please reconnect your phones.");
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
