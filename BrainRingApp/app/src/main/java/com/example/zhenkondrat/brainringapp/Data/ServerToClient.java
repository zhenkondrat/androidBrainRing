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
    public static String data = "";

    private void callClient()
    {
        Log.d( "callClient",  "----");
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
                socket.connect(new InetSocketAddress(serverAddr, 4445), 300);
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                        .getOutputStream())), true);
                // WHERE YOU ISSUE THE COMMANDS
                out.println(command.toString());
                socket.close();
                if(PublicData.clients.get(i).getZayavka()==2)
                    PublicData.clients.get(i).setZayavka(PublicData.clients.get(i).getPriorzayavka());
                //Log.d(PublicData.clients.get(i).getIp(), "-connect");
            } catch (IOException e) {
                e.printStackTrace();
                PublicData.clients.get(i).setPriorzayavka(PublicData.clients.get(i).getZayavka());
                PublicData.clients.get(i).setZayavka(2);
                //Log.d( PublicData.clients.get(i).getIp(),  "-disconnect");
            }
        }
    }

    private void startRound()
    {
        Log.d( "startRound",  "----");
        for(int i = 0; i<PublicData.clients.size();i++)
        {
            if(PublicData.clients.get(i).getZayavka()!=3) break;

            InetAddress serverAddr = null;
            try {
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

    private void sendData()
    {
        Log.d( "sendData",  "----");
        for(int i = 0; i<PublicData.clients.size();i++)
        {
            if(PublicData.clients.get(i).getZayavka()!=2) break;

            InetAddress serverAddr = null;
            try {
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
                out.println(command.toString()+"#"+data);
                socket.close();

                Log.d(PublicData.clients.get(i).getIp(), data+"-send-"+command.toString());
            } catch (IOException e) {
                e.printStackTrace();
                PublicData.clients.get(i).setZayavka(2);
                Log.d( PublicData.clients.get(i).getIp(),  "-disconnect");
            }
        }
    }


    private void zayavkaClient()
    {
        Log.d( "zayavkaClient",  "----");
                InetAddress serverAddr = null;
                try {
                    serverAddr = InetAddress.getByName(data);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(serverAddr, 4445), 100);
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                            .getOutputStream())), true);
                    // WHERE YOU ISSUE THE COMMANDS
                    out.println(command.toString()+"#"+PublicData.leader.getGameName());
                    socket.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
        try {
            PublicData.serverToClient.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        PublicData.sTThread.interrupt();
    }

    @Override
    public void run() {
        switch (command) {
            case call_clients:
                callClient();
                break;
            case start_def_round:
                startRound();
                break;
            case start_vabank_round:
                startRound();
                break;
            case say_team:
                startRound();
                break;
            case delete_client:
                zayavkaClient();
                break;
            case wait_client:
                zayavkaClient();
                break;
            case accept_cleint:
                zayavkaClient();
                break;
            case start_def_time:
                sendData();
                break;

        }
        try {

            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

