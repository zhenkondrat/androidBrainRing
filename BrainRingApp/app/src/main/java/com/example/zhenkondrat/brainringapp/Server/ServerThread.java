package com.example.zhenkondrat.brainringapp.Server;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    // DESIGNATE A PORT
    public static final int SERVERPORT = 4444;

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
                serverSocket = new ServerSocket(SERVERPORT);
                while (true) {
                    // LISTEN FOR INCOMING CLIENTS
                    Socket client = serverSocket.accept();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("Connected.", "true");
                        }
                    });

                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        String line = null;
                        while ((line = in.readLine()) != null) {
                            Log.d("ServerActivity", line);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // DO WHATEVER YOU WANT TO THE FRONT END
                                    // THIS IS WHERE YOU CAN BE CREATIVE
                                }
                            });
                        }
                        break;
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

    private String getIP(){
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

