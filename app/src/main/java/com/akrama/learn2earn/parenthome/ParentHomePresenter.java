package com.akrama.learn2earn.parenthome;

import android.content.Context;

import com.akrama.learn2earn.Constants;
import com.akrama.learn2earn.EthereumInteractor;
import com.akrama.learn2earn.model.CompressedBet;

import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by akrama on 31/01/18.
 */

public class ParentHomePresenter {

    private ParentHomeInteractor mInteractor;
    private EthereumInteractor mEthereumInteractor;
    private ParentHomeView mView;

    public ParentHomePresenter(ParentHomeView view) {
        mView = view;
        mInteractor = new ParentHomeInteractor();
        mEthereumInteractor = EthereumInteractor.getInstance();
    }

    public void onCreate(Context context) {
        mInteractor.requestActiveBets(activeBets -> {
            hideAllViews();
            if (activeBets == null || activeBets.isEmpty()) {
                mView.showNoBetsView();
            } else {
                mView.showActiveBets(convertMapListToCompressedBetList(activeBets));
            }
        });

        mView.showFullScreenProgressBar();
        mEthereumInteractor.init(context, Constants.ROLE_PARENT, success -> {
            if (success) {
                mEthereumInteractor.requestCurrentBalance(balance -> {
                    mView.hideFullScreenProgressBar();
                    if (balance != null) {
                        BigDecimal balanceEth = Convert.fromWei(balance.toString(), Convert.Unit.ETHER);
                        mView.showCurrentBalance(balanceEth.toPlainString());
                    } else {
                        // TODO: inform the user that the current balance could not be retrieved
                    }
                });
            } else {
                // TODO: inform the user that we can't connect to ethereum network
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

    public void onUpdateBalanceClicked() {
        mEthereumInteractor.requestCurrentBalance(balance -> {
            if (balance != null) {
                BigDecimal balanceEth = Convert.fromWei(balance.toString(), Convert.Unit.ETHER);
                mView.showCurrentBalance(balanceEth.toPlainString());
            } else {
                // TODO: inform the user that the current balance could not be retrieved
            }
        });
    }
}
