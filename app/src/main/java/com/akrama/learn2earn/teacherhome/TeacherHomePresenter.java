package com.akrama.learn2earn.teacherhome;

import com.akrama.learn2earn.model.Assignment;

/**
 * Created by akrama on 31/01/18.
 */

public class TeacherHomePresenter {

    private TeacherHomeInteractor mInteractor;
    private TeacherHomeView mView;

    public TeacherHomePresenter(TeacherHomeView view) {
        mView = view;
        mInteractor = new TeacherHomeInteractor();
    }

    public void onCreate() {
        mInteractor.requestAssignments(assignments -> {
            mView.hideProgressBar();
            if (assignments == null || assignments.isEmpty()) {
                mView.hideAssignments();
                mView.showNoAssignmentsView();
            } else {
                mView.hideNoAssignmentsView();
                mView.showAssignments(Assignment.fromMapListToAssignmentList(assignments));
            }
        });
    }

    public void onCreateAssignmentClicked() {
        mView.showCreateAssignmentDialog();
    }

    public void onCreateAssignmentSubmitted(String assignmentName) {
        mView.showFullScreenProgressBar();
        mInteractor.createAssignment(assignmentName, success -> {
            if (success) {
                mView.hideFullScreenProgressBar();
            } else {
                // TODO: inform the user that the assignment was not created
            }
        });
    }

    public void onAssignmentClicked(Assignment assignment) {
        mView.launchStudentListScreen(assignment);
    }
}
