package com.example.quizkart.presenter;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.quizkart.TestDetailsActivity;

public class TestDetailsPresenter {

    TestDetailsActivity mView;
    private String mCurrentQuizId;




    public void startQuizClicked() {

        mView.startQuiz(mCurrentQuizId);
    }


    public void start(@Nullable Bundle extras) {
        if (extras == null || !extras.containsKey(mView.KEY_QUIZ_ID)) {
            mView.showInvalidInput();
            return;
        }

        String quizId = extras.getString(mView.KEY_QUIZ_ID);
        this.mCurrentQuizId = quizId;

        mView.showLoading();
        /*mDataHandler.fetchQuizById(quizId, new DataHandler.Callback<Quiz>() {
            @Override
            public void onResponse(Quiz result) {

                mView.showAuthor(result.getCreatorName());
                mView.showDeadline(result.getDeadline());
                mView.showDescription(result.getDescription());
                mView.showReleaseDate(result.getLastModified());
                mView.showTitle(result.getTitle());

                if (result.isAttempted()) {
                    // User score is stored in rated-by field
                    mView.showUserScore(String.valueOf(result.getRatedBy()),
                            String.valueOf(result.getMaxMarks()));
                } else {
                    mView.enableStartButton();
                }
                mView.hideLoading();
            }

            @Override
            public void onError() {
                mView.onError();
                mView.hideLoading();
            }
        });*/
    }

    public void destroy() {
        this.mView = null;
        //this.mDataHandler = null;
    }
}
