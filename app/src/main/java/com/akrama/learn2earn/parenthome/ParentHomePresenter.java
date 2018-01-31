package com.akrama.learn2earn.parenthome;

import com.akrama.learn2earn.CompressedBet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by akrama on 31/01/18.
 */

public class ParentHomePresenter {

    private ParentHomeInteractor mInteractor;
    private ParentHomeView mView;

    public ParentHomePresenter(ParentHomeView view) {
        mView = view;
        mInteractor = new ParentHomeInteractor();
    }

    public void onCreate() {
        mInteractor.requestActiveBets(activeBets -> {
            hideAllViews();
            if (activeBets == null || activeBets.isEmpty()) {
                mView.showNoBetsView();
            } else {
                mView.showActiveBets(convertMapListToCompressedBetList(activeBets));
            }
        });
    }

    private void hideAllViews() {
        mView.hideProgressBar();
        mView.hideNoBetsView();
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
