package com.example.zhenkondrat.brainringapp.Data;

/**
 * Created by zhEnkondrat on 14.07.2015.
 */
public class Servers {
    private String name;
    private String ip;

    public Servers(){}
    public Servers(String n, String i){
        name = n;
        ip = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
