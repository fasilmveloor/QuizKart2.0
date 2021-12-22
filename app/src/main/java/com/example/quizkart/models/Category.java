package com.example.quizkart.models;

public class Category {

    private final String name;
    private final int question;
    private final String image;
    private final String description;

    public Category(String name, int question, String image, String description) {
        this.name = name;
        this.question = question;
        this.image = image;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getQuestion() {
        return question;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
