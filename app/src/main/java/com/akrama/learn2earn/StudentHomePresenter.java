package com.akrama.learn2earn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            hideAllViewsExceptProgressBar();
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
                        mView.disableCreateBetButton();
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
        mInteractor.requestAssignments(assignments -> {
            if (assignments == null || assignments.isEmpty()) {
                // User has no assignments, check if they have joined a classroom
                mInteractor.requestClassroomStatus(hasClassroom -> {
                    mView.hideFullScreenProgressBar();
                    if (hasClassroom) {
                        // TODO: inform the user that there are no assignments to bet on
                    } else {
                        // TODO: mView.showAddClassroomDialog();
                    }
                    mView.hideFullScreenProgressBar();
                });
            } else {
                mView.hideFullScreenProgressBar();
                mView.showCreateBetDialog(convertMapListToAssignmentList(assignments));
            }
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

    public void onCreateBetSubmitted(String assignmentUid, float value) {
        mView.showFullScreenProgressBar();
        mInteractor.createBet(assignmentUid, value, success -> mView.hideFullScreenProgressBar());
    }

    private void hideAllViewsExceptProgressBar() {
        mView.showProgressBar();
        mView.hideNoBetsView();
        mView.hideNoParentView();
        // TODO: mView.hideBetsList()
    }

    private List<Assignment> convertMapListToAssignmentList(List<Map> mapList) {
        List<Assignment> assignments = new ArrayList<>();
        for (Map map : mapList) {
            String name = (String) map.get(Constants.FIELD_ASSIGNMENT_NAME);
            String uid = (String) map.get(Constants.FIELD_ASSIGNMENT_UID);
            assignments.add(new Assignment(name, uid));
        }
        return assignments;
    }
}
