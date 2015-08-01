package com.example.zhenkondrat.brainringapp.Client.data;

import android.util.Log;

import com.example.zhenkondrat.brainringapp.Data.ClientPublicData;
import com.example.zhenkondrat.brainringapp.Data.Command;
import com.example.zhenkondrat.brainringapp.Server.data.ServerThread;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by zhEnkondrat on 08.07.2015.
 */
public class ClientToServer implements Runnable  {
    public static Command command = Command.none;
    public static String data = "";
    //for first connect to server
    public static boolean connected = false;

    private void connect_server()
    {
        try {
            InetAddress serverAddr = InetAddress.getByName(ClientPublicData.selectServer);
            Log.d("ClientToServ", "Connecting..."+ClientPublicData.selectServer);
            Socket socket = new Socket(serverAddr, ServerThread.SERVERPORT);
            connected = true;
            //while (connected) {
            if (connected) {
                try {
                    Log.d("ClientToServ", "Sending command.");
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                            .getOutputStream())), true);
                    out.println(data);
                    Log.d("ClientToServ", "Sent.->"+data);
                } catch (Exception e) {
                    Log.e("ClientToServ", "Error", e);
                }
            }
            // socket.close();
            Log.d("ClientToServ", "Closed.");
        } catch (Exception e) {
            Log.e("ClientToServ", "Error", e);

        }
        connected = false;
    }

    private void doIt()
    {
         InetAddress serverAddr = null;
            try {
                serverAddr = InetAddress.getByName(ClientPublicData.selectServer);
                Log.v("ClienToserv-Say", ClientPublicData.selectServer);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(serverAddr, 4444), 100);
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                        .getOutputStream())), true);
                // WHERE YOU ISSUE THE COMMANDS
                out.println(command.toString());
                socket.close();

                Log.d(ClientPublicData.selectServer, "-"+command.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    private void set_ball()
    {
        InetAddress serverAddr = null;
        try {
            serverAddr = InetAddress.getByName(ClientPublicData.selectServer);
            Log.v("ClienToserv", ClientPublicData.selectServer);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(serverAddr, 4444), 100);
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                    .getOutputStream())), true);
            // WHERE YOU ISSUE THE COMMANDS
            out.println(command.toString()+"#"+data);
            socket.close();

            Log.d(ClientPublicData.selectServer, "-"+command.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void exit()
    {
        InetAddress serverAddr = null;
        try {
            serverAddr = InetAddress.getByName(ClientPublicData.selectServer);
            Log.v("ClienToserv", ClientPublicData.selectServer);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(serverAddr, 4444), 100);
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                    .getOutputStream())), true);
            // WHERE YOU ISSUE THE COMMANDS
            out.println(command.toString()+"#"+data);
            socket.close();

            Log.d(ClientPublicData.selectServer, "-"+command.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        switch (command) {
            case first_connect:
                connect_server();
                break;
            case say:
                doIt();
                break;
            case set_ball:
                set_ball();
                break;
            case exit:
                exit();
                break;
        }
        try {

            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

