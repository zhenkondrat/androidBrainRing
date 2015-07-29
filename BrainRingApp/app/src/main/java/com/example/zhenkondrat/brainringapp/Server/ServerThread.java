package com.example.zhenkondrat.brainringapp.Server;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.zhenkondrat.brainringapp.Client.ClientDefRoundActivity;
import com.example.zhenkondrat.brainringapp.Client.ClientVaBankActivity;
import com.example.zhenkondrat.brainringapp.Client.TeamInGameActivity;
import com.example.zhenkondrat.brainringapp.Data.Client;
import com.example.zhenkondrat.brainringapp.Data.Command;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.Data.ServerToClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by zhEnkondrat on 18.06.2015.
 */
public class ServerThread implements Runnable {
    // DEFAULT IP
    public static String SERVERIP = "192.168.231.101";
    Context context;
    public String line = null;

    // DESIGNATE A PORT
    public static final int SERVERPORT = 4444;

    private Handler handler = new Handler();

    private ServerSocket serverSocket;

    public void Closed(){
        try {
            serverSocket.close();
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
                serverSocket = new ServerSocket(SERVERPORT);
                while (true) {
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

                        while ((line = in.readLine()) != null)
                        {
                            Log.d("ServerActivity", line);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                            break;
                        }

                        if(line!=null) {
                            String word="";
                            if(line.indexOf("#")>0) {
                                word = line;
                                line = line.substring(0, line.indexOf("#"));
                            }
                            switch (line) {
                                case "connect":
                                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client
                                            .getOutputStream())), true);
                                    out.println(PublicData.leader.getGameName());
                                    break;
                                case "say":
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(500);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Toast.makeText(((ServerQuestionActivity)context), "Say" + client.getLocalAddress().toString(), Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent( (context), AgreeQuestionActivity.class);

                                            String team = PublicData.getClientFromIP(client.getLocalAddress().toString());
                                            Log.v("agree", "aaaaaaaa-"+team);
                                            Toast.makeText(((ServerQuestionActivity)context), team, Toast.LENGTH_LONG).show();

                                            intent.putExtra("talker", team );
                                            context.startActivity(intent);
                                            try {
                                                this.finalize();
                                            } catch (Throwable throwable) {
                                                throwable.printStackTrace();
                                            }

                                            for(int i =0; i<PublicData.clients.size(); i++)
                                            {
                                                if (PublicData.clients.get(i).getIp().endsWith(client.getLocalAddress().toString().substring(4,client.getLocalAddress().toString().length())))
                                                {
                                                    ServerToClient.command = Command.say_team;
                                                    ServerToClient.data = PublicData.clients.get(i).getName();
                                                    Thread cThread = new Thread(new ServerToClient());
                                                    cThread.start();
                                                    Log.d("ClientServerActivity", "Say" + client.getLocalAddress().toString());
                                                }
                                            }
                                        }
                                    });

                                    break;
                                case "set_ball":
                                    word = word.substring(word.indexOf("#")+1 , word.length());
                                    int i;
                                    for (i = 0; i < PublicData.clients.size(); i++) {
                                        if(client.getLocalAddress().toString().endsWith(PublicData.clients.get(i).getIp()))
                                        {
                                            break;
                                        }
                                    }
                                    PublicData.currentScores.set(i,Integer.parseInt(word));
                                    Log.d("server thread", "set " + word);
                                    break;
                                case "exit":
                                    word = word.substring(word.indexOf("#")+1 , word.length());
                                    removeClient(word);
                                    Log.d("server thread", "set " + word);
                                    break;
                                default:
                                    if(!inTheClient(line.substring(line.indexOf("@") + 1, line.length())))
                                    PublicData.clients.add(new Client(line.substring(0, line.indexOf("@")), line.substring(line.indexOf("@") + 1, line.length())));

                                    Log.v("client", line.substring(0, line.indexOf("@")));
                                    Log.v("client", line.substring(line.indexOf("@") + 2, line.length()));
                            }
                        }
                        //break;
                    } catch (Exception e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("","ServerThread. Connection interrupted. Please reconnect your phones.");
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

    private boolean inTheClient(String ip)
    {
        for(int i=0; i<PublicData.clients.size();i++)
        {
            if(PublicData.clients.get(i).getIp().equals(ip))
                return true;
        }
        return false;
    }

    private void removeClient(String ip)
    {
        for(int i=0; i<PublicData.clients.size();i++)
        {
            if(PublicData.clients.get(i).getIp().equals(ip))
                PublicData.clients.remove(i);
        }
    }
    // GETS THE IP ADDRESS OF YOUR PHONE'S NETWORK
    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) { return inetAddress.getHostAddress().toString(); }
                }
            }
        } catch (SocketException ex) {
            Log.e("ServerActivity", ex.toString());
        }
        return null;
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

