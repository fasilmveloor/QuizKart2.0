package com.example.quizkart.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Quiz {

    private int mMaxMarks;
    private Map<String, Question> mQuestions;


    /**
     * For local usage only, it is not stored in database
     */
    private boolean mAttempted;

    public Quiz() {

    }



//    public Quiz(Quiz toClone) {
//        mMaxMarks = 15;
//
//        mAttempted = toClone.mAttempted;
//
//
//
//
//        if (toClone.mQuestions != null) {
//            Map<String, Question> questions = new HashMap<>();
//            for (Map.Entry<String, Question> questionEntry : toClone.mQuestions.entrySet()) {
//                questions.put(questionEntry.getKey(), new Question(questionEntry.getValue()));
//            }
//            mQuestions = questions;
//        }

    //}



    public int getmMaxMarks() {
        return mMaxMarks;
    }

    public void setmMaxMarks(int mMaxMarks) {
        this.mMaxMarks = mMaxMarks;
    }

    public Map<String, Question> getmQuestions() {
        return mQuestions;
    }

    public void setmQuestions(Map<String, Question> mQuestions) {
        this.mQuestions = mQuestions;
    }



    public boolean ismAttempted() {
        return mAttempted;
    }

    public void setmAttempted(boolean mAttempted) {
        this.mAttempted = mAttempted;
    }


    public void reset() {
        for (Map.Entry<String, Question> questionEntry : mQuestions.entrySet()) {
            Map<String, Option> options = questionEntry.getValue().getOptions();
            for (Map.Entry<String, Option> optionEntry : options.entrySet()) {
                optionEntry.getValue().setIs_correct(false);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;
        Quiz quiz = (Quiz) o;
        return getmMaxMarks() == quiz.getmMaxMarks() && ismAttempted() == quiz.ismAttempted()  && getmQuestions().equals(quiz.getmQuestions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getmMaxMarks(), getmQuestions(), ismAttempted());
    }
}
