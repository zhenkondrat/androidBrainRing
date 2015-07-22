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
public class ClientToServer implements Runnable  {
    public static Command command = Command.none;
    public static String data = "";

    private void doIt()
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

