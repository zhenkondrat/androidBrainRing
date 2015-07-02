package com.example.zhenkondrat.brainringapp.Data;

/**
 * Created by zhEnkondrat on 23.06.2015.
 */
public class UsualRound extends Round {
    private int countQuestion;
    private boolean noLimit;
    private int timeQuestion;
    private int costScoreTrue;
    private int costScoreFalse;
    private boolean acceptSequel;
    private int countTry;
    private boolean showStatistic;

    public UsualRound(String name_round)
    {
        super();
        this.nameRound = name_round;
        countQuestion = 10;
        noLimit = false;
        timeQuestion = 60;
        costScoreTrue = 1;
        costScoreFalse = 0;
        acceptSequel = true;
        countTry = 1;
        showStatistic = false;

    }

    public int getCountQuestion() {
        return countQuestion;
    }

    public void setCountQuestion(int countQuestion) {
        this.countQuestion = countQuestion;
    }

    public boolean isNoLimit() {
        return noLimit;
    }

    public void setNoLimit(boolean noLimit) {
        this.noLimit = noLimit;
    }

    public int getTimeQuestion() {
        return timeQuestion;
    }

    public void setTimeQuestion(int timeQuestion) {
        this.timeQuestion = timeQuestion;
    }

    public int getCostScoreTrue() {
        return costScoreTrue;
    }

    public void setCostScoreTrue(int costScoreTrue) {
        this.costScoreTrue = costScoreTrue;
    }

    public int getCostScoreFalse() {
        return costScoreFalse;
    }

    public void setCostScoreFalse(int costScoreFalse) {
        this.costScoreFalse = costScoreFalse;
    }

    public boolean isAcceptSequel() {
        return acceptSequel;
    }

    public void setAcceptSequel(boolean acceptSequel) {
        this.acceptSequel = acceptSequel;
    }

    public int getCountTry() {
        return countTry;
    }

    public void setCountTry(int countTry) {
        this.countTry = countTry;
    }

    public boolean isShowStatistic() {
        return showStatistic;
    }

    public void setShowStatistic(boolean showStatistic) {
        this.showStatistic = showStatistic;
    }
}
