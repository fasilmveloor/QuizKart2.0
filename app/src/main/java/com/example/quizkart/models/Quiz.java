package com.example.quizkart.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Quiz {

    private String mDescription;
    private Map<String, String> mFiles;
    private int mMaxMarks;
    private Map<String, Question> mQuestions;
    private String mTitle;

    /**
     * For local usage only, it is not stored in database
     */
    private boolean mAttempted;

    /**
     * For local usage only, it is not stored in database
     */
    private boolean mIsBookmarked;

    public Quiz(Quiz toClone) {
        mDescription = toClone.mDescription;
        mMaxMarks = toClone.mMaxMarks;
        mTitle = toClone.mTitle;
        mAttempted = toClone.mAttempted;
        mIsBookmarked = toClone.mIsBookmarked;

        if (toClone.mFiles != null) {
            Map<String, String> files = new HashMap<>();
            for (Map.Entry<String, String> fileEntry : toClone.mFiles.entrySet()) {
                files.put(fileEntry.getKey(), fileEntry.getValue());
            }
            mFiles = files;
        }

        if (toClone.mQuestions != null) {
            Map<String, Question> questions = new HashMap<>();
            for (Map.Entry<String, Question> questionEntry : toClone.mQuestions.entrySet()) {
                questions.put(questionEntry.getKey(), new Question(questionEntry.getValue()));
            }
            mQuestions = questions;
        }

    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Map<String, String> getmFiles() {
        return mFiles;
    }

    public void setmFiles(Map<String, String> mFiles) {
        this.mFiles = mFiles;
    }

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

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public boolean ismAttempted() {
        return mAttempted;
    }

    public void setmAttempted(boolean mAttempted) {
        this.mAttempted = mAttempted;
    }

    public boolean ismIsBookmarked() {
        return mIsBookmarked;
    }

    public void setmIsBookmarked(boolean mIsBookmarked) {
        this.mIsBookmarked = mIsBookmarked;
    }

    public void reset() {
        for (Map.Entry<String, Question> questionEntry : mQuestions.entrySet()) {
            Map<String, Option> options = questionEntry.getValue().getOptions();
            for (Map.Entry<String, Option> optionEntry : options.entrySet()) {
                optionEntry.getValue().setIsCorrect(false);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;
        Quiz quiz = (Quiz) o;
        return getmMaxMarks() == quiz.getmMaxMarks() && ismAttempted() == quiz.ismAttempted() && ismIsBookmarked() == quiz.ismIsBookmarked() && getmDescription().equals(quiz.getmDescription()) && getmFiles().equals(quiz.getmFiles()) && getmQuestions().equals(quiz.getmQuestions()) && getmTitle().equals(quiz.getmTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getmDescription(), getmFiles(), getmMaxMarks(), getmQuestions(), getmTitle(), ismAttempted(), ismIsBookmarked());
    }
}
