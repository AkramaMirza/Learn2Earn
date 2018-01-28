package com.akrama.learn2earn;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentHomeActivity extends BaseActivity implements StudentHomeView {

    @BindView(R.id.no_parent_view) View mNoParentView;
    @BindView(R.id.no_bets_view) View mNoBetsView;
    @BindView(R.id.active_bets_progress_bar) View mActiveBetsProgessBar;
    @BindView(R.id.progress_bar) View mFullScreenProgressBar;
    @BindView(R.id.create_bet_btn) View mCreateBetButton;

    private StudentHomePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        ButterKnife.bind(this);
        mPresenter = new StudentHomePresenter(this);
        mPresenter.onCreate();
    }

    @OnClick(R.id.add_parent_btn)
    public void onAddParentClicked() {
        mPresenter.onAddParentClicked();
    }

    @OnClick({R.id.create_bet_btn, R.id.no_bets_create_bet_btn})
    public void onCreateBetClicked() {
        mPresenter.onCreateBetClicked();
    }

    @Override
    public void showAddParentDialog() {
        View addParentDialogContent = LayoutInflater.from(this).inflate(R.layout.add_parent_dialog, null);
        TextInputEditText emailEditText = addParentDialogContent.findViewById(R.id.add_parent_edit_text);
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.student_home_add_parent))
                .setMessage(getString(R.string.add_parent_description))
                .setPositiveButton(getString(R.string.add_parent_submit), (dialogInterface, i) ->
                        mPresenter.onAddParentSubmitted(emailEditText.getText().toString()))
                .setView(addParentDialogContent)
                .create()
                .show();
    }

    @Override
    public void showCreateBetDialog(List<Assignment> assignments) {
        View createBetDialog = LayoutInflater.from(this).inflate(R.layout.create_bet_dialog, null);
        Spinner assignmentSpinner = createBetDialog.findViewById(R.id.create_bet_assignment_spinner);
        TextInputEditText valueEditText = createBetDialog.findViewById(R.id.add_parent_edit_text);

        AssignmentAdapter adapter = new AssignmentAdapter(this);
        adapter.addAll(assignments);
        assignmentSpinner.setAdapter(adapter);


        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.home_create_bet))
                .setView(createBetDialog)
                .setPositiveButton(getString(R.string.create_bet_create), (dialogInterface, i) -> {
                    float value = Float.valueOf(valueEditText.getText().toString());
                    String assignmentUid = ((Assignment) assignmentSpinner.getSelectedItem()).getUid();
                    mPresenter.onCreateBetSubmitted(assignmentUid, value);
                })
                .create()
                .show();
    }

    @Override
    public void showAddClassroomDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.student_home_add_classroom))
                .create()
                .show();
    }

    @Override
    public void showNoParentView() {
        mNoParentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoParentView() {
        mNoParentView.setVisibility(View.GONE);
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
    public void showFullScreenProgressBar() {
        mFullScreenProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFullScreenProgressBar() {
        mFullScreenProgressBar.setVisibility(View.GONE);
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
    public void enableCreateBetButton() {
        mCreateBetButton.setEnabled(true);
    }

    @Override
    public void disableCreateBetButton() {
        mCreateBetButton.setEnabled(false);
    }
}
