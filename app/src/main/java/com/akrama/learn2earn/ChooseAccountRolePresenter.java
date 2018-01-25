package com.akrama.learn2earn;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by akrama on 24/01/18.
 */

public class ChooseAccountRolePresenter {

    private String mRoleSelected;
    private ChooseAccountRoleView mView;
    private ChooseAccountRoleInteractor mInteractor;

    public ChooseAccountRolePresenter(ChooseAccountRoleView view) {
        mView = view;
        mInteractor = new ChooseAccountRoleInteractor(this);
    }

    public void onRoleClicked(int index) {
        switch (index) {
            case 0:
                mRoleSelected = Constants.ROLE_STUDENT;
                break;
            case 1:
                mRoleSelected = Constants.ROLE_PARENT;
                break;
            case 2:
                mRoleSelected = Constants.ROLE_TEACHER;
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(mRoleSelected)) {
            mView.enableNextButton();
        }
    }

    public void onNextClicked() {
        mInteractor.setRole(mRoleSelected);
        switch (mRoleSelected) {
            case Constants.ROLE_STUDENT:
                mView.launchStudentHomeScreen();
                break;
            case Constants.ROLE_PARENT:
                mView.launchParentHomeScreen();
                break;
            case Constants.ROLE_TEACHER:
                mView.launchTeacherHomeScreen();
                break;
            default:
                break;
        }
    }

}
