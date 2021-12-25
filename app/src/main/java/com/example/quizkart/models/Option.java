package com.example.quizkart.models;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Option {
    private String description;
    private boolean is_correct;

    public Option() {
    }

    public Option(String description, boolean is_correct) {
        this.description = description;
        this.is_correct = is_correct;
    }

    public Option(Option value) {
        this.description = value.description;
        this.is_correct = value.is_correct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIs_correct() {
        return is_correct;
    }

    public void setIs_correct(boolean is_correct) {
        this.is_correct = is_correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Option)) return false;
        Option option = (Option) o;
        return is_correct == option.is_correct && description.equals(option.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, is_correct);
    }
}
