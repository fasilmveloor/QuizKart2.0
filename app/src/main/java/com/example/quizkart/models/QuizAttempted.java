package com.example.quizkart.models;

import java.util.Objects;

public class QuizAttempted {

    private long mLesson;

    private long mMaxMarks;

    private long mPercentage;

    private String mQuizId;

    private String mQuizTitle;

    private String mRemarks;

    private long mScore;

    public long getmLesson() {
        return mLesson;
    }

    public void setmLesson(long mLesson) {
        this.mLesson = mLesson;
    }

    public long getmMaxMarks() {
        return mMaxMarks;
    }

    public void setmMaxMarks(long mMaxMarks) {
        this.mMaxMarks = mMaxMarks;
    }

    public long getmPercentage() {
        return mPercentage;
    }

    public void setmPercentage(long mPercentage) {
        this.mPercentage = mPercentage;
    }

    public String getmQuizId() {
        return mQuizId;
    }

    public void setmQuizId(String mQuizId) {
        this.mQuizId = mQuizId;
    }

    public String getmQuizTitle() {
        return mQuizTitle;
    }

    public void setmQuizTitle(String mQuizTitle) {
        this.mQuizTitle = mQuizTitle;
    }

    public String getmRemarks() {
        return mRemarks;
    }

    public void setmRemarks(String mRemarks) {
        this.mRemarks = mRemarks;
    }

    public long getmScore() {
        return mScore;
    }

    public void setmScore(long mScore) {
        this.mScore = mScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizAttempted)) return false;
        QuizAttempted that = (QuizAttempted) o;
        return getmLesson() == that.getmLesson() && getmMaxMarks() == that.getmMaxMarks() && getmPercentage() == that.getmPercentage() && getmScore() == that.getmScore() && getmQuizId().equals(that.getmQuizId()) && getmQuizTitle().equals(that.getmQuizTitle()) && getmRemarks().equals(that.getmRemarks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getmLesson(), getmMaxMarks(), getmPercentage(), getmQuizId(), getmQuizTitle(), getmRemarks(), getmScore());
    }
}
