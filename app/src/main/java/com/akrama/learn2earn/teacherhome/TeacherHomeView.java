package com.akrama.learn2earn.teacherhome;

import com.akrama.learn2earn.model.Assignment;

import java.util.List;

/**
 * Created by akrama on 31/01/18.
 */

public interface TeacherHomeView {
    void showCreateAssignmentDialog();
    void showProgressBar();
    void hideProgressBar();
    void showFullScreenProgressBar();
    void hideFullScreenProgressBar();
    void showNoAssignmentsView();
    void hideNoAssignmentsView();
    void showAssignments(List<Assignment> assignments);
    void hideAssignments();
}
