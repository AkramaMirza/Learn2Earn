package com.akrama.learn2earn.teacherhome.studentlist;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.akrama.learn2earn.BaseActivity;
import com.akrama.learn2earn.Constants;
import com.akrama.learn2earn.R;
import com.akrama.learn2earn.model.Student;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeacherHomeStudentListActivity extends BaseActivity implements TeacherHomeStudentListView {

    @BindView(R.id.students_recycler_view) RecyclerView mStudentRecyclerView;
    @BindView(R.id.student_list_toolbar) Toolbar mToolbar;
    @BindView(R.id.no_students) View mNoStudentsView;
    @BindView(R.id.progress_bar) View mProgressBar;
    @BindView(R.id.full_screen_progress_bar) View mFullscreenProgressBar;
    @BindView(R.id.submit_grades_btn) Button mSubmitGradesButton;

    private TeacherHomeStudentListPresenter mPresenter;
    private TeacherHomeStudentListAdapter mStudentAdapter;

    private String mAssignmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home_student_list);
        ButterKnife.bind(this);

        mStudentAdapter = new TeacherHomeStudentListAdapter(this);
        mStudentAdapter.setOnItemClickListener(this::onStudentClicked);
        mStudentRecyclerView.setAdapter(mStudentAdapter);
        mStudentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mStudentRecyclerView.addItemDecoration(itemDecoration);

        mAssignmentName = getIntent().getStringExtra(Constants.EXTRA_ASSIGNMENT_NAME);
        String assignmentUid = getIntent().getStringExtra(Constants.EXTRA_ASSIGNMENT_UID);

        mPresenter = new TeacherHomeStudentListPresenter(this);
        mPresenter.onCreate(mAssignmentName, assignmentUid);
    }

    private void onStudentClicked(Integer index) {
        mPresenter.onStudentClicked(index);
    }

    @OnClick(R.id.submit_grades_btn)
    public void onSubmitGradesClicked() {
        mPresenter.onSubmitGradesClicked(mStudentAdapter.getStudents(), mStudentAdapter.getGrades());
    }

    @Override
    public void showSetGradeDialog(Integer index) {
        View setGradeDialogContent = LayoutInflater.from(this).inflate(R.layout.teacher_set_grade_dialog, null);
        TextInputEditText gradeEditText = setGradeDialogContent.findViewById(R.id.set_grade_edit_text);

        new AlertDialog.Builder(this)
                .setTitle(mStudentAdapter.getStudentName(index))
                .setMessage(String.format(getString(R.string.teacher_home_set_grade_description), mAssignmentName))
                .setView(setGradeDialogContent)
                .setPositiveButton(getString(R.string.teacher_home_set_grade), ((dialog, which) -> {
                    mPresenter.onSetGradeClicked(index, Integer.parseInt(gradeEditText.getText().toString()));
                }))
                .create()
                .show();
    }

    @Override
    public void updateGrade(Integer index, Integer grade) {
        mStudentAdapter.setGrade(index, grade);
    }

    @Override
    public void showSubmitGradesButton() {
        mSubmitGradesButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSubmitGradesButton() {
        mSubmitGradesButton.setVisibility(View.GONE);
    }

    @Override
    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showFullscreenProgressBar() {
        mFullscreenProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFullscreenProgressBar() {
        mFullscreenProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showStudents(List<Student> studentList) {
        mStudentAdapter.setStudents(studentList);
        mStudentRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideStudents() {
        mStudentRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showNoStudentsView() {
        mNoStudentsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoStudentsView() {
        mNoStudentsView.setVisibility(View.GONE);
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
