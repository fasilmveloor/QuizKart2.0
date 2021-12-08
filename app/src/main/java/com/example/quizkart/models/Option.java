package com.example.quizkart.models;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Option {
    private String mDescription;

    /**
     * Since we are using the same model to store correct answers and scholar's answers, this field
     * can represent either
     */
    private boolean mIsCorrect;

    private String mRemarks;

    /**
     * This field should be used for storing key of realtime database snapshot, otherwise ignore it
     */

    public Option() {

    }

    /**
     * Copy constructor
     *
     * @param toClone Option object to be shallow copied
     */
    public Option(@NonNull Option toClone) {
        mDescription = toClone.mDescription;
        mIsCorrect = toClone.mIsCorrect;
        mRemarks = toClone.mRemarks;
    }

    public String getDescription() {

        return mDescription;
    }

    public void setDescription(String description) {

        mDescription = description;
    }

    public boolean isCorrect() {
        return mIsCorrect;
    }


    public void setIsCorrect(boolean isCorrect) {

        mIsCorrect = isCorrect;
    }

    public String getRemarks() {

        return mRemarks;
    }

    public void setRemarks(String remarks) {

        mRemarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return mIsCorrect == option.mIsCorrect &&
                Objects.equals(mDescription, option.mDescription) &&
                Objects.equals(mRemarks, option.mRemarks);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mDescription, mIsCorrect, mRemarks);
    }
}
