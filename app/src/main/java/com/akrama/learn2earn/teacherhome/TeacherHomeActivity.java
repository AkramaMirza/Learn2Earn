package com.akrama.learn2earn.teacherhome;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.akrama.learn2earn.Constants;
import com.akrama.learn2earn.model.Assignment;
import com.akrama.learn2earn.BaseActivity;
import com.akrama.learn2earn.R;
import com.akrama.learn2earn.teacherhome.studentlist.TeacherHomeStudentListActivity;
import com.akrama.learn2earn.teacherhome.studentlist.TeacherHomeStudentListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeacherHomeActivity extends BaseActivity implements TeacherHomeView {

    @BindView(R.id.assignments_list_view) RecyclerView mAssignmentsRecyclerView;
    @BindView(R.id.no_assignments_view) View mNoAssignmentsView;
    @BindView(R.id.assignments_progress_bar) View mAssignmentsProgressBar;
    @BindView(R.id.progress_bar) View mFullScreenProgressBar;
    @BindView(R.id.create_assignment_btn) View mCreateAssignmentButton;

    private TeacherHomePresenter mPresenter;
    private TeacherHomeAssignmentAdapter mAssignmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        ButterKnife.bind(this);

        mAssignmentAdapter = new TeacherHomeAssignmentAdapter(this);
        mAssignmentAdapter.setOnClickListener(this::onAssignmentClicked);
        mAssignmentsRecyclerView.setAdapter(mAssignmentAdapter);
        mAssignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAssignmentsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mPresenter = new TeacherHomePresenter(this);
        mPresenter.onCreate();
    }

    @OnClick({R.id.create_assignment_btn, R.id.no_assignments_create_assignment_btn})
    public void onCreateAssignmentClicked() {
        mPresenter.onCreateAssignmentClicked();
    }

    @Override
    public void showCreateAssignmentDialog() {
        View createAssignmentDialogContent = LayoutInflater.from(this).inflate(R.layout.create_assignment_dialog, null);
        TextInputEditText assignmentNameEditText = createAssignmentDialogContent.findViewById(R.id.create_assignment_edit_text);

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.home_create_assignment))
                .setPositiveButton(getString(R.string.create_assignment_create), (dialogInterface, i) ->
                        mPresenter.onCreateAssignmentSubmitted(assignmentNameEditText.getText().toString()))
                .setView(createAssignmentDialogContent)
                .create()
                .show();
    }

    private void onAssignmentClicked(Assignment assignment) {
        mPresenter.onAssignmentClicked(assignment);
    }

    @Override
    public void launchStudentListScreen(Assignment assignment) {
        Intent intent = new Intent(this, TeacherHomeStudentListActivity.class);
        intent.putExtra(Constants.EXTRA_ASSIGNMENT_NAME, assignment.getName());
        intent.putExtra(Constants.EXTRA_ASSIGNMENT_UID, assignment.getUid());
        startActivity(intent);
    }

    @Override
    public void showProgressBar() {
        mAssignmentsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mAssignmentsProgressBar.setVisibility(View.GONE);
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
    public void showNoAssignmentsView() {
        mNoAssignmentsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoAssignmentsView() {
        mNoAssignmentsView.setVisibility(View.GONE);
    }

    @Override
    public void showAssignments(List<Assignment> assignments) {
        mAssignmentAdapter.setAssignments(assignments);
        mAssignmentsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAssignments() {
        mAssignmentsRecyclerView.setVisibility(View.GONE);
    }
}
