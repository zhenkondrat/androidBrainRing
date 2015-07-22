package com.example.zhenkondrat.brainringapp.Data;

import java.io.Serializable;

/**
 * Created by zhEnkondrat on 23.06.2015.
 */
public class VaBankRound extends Round implements Serializable {
    private int timeQuestion;
    private int maxBet;
    private int minBet;

    public VaBankRound(String name_round)
    {
        this.nameRound = name_round;
        timeQuestion = 60;
        maxBet = 10;
        minBet = 1;
    }

    public int getTimeQuestion() {
        return timeQuestion;
    }

    public void setTimeQuestion(int timeQuestion) {
        this.timeQuestion = timeQuestion;
    }

    public int getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(int maxBet) {
        this.maxBet = maxBet;
    }

    public int getMinBet() {
        return minBet;
    }

    public void setMinBet(int minBet) {
        this.minBet = minBet;
    }
}
