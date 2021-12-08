package com.example.quizkart.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Question {

    private String mDescription;

    private long mMarks;

    private Map<String, Option> mOptions;


    private Map<String, String> mFiles;

    private String mExtra;


    public Question() {
    }

    /**
     * Copy constructor
     *
     * @param toClone Question object to be shallow copied
     */
    public Question(Question toClone) {
        mDescription = toClone.mDescription;
        mMarks = toClone.mMarks;
        mExtra = toClone.mExtra;

        if (toClone.mFiles != null) {
            Map<String, String> files = new HashMap<>();
            for (Map.Entry<String, String> fileEntry : toClone.mFiles.entrySet()) {
                files.put(fileEntry.getKey(), fileEntry.getValue());
            }
            mFiles = files;
        }

        if (toClone.mOptions != null) {
            Map<String, Option> options = new HashMap<>();
            for (Map.Entry<String, Option> optionEntry : toClone.mOptions.entrySet()) {
                options.put(optionEntry.getKey(), new Option(optionEntry.getValue()));
            }
            mOptions = options;
        }
    }

    public String getDescription() {

        return mDescription;
    }

    public void setDescription(String description) {

        mDescription = description;
    }

    public long getMarks() {

        return mMarks;

    }

    public void setMarks(Long marks) {

        mMarks = marks;
    }

    public Map<String, Option> getOptions() {

        return mOptions;
    }

    public void setOptions(Map<String, Option> options) {

        mOptions = options;
    }


    public Map<String, String> getFiles() {

        return mFiles;
    }

    public void setFiles(Map<String, String> files) {

        this.mFiles = files;
    }

    public String getExtra() {

        return mExtra;
    }

    public void setExtra(String extra) {

        this.mExtra = extra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return mMarks == question.mMarks &&
                Objects.equals(mDescription, question.mDescription) &&
                Objects.equals(mOptions, question.mOptions) ;
    }

    @Override
    public int hashCode() {

        return Objects.hash(mDescription, mMarks, mOptions);
    }

    /**
     * Clears all the isCorrect flag from options of this question.
     */
    public void resetOptions() {
        if (mOptions != null) {
            for (Map.Entry<String, Option> optionEntry : mOptions.entrySet()) {
                optionEntry.getValue().setIsCorrect(false);
            }
        }
    }
}
