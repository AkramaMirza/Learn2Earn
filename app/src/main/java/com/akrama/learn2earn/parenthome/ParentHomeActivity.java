package com.akrama.learn2earn.parenthome;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.akrama.learn2earn.ActiveBetAdapter;
import com.akrama.learn2earn.BaseActivity;
import com.akrama.learn2earn.CompressedBet;
import com.akrama.learn2earn.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParentHomeActivity extends BaseActivity implements ParentHomeView {

    @BindView(R.id.active_bets_list_view) RecyclerView mActiveBetsRecyclerView;
    @BindView(R.id.no_bets_view) View mNoBetsView;
    @BindView(R.id.active_bets_progress_bar) View mActiveBetsProgessBar;

    private ParentHomePresenter mPresenter;
    private ActiveBetAdapter mActiveBetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);
        ButterKnife.bind(this);
        mActiveBetAdapter = new ActiveBetAdapter(this);
        mActiveBetsRecyclerView.setAdapter(mActiveBetAdapter);
        mActiveBetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mActiveBetsRecyclerView.addItemDecoration(decoration);
        mPresenter = new ParentHomePresenter(this);
        mPresenter.onCreate();
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
        mActiveBetsProgessBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mActiveBetsProgessBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoBetsView() {
        mNoBetsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoBetsView() {
        mNoBetsView.setVisibility(View.GONE);
    }
}
