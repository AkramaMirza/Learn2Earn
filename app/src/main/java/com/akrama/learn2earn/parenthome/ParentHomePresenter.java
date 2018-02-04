package com.akrama.learn2earn.parenthome;

import android.content.Context;

import com.akrama.learn2earn.Constants;
import com.akrama.learn2earn.ethereum.EthereumInteractor;
import com.akrama.learn2earn.model.CompressedBet;

import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

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
                mView.showActiveBets(CompressedBet.fromMapListToCompressedBetList(activeBets));
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

    public void onConfirmBetClicked(CompressedBet bet) {
        mView.showFullScreenProgressBar();
        mView.showBetBeingConfirmedToast();
        bet.setConfirmed(true);
        mInteractor.updateBet(bet, success -> {});
        BigInteger valueWei = Convert.toWei(bet.getBetValue(), Convert.Unit.ETHER).toBigInteger();
        mEthereumInteractor.confirmBet(bet.getBetUid(), valueWei, success -> mView.hideFullScreenProgressBar());
    }
}
