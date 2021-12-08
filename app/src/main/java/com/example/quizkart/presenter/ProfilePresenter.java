package com.example.quizkart.presenter;

import android.os.Bundle;

import com.example.quizkart.UserProfileActivity;
import com.example.quizkart.models.Question;
import com.example.quizkart.models.Quiz;

import java.util.List;

public class ProfilePresenter {

    UserProfileActivity mView;





    /*@Override
    public void saveProfile(Bitmap picture, String slackHandle, String courseTrack) {

        // Upload the image in firebase storage
        // This is yet to be implemented. THROWS RUNTIME EXCEPTION.
        mDataHandler.uploadProfilePic(picture, new DataHandler.Callback<String>() {
            @Override
            public void onResponse(String result) {
                // This result is the public URL of the bitmap we just uploaded
                if (result != null && !result.isEmpty()) {
                    mDataHandler.saveUserPic(result);
                }
                mDataHandler.saveSlackHandle(slackHandle);
                mDataHandler.saveUserTrack(courseTrack);

                mDataHandler.setUserInfo(new DataHandler.Callback<Void>() {
                    @Override
                    public void onResponse(Void result) {
                        mView.onProfileSaved();
                    }

                    @Override
                    public void onError() {
                        mView.onSaveError();
                    }
                });
            }

            @Override
            public void onError() {
                mView.onPictureUploadError();
            }
        });
    }*/

    /*
    public void saveProfile(String pictureUrl, String username, String slackHandle, String courseTrack) {

        if (pictureUrl != null && !pictureUrl.isEmpty()) {
            mDataHandler.saveUserPic(pictureUrl);
        }

        if (!slackHandle.startsWith("@")) {
            slackHandle = "@" + slackHandle;
        }

        mDataHandler.saveSlackHandle(slackHandle);
        mDataHandler.saveUserTrack(courseTrack);
        mDataHandler.saveUserName(username);

        mDataHandler.setUserInfo(new DataHandler.Callback<Void>() {
            @Override
            public void onResponse(Void result) {
                mView.onProfileSaved();
            }

            @Override
            public void onError() {
                mView.onSaveError();
            }
        });
    }*/


    /*public void start(@Nullable Bundle extras) {

        if (mDataHandler.isLoggedIn()) {
            // If user is logged in directly navigate to home
            mView.onProfileSaved();
            return;
        }

        // Updating UI with data we have
        mView.loadEmailAddress(mDataHandler.getUserEmail());
        mView.loadSlackHandle(mDataHandler.getSlackHandle());
        mView.loadUserName(mDataHandler.getUserName());
        mView.loadUserPic(mDataHandler.getUserPic());
        mView.loadUserTrack(mDataHandler.getUserTrack());

    }*/

    public void destroy() {

        this.mView = null;
    }
}
