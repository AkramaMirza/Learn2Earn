package com.akrama.learn2earn;

import java.util.function.Consumer;

/**
 * Created by akrama on 25/01/18.
 */

public class StudentHomePresenter {

    private StudentHomeView mView;
    private StudentHomeInteractor mInteractor;

    public StudentHomePresenter(StudentHomeView view) {
        mView = view;
        mInteractor = new StudentHomeInteractor();
    }

    public void onCreate() {
        mInteractor.requestActiveBets(activeBets -> {
            if (activeBets == null || activeBets.isEmpty()) {
                // No active bets were found, check if the user has connected with a parent
                mInteractor.requestParentStatus(hasParent -> {
                    mView.hideProgressBar();
                    if (hasParent) {
                        // The student has connected with a parent but has not created any bets yet
                        mView.showNoBetsView();
                        mView.enableCreateBetButton();
                    } else {
                        // The student has to connect with a parent
                        mView.showNoParentView();
                    }
                });
            } else {
                mView.hideProgressBar();
                mView.enableCreateBetButton();
                // Show the active bets
            }
        });
    }

    public void onAddParentClicked() {
        mView.showAddParentDialog();
    }

    public void onCreateBetClicked() {
        mView.showFullScreenProgressBar();
        mInteractor.requestClassroomStatus(hasClassroom -> {
            if (hasClassroom) {
                mView.showCreateBetDialog();
            } else {
                mView.showAddClassroomDialog();
            }
            mView.hideFullScreenProgressBar();
        });
    }

    public void onAddParentSubmitted(String email){
        mView.hideNoParentView();
        mView.showProgressBar();
        mInteractor.addParentWithEmail(email, success -> {
            if (success) {
                mView.hideProgressBar();
                mView.showNoBetsView(); // Assumption: If a user just added a parent, they must have zero active bets
                mView.enableCreateBetButton();
            } else {
                // TODO: inform the user that the parent was not added
            }
        });
    }
}
