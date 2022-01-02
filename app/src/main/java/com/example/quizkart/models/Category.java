package com.example.quizkart.models;

public class Category {

    private String name;
    private int question;
    private String url;
    private String description;

    public Category(){}

    public Category(String name, int question, String url, String description) {
        this.name = name;
        this.question = question;
        this.url = url;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getQuestion() {
        return question;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
}
