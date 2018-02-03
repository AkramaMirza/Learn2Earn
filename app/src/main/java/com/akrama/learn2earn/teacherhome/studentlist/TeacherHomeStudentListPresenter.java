package com.akrama.learn2earn.teacherhome.studentlist;

import com.akrama.learn2earn.model.Student;

import java.util.List;

/**
 * Created by akrama on 02/02/18.
 */

public class TeacherHomeStudentListPresenter {

    private TeacherHomeStudentListInteractor mInteractor;
    private TeacherHomeStudentListView mView;
    private String mAssignmentUid;

    public TeacherHomeStudentListPresenter(TeacherHomeStudentListView view) {
        mView = view;
        mInteractor = new TeacherHomeStudentListInteractor();
    }

    // TODO: use assignmentUid to only show students that have a bet depending on this assignment
    public void onCreate(String assignmentName, String assignmentUid) {
        mView.setTitle(assignmentName);
        mInteractor.requestStudents(mapList -> {
            mView.hideProgressBar();
            if (mapList == null || mapList.isEmpty()) {
                mView.showNoStudentsView();
            } else {
                List<Student> studentList = Student.fromMapListToStudentList(mapList);
                mView.showStudents(studentList);
                mView.showSubmitGradesButton();
            }
        });
        mAssignmentUid = assignmentUid;
    }

    public void onStudentClicked(Integer index) {
        mView.showSetGradeDialog(index);
    }

    public void onSetGradeClicked(Integer index, Integer grade) {
        mView.updateGrade(index, grade);
    }

    public void onSubmitGradesClicked(List<Student> students, Integer[] grades) {
        // TODO: send to ETHEREUM smart contract
        mView.showFullscreenProgressBar();
        mInteractor.deleteAssignment(mAssignmentUid, students, success -> {
            mView.finishActivity();
        });
    }
}
