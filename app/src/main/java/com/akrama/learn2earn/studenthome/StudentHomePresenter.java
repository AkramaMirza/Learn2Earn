package com.akrama.learn2earn.studenthome;

import android.content.Context;

import com.akrama.learn2earn.Constants;
import com.akrama.learn2earn.EthereumInteractor;
import com.akrama.learn2earn.model.Assignment;
import com.akrama.learn2earn.model.CompressedBet;

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

    public void onCreate(Context context) {
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
                mView.showActiveBets(convertMapListToCompressedBetList(activeBets));
            }
        });

        mView.showFullScreenProgressBar();
        EthereumInteractor.getInstance().init(context, Constants.ROLE_STUDENT, success -> {
            if (success) {
                mView.hideFullScreenProgressBar();
            } else {
                // TODO: inform the user that we can't connect to ethereum network
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
                mInteractor.requestTeacherStatus(hasClassroom -> {
                    mView.hideFullScreenProgressBar();
                    if (hasClassroom) {
                        // TODO: inform the user that there are no assignments to bet on
                    } else {
                        mView.showAddTeacherDialog();
                    }
                    mView.hideFullScreenProgressBar();
                });
            } else {
                mView.hideFullScreenProgressBar();
                mView.showCreateBetDialog(Assignment.fromMapListToAssignmentList(assignments));
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

    public void onAddTeacherSubmitted(String email) {
        mView.showFullScreenProgressBar();
        mInteractor.addTeacherWithEmail(email, success -> {
            if (success) {
                mView.hideFullScreenProgressBar();
                // TODO: fetch any assignments that the teacher has started and add to current users users_to_assignments table
                onCreateBetClicked();
            } else {
                // TODO: inform the user that the teacher was not added
            }
        });
    }

    public void onCreateBetSubmitted(Assignment assignment, String value, Long grade) {
        mView.showFullScreenProgressBar();
        mInteractor.createBet(assignment, value, grade, success -> mView.hideFullScreenProgressBar());
    }

    private void hideAllViewsExceptProgressBar() {
        mView.showProgressBar();
        mView.hideNoBetsView();
        mView.hideNoParentView();
        mView.hideActiveBets();
    }

    private List<CompressedBet> convertMapListToCompressedBetList(List<Map> mapList) {
        List<CompressedBet> compressedBets = new ArrayList<>();
        for (Map map : mapList) {
            compressedBets.add(CompressedBet.fromMap(map));
        }
        return compressedBets;
    }
}
