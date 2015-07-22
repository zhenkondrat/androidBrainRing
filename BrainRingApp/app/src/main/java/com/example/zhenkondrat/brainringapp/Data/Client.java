package com.example.zhenkondrat.brainringapp.Data;

import java.io.Serializable;

/**
 * Created by zhEnkondrat on 03.07.2015.
 */
public class Client implements Serializable {
    private String name;
    private String ip;
    private int zayavka;
    private int priorzayavka;

    public Client()
    {
        name="None";
        zayavka=1;
        priorzayavka=2;
    }

    public Client(String n, String ip)
    {
        name=n;
        this.ip = ip.replace("/","");
        zayavka=1;
        priorzayavka=2;
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

    public int getPriorzayavka() {
        return priorzayavka;
    }

    public void setPriorzayavka(int priorzayavka) {
        this.priorzayavka = priorzayavka;
    }
}
