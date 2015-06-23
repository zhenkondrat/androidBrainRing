package com.example.zhenkondrat.brainringapp.Data;

/**
 * Created by zhEnkondrat on 23.06.2015.
 */
public class Question{
    private String question;
    private String answer;

    public Question(){}

    public Question(String quest, String answ)
    {
        question = quest;
        answer = answ;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
