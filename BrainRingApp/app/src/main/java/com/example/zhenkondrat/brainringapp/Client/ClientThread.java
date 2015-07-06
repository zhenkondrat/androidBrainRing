package com.example.zhenkondrat.brainringapp.Client;

import android.os.Handler;
import android.util.Log;

import com.example.zhenkondrat.brainringapp.Server.ServerThread;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by zhEnkondrat on 18.06.2015.
 */
public class ClientThread implements Runnable {
    public static String serverIpAddress = "";
    public static String data="";

    public static boolean connected = false;

    private Handler handler = new Handler();

    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
            Log.d("ClientActivity", "C: Connecting..."+serverIpAddress);
            Socket socket = new Socket(serverAddr, ServerThread.SERVERPORT);
            connected = true;
            //while (connected) {
            if (connected) {
                try {
                    Log.d("ClientActivity", "C: Sending command.");
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                            .getOutputStream())), true);
                    // WHERE YOU ISSUE THE COMMANDS
                    out.println(data);
                    Log.d("ClientActivity", "C: Sent.->"+data);
                } catch (Exception e) {
                    Log.e("ClientActivity", "S: Error", e);
                }
            }
           // socket.close();
            Log.d("ClientActivity", "C: Closed.");
        } catch (Exception e) {
            Log.e("ClientActivity", "C: Error", e);

        }
        connected = false;
    }
}
