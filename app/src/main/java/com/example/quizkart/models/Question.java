package com.example.quizkart.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Question {

    private String question;

    private long mMarks;

    private HashMap<String, Option> mOptions;



    public Question() {
    }

    public Question(String question, long mMarks, HashMap<String, Option> mOptions) {
        this.question = question;
        this.mMarks = mMarks;
        //this.mOptions = mOptions;

        if (mOptions != null) {
            HashMap<String, Option> options = new HashMap<>();
            for (Map.Entry<String, Option> optionEntry : mOptions.entrySet()) {
                options.put(optionEntry.getKey(), new Option(optionEntry.getValue()));
            }
            this.mOptions = options;
        }
    }

    /**
     * Copy constructor
     *
     * @param toClone Question object to be shallow copied
     */


    public Question(Question toClone) {
        question = toClone.question;
        mMarks = 1;


        if (toClone.mOptions != null) {
            HashMap<String, Option> options = new HashMap<>();
            for (Map.Entry<String, Option> optionEntry : toClone.mOptions.entrySet()) {
                options.put(optionEntry.getKey(), new Option(optionEntry.getValue()));
            }
            mOptions = options;
        }
    }

    public String getQuestion() {

        return question;
    }

    public void setQuestion(String question) {

        this.question = question;
    }

    public long getMarks() {

        return mMarks;

    }

    public void setMarks(Long marks) {

        mMarks = marks;
    }

    public HashMap<String, Option> getOptions() {

        return mOptions;
    }

    public void setOptions(HashMap<String, Option> options) {

        mOptions = options;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return mMarks == question.mMarks &&
                Objects.equals(this.question, question.question) &&
                Objects.equals(mOptions, question.mOptions) ;
    }

    @Override
    public int hashCode() {

        return Objects.hash(question, mMarks, mOptions);
    }

    /**
     * Clears all the isCorrect flag from options of this question.
     */
    public void resetOptions() {
        if (mOptions != null) {
            for (Map.Entry<String, Option> optionEntry : mOptions.entrySet()) {
                optionEntry.getValue().setIs_correct(false);
            }
        }
    }
}
