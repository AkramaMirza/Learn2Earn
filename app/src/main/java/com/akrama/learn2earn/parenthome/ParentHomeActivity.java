package com.akrama.learn2earn.parenthome;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.akrama.learn2earn.BaseActivity;
import com.akrama.learn2earn.R;
import com.akrama.learn2earn.model.CompressedBet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ParentHomeActivity extends BaseActivity implements ParentHomeView {

    @BindView(R.id.active_bets_list_view) RecyclerView mActiveBetsRecyclerView;
    @BindView(R.id.no_bets_view) View mNoBetsView;
    @BindView(R.id.active_bets_progress_bar) View mActiveBetsProgressBar;
    @BindView(R.id.current_balance_text_view) TextView mCurrentBalanceTextView;
    @BindView(R.id.progress_bar) View mFullScreenProgressBar;

    private ParentHomePresenter mPresenter;
    private ParentHomeActiveBetAdapter mActiveBetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);
        ButterKnife.bind(this);
        mActiveBetAdapter = new ParentHomeActiveBetAdapter(this);
        mActiveBetAdapter.setOnConfirmClickListener(this::onConfirmBetClicked);
        mActiveBetsRecyclerView.setAdapter(mActiveBetAdapter);
        mActiveBetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mActiveBetsRecyclerView.addItemDecoration(decoration);
        mPresenter = new ParentHomePresenter(this);
        mPresenter.onCreate(getApplicationContext());
    }

    @OnClick(R.id.update_balance_btn)
    public void onUpdateBalanceClicked() {
        mPresenter.onUpdateBalanceClicked();
    }

    public void onConfirmBetClicked(CompressedBet bet) {
        mPresenter.onConfirmBetClicked(bet);
    }

    @Override
    public void showActiveBets(List<CompressedBet> bets) {
        mActiveBetAdapter.setBets(bets);
        mActiveBetsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideActiveBets() {
        mActiveBetsRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        mActiveBetsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mActiveBetsProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoBetsView() {
        mNoBetsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoBetsView() {
        mNoBetsView.setVisibility(View.GONE);
    }

    @Override
    public void showFullScreenProgressBar() {
        mFullScreenProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFullScreenProgressBar() {
        runOnUiThread(() -> mFullScreenProgressBar.setVisibility(View.GONE));
    }

    @Override
    public void showCurrentBalance(String balance) {
        runOnUiThread(() -> mCurrentBalanceTextView.setText(String.format(getString(R.string.home_current_balance), balance)));
    }
}
