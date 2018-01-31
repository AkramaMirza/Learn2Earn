package com.akrama.learn2earn.parenthome;

import com.akrama.learn2earn.Assignment;
import com.akrama.learn2earn.CompressedBet;

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
}
