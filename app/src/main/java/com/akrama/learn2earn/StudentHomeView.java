package com.akrama.learn2earn;

import java.util.List;

/**
 * Created by akrama on 25/01/18.
 */

public interface StudentHomeView {
    void showAddParentDialog();
    void showCreateBetDialog(List<Assignment> assignments);
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
    void disableCreateBetButton();
    void showActiveBets(List<CompressedBet> bets);
    void hideActiveBets();
}
