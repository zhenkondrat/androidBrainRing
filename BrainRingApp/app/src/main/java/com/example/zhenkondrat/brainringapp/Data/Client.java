package com.example.zhenkondrat.brainringapp.Data;

/**
 * Created by zhEnkondrat on 03.07.2015.
 */
public class Client {
    private String name;
    private String ip;
    private int zayavka;

    public Client()
    {
        name="None";
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

    public int getZayavka() {
        return zayavka;
    }

    public void setZayavka(int zayavka) {
        this.zayavka = zayavka;
    }
}
