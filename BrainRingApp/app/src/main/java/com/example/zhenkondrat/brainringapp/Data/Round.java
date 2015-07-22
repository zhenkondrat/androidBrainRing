package com.example.zhenkondrat.brainringapp.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhEnkondrat on 23.06.2015.
 */
public abstract class Round implements Serializable {
    protected String nameRound;
    protected boolean isFinish=false;
    //protected ArrayList<Question> questions;

    public Round()
    {
//        questions = new ArrayList<Question>();
    }

//    public void addQuestion(Question q)
//    {
//        questions.add(q);
//    }

    public String getNameRound() {
        return nameRound;
    }

    public void setNameRound(String nameRound) {
        this.nameRound = nameRound;
    }

    public void setFinish(boolean fin) {
        this.isFinish = fin;
    }

    public boolean isFin() {
       return this.isFinish;
    }
}
