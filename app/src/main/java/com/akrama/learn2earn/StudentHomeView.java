package com.akrama.learn2earn;

/**
 * Created by akrama on 25/01/18.
 */

public interface StudentHomeView {
    void showAddParentDialog();
    void showCreateBetDialog();
    void showAddClassroomDialog();
    void showNoParentView();
    void hideNoParentView();
    void showProgressBar();
    void hideProgressBar();
    void showFullScreenProgressBar();
    void hideFullScreenProgressBar();
    void showNoBetsView();
    void hideNoBetsView();
    void enableCreateBetButton();
}
