package com.example.quizkart.models;

import java.io.Serializable;

public class QuizResult implements Serializable {

    String quizName;
    int score;
    int maxScore;
    String Date;
    String url;

    public QuizResult() {
    }

    public QuizResult(String quizName, int score, int maxScore, String date) {
        this.quizName = quizName;
        this.score = score;
        this.maxScore = maxScore;
        this.Date = date;

    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
