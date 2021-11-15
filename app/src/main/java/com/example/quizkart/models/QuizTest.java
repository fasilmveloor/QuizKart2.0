package com.example.quizkart.models;

import java.util.List;

public class QuizTest {

    private String TestName;
    private String rating;
    private String bestSeller;
    private String member;

    private List<Question> playList = null;
    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBestSeller() {
        return bestSeller;
    }

    public void setBestSeller(String bestSeller) {
        this.bestSeller = bestSeller;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public List<Question> getPlayList() {
        return playList;
    }

    public void setPlayList(List<Question> playList) {
        this.playList = playList;
    }


}
