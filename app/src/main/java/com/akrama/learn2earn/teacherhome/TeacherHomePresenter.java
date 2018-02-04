package com.akrama.learn2earn.teacherhome;

import android.content.Context;

import com.akrama.learn2earn.Constants;
import com.akrama.learn2earn.ethereum.EthereumInteractor;
import com.akrama.learn2earn.model.Assignment;

import org.web3j.utils.Convert;

import java.math.BigDecimal;

/**
 * Created by akrama on 31/01/18.
 */

public class TeacherHomePresenter {

    private TeacherHomeInteractor mInteractor;
    private EthereumInteractor mEthereumInteractor;
    private TeacherHomeView mView;

    public TeacherHomePresenter(TeacherHomeView view) {
        mView = view;
        mInteractor = new TeacherHomeInteractor();
        mEthereumInteractor = EthereumInteractor.getInstance();
    }

    public void onCreate(Context context) {
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

        mView.showFullScreenProgressBar();
        mEthereumInteractor.init(context, Constants.ROLE_TEACHER, success -> {
            if (success) {
                mView.hideFullScreenProgressBar();
            } else {
                // TODO: inform the user that we can't connect to ethereum network
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
