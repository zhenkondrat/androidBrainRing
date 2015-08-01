package com.example.zhenkondrat.brainringapp.Server.data;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.zhenkondrat.brainringapp.Data.Client;
import com.example.zhenkondrat.brainringapp.Data.Command;
import com.example.zhenkondrat.brainringapp.Data.PublicData;
import com.example.zhenkondrat.brainringapp.Server.AgreeQuestionActivity;
import com.example.zhenkondrat.brainringapp.Server.ServerQuestionActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhEnkondrat on 18.06.2015.
 */
public class ServerThread implements Runnable {
    // default IP
    public static String SERVERIP = "0.0.0.0";
    // for open&show data on activity
    public static Context context;
    // send data
    public String line = null;
    // PORT
    public static final int SERVERPORT = 4444;
    // for use UI-thread
    private Handler handler = new Handler();
    //socket for connecing client
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
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                });

                Log.v("ServerThread ip: ", SERVERIP);
                serverSocket = new ServerSocket(SERVERPORT);
                while (true) {
                    // LISTEN FOR INCOMING CLIENTS
                    final Socket client = serverSocket.accept();
                    line = null;
                    //cient accept
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

//                        while ((line = in.readLine()) )
//                        {
//                            Log.v("ServerThread get data", line);
//                            break;
//                        }

                        line = in.readLine();

                        // client data parse
                        if(line!=null) {
                            Log.v("ServerThread get data", line);
                            String word="";
                            //this is constructor expression
                            if(line.indexOf("#")>0) {
                                word = line;
                                line = line.substring(0, line.indexOf("#"));
                            }

                            //select the command
                            switch (line) {
                                case "connect":
                                    //send client name of the game
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
                                            //server print team_say
                                            Toast.makeText(((ServerQuestionActivity)context), "Say" + client.getLocalAddress().toString(), Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent( (context), AgreeQuestionActivity.class);
                                            ((ServerQuestionActivity)context).clientSay();
                                            String team = PublicData.getClientFromIP(client.getLocalAddress().toString());
                                            Log.v("ServerThread", "Team-"+team);
                                            Toast.makeText(((ServerQuestionActivity)context), team, Toast.LENGTH_LONG).show();

                                            intent.putExtra("talker", team );
                                            context.startActivity(intent);
                                            try {
                                                this.finalize();
                                            } catch (Throwable throwable) {
                                                throwable.printStackTrace();
                                            }

                                            //clients print team_say
                                            for(int i =0; i<PublicData.clients.size(); i++)
                                            {
                                                if (PublicData.clients.get(i).getIp().endsWith(client.getLocalAddress().toString().substring(4,client.getLocalAddress().toString().length())))
                                                {
                                                    ServerToClient.command = Command.say_team;
                                                    ServerToClient.data = PublicData.clients.get(i).getName();
                                                    Thread cThread = new Thread(new ServerToClient());
                                                    cThread.start();
                                                    Log.v("ServerThread", "on client Say client" + client.getLocalAddress().toString());
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
                                    Log.v("ServerThread", "set ball" + word);
                                    break;
                                case "exit":
                                    word = word.substring(word.indexOf("#")+1 , word.length());
                                    removeClient(word);
                                    Log.v("ServerThread", "exit " + word);
                                    break;
                                default:
                                    if(!inTheClient(line.substring(line.indexOf("@") + 1, line.length())))
                                    PublicData.clients.add(new Client(line.substring(0, line.indexOf("@")), line.substring(line.indexOf("@") + 1, line.length())));

                                    Log.v("ServerThread", line.substring(0, line.indexOf("@")));
                                    Log.v("ServerThread", line.substring(line.indexOf("@") + 2, line.length()));
                            }
                        }
                        //break;
                    } catch (Exception e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("ServerThread."," Connection interrupted. Please reconnect your phones.");
                            }
                        });
                        e.printStackTrace();
                    }
                }
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("ServerThread.","Couldn't detect internet connection.");
                    }
                });
            }
        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.v("ServerThread.","Error");
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

}

