package com.akrama.learn2earn.parenthome;

import com.akrama.learn2earn.model.CompressedBet;

import java.util.List;

/**
 * Created by akrama on 31/01/18.
 */

public interface ParentHomeView {
    void showProgressBar();
    void hideProgressBar();
    void showNoBetsView();
    void hideNoBetsView();
    void showActiveBets(List<CompressedBet> bets);
    void hideActiveBets();
    void showCurrentBalance(String balance);
    void showFullScreenProgressBar();
    void hideFullScreenProgressBar();
    void showBetBeingConfirmedToast();
}
