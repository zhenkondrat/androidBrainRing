package com.example.zhenkondrat.brainringapp.Data;

/**
 * Created by zhEnkondrat on 23.06.2015.
 */
public class Leader {
    private String gameName;
    private boolean light;
    private boolean block;

    public Leader()
    {
        light=true;
        block=true;
    }

    public Leader(String n)
    {
        gameName = n;
        light=true;
        block=true;
    }
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }
}
