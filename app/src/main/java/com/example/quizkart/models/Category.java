package com.example.quizkart.models;

public class Category {

    private final String name;
    private final int question;
    private final int image;

    public int getImage() {
        return image;
    }

    public Category(String name, int question, int image) {
        this.name = name;
        this.question = question;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getQuestion() {
        return question;
    }


}
