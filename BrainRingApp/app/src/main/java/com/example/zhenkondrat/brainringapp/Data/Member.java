package com.example.zhenkondrat.brainringapp.Data;

/**
 * Created by zhEnkondrat on 23.06.2015.
 */
public class Member {
    private String name;
    private boolean light;
    private boolean block;
    private boolean sound;
    private boolean blick;

    public Member(String _name)
    {
        name = _name;
        light=true;
        block=true;
        sound=false;
        blick=false;
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

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isBlick() {
        return blick;
    }

    public void setBlick(boolean blick) {
        this.blick = blick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
