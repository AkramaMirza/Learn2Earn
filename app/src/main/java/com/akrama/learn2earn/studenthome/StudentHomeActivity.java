package com.akrama.learn2earn.studenthome;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akrama.learn2earn.model.Assignment;
import com.akrama.learn2earn.BaseActivity;
import com.akrama.learn2earn.model.CompressedBet;
import com.akrama.learn2earn.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentHomeActivity extends BaseActivity implements StudentHomeView {

    @BindView(R.id.active_bets_list_view) RecyclerView mActiveBetsRecyclerView;
    @BindView(R.id.no_parent_view) View mNoParentView;
    @BindView(R.id.no_bets_view) View mNoBetsView;
    @BindView(R.id.active_bets_progress_bar) View mActiveBetsProgressBar;
    @BindView(R.id.progress_bar) View mFullScreenProgressBar;
    @BindView(R.id.create_bet_btn) View mCreateBetButton;
    @BindView(R.id.current_balance_text_view) TextView mCurrentBalanceTextView;

    private StudentHomePresenter mPresenter;
    private StudentHomeActiveBetAdapter mActiveBetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        ButterKnife.bind(this);
        mActiveBetAdapter = new StudentHomeActiveBetAdapter(this);
        mActiveBetsRecyclerView.setAdapter(mActiveBetAdapter);
        mActiveBetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mActiveBetsRecyclerView.addItemDecoration(decoration);
        mPresenter = new StudentHomePresenter(this);
        mPresenter.onCreate(getApplicationContext());
    }

    @OnClick(R.id.add_parent_btn)
    public void onAddParentClicked() {
        mPresenter.onAddParentClicked();
    }

    @OnClick({R.id.create_bet_btn, R.id.no_bets_create_bet_btn})
    public void onCreateBetClicked() {
        mPresenter.onCreateBetClicked();
    }

    @OnClick(R.id.update_balance_btn)
    public void onUpdateBalanceClicked() {
        mPresenter.onUpdateBalanceClicked();
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
        TextInputEditText valueEditText = createBetDialog.findViewById(R.id.bet_value_edit_text);
        TextInputEditText gradeEditText = createBetDialog.findViewById(R.id.bet_grade_edit_text);

        CreateBetAssignmentAdapter adapter = new CreateBetAssignmentAdapter(this);
        adapter.addAll(assignments);
        assignmentSpinner.setAdapter(adapter);


        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.home_create_bet))
                .setView(createBetDialog)
                .setPositiveButton(getString(R.string.create_bet_create), (dialogInterface, i) -> {
                    Long grade = Long.parseLong(gradeEditText.getText().toString());
                    String value = valueEditText.getText().toString();
                    Assignment assignment = (Assignment) assignmentSpinner.getSelectedItem();
                    mPresenter.onCreateBetSubmitted(assignment, value, grade);
                })
                .create()
                .show();
    }

    @Override
    public void showAddTeacherDialog() {
        View addTeacherDialogContent = LayoutInflater.from(this).inflate(R.layout.add_teacher_dialog, null);
        TextInputEditText emailEditText = addTeacherDialogContent.findViewById(R.id.add_teacher_edit_text);

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.student_home_add_teacher))
                .setMessage(getString(R.string.add_teacher_description))
                .setPositiveButton(getString(R.string.add_teacher_submit), (dialogInterface, i) ->
                        mPresenter.onAddTeacherSubmitted(emailEditText.getText().toString()))
                .setView(addTeacherDialogContent)
                .create()
                .show();
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
    public void showNoParentView() {
        mNoParentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoParentView() {
        mNoParentView.setVisibility(View.GONE);
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
    public void showFullScreenProgressBar() {
        mFullScreenProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFullScreenProgressBar() {
        runOnUiThread(() -> mFullScreenProgressBar.setVisibility(View.GONE));
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

    @Override
    public void showCurrentBalance(String balance) {
        runOnUiThread(() -> mCurrentBalanceTextView.setText(String.format(getString(R.string.home_current_balance), balance)));
    }

    @Override
    public void showNoAssignmentsToast() {
        Toast.makeText(this, getString(R.string.student_home_no_assignments_toast), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showBetIsBeingCreatedToast() {
        Toast.makeText(this, getString(R.string.student_home_bet_being_created), Toast.LENGTH_LONG).show();
    }
}
