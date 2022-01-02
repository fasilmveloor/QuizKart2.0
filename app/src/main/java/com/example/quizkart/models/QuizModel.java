package com.example.quizkart.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QuizModel {

    private String title;
    private int marks;
    private HashMap<String, Question> questions;
    private boolean isAttempted;

    public QuizModel() {
    }

    public QuizModel(String title, int marks, HashMap<String, Question> questions, boolean isAttempted) {
        this.title = title;
        this.marks = marks;

        this.isAttempted = isAttempted;

        if (questions != null) {
            HashMap<String, Question> copyquestions = new HashMap<>();
            for (Map.Entry<String, Question> questionEntry : questions.entrySet()) {
                copyquestions.put(questionEntry.getKey(), new Question(questionEntry.getValue()));
            }
            this.questions = copyquestions;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public HashMap<String, Question> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<String, Question> questions) {
        this.questions = questions;
    }

    public boolean isAttempted() {
        return isAttempted;
    }

    public void setAttempted(boolean attempted) {
        isAttempted = attempted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizModel)) return false;
        QuizModel quizModel = (QuizModel) o;
        return marks == quizModel.marks && isAttempted == quizModel.isAttempted && title.equals(quizModel.title) && questions.equals(quizModel.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, marks, questions, isAttempted);
    }
}
