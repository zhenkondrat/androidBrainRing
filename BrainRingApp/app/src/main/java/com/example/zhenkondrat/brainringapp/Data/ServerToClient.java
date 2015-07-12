package com.example.zhenkondrat.brainringapp.Data;

import android.util.Log;

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
public class ServerToClient implements Runnable  {
    public static Command command = Command.none;

    private void callClient()
    {
        for(int i = 0; i<PublicData.clients.size();i++)
        {
            InetAddress serverAddr = null;
            try {
                //serverAddr = InetAddress.getByName("192.168.231.100");
                serverAddr = InetAddress.getByName( PublicData.clients.get(i).getIp());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(serverAddr, 4445), 100);
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                        .getOutputStream())), true);
                // WHERE YOU ISSUE THE COMMANDS
                out.println(command.toString());
                socket.close();

                Log.d(PublicData.clients.get(i).getIp(), "-connect");
            } catch (IOException e) {
                e.printStackTrace();
                PublicData.clients.get(i).setZayavka(2);
                Log.d( PublicData.clients.get(i).getIp(),  "-disconnect");
            }
        }
    }

    private void startRound()
    {
        for(int i = 0; i<PublicData.clients.size();i++)
        {
            if(PublicData.clients.get(i).getZayavka()!=1) break;

            InetAddress serverAddr = null;
            try {
                //serverAddr = InetAddress.getByName("192.168.231.100");
                serverAddr = InetAddress.getByName( PublicData.clients.get(i).getIp());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(serverAddr, 4445), 100);
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                        .getOutputStream())), true);
                // WHERE YOU ISSUE THE COMMANDS
                out.println(command.toString());
                socket.close();

                Log.d(PublicData.clients.get(i).getIp(), "-"+command.toString());
            } catch (IOException e) {
                e.printStackTrace();
                PublicData.clients.get(i).setZayavka(2);
                Log.d( PublicData.clients.get(i).getIp(),  "-disconnect");
            }
        }
    }


    @Override
    public void run() {
        switch (command) {
            case call_clients:
                callClient();
                break;
            case you_green:
                startRound();
                break;
            case you_blue:
                startRound();
                break;
        }
        try {

            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

