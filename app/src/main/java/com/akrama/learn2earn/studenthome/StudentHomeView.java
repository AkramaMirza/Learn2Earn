package com.akrama.learn2earn.studenthome;

import com.akrama.learn2earn.model.Assignment;
import com.akrama.learn2earn.model.CompressedBet;

import java.util.List;

/**
 * Created by akrama on 25/01/18.
 */

public interface StudentHomeView {
    void showAddParentDialog();
    void showCreateBetDialog(List<Assignment> assignments);
    void showAddTeacherDialog();
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
