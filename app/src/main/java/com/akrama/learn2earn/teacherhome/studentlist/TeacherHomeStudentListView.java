package com.akrama.learn2earn.teacherhome.studentlist;

import com.akrama.learn2earn.model.Student;

import java.util.List;

/**
 * Created by akrama on 02/02/18.
 */

public interface TeacherHomeStudentListView {
    void setTitle(String title);
    void showProgressBar();
    void hideProgressBar();
    void showFullscreenProgressBar();
    void hideFullscreenProgressBar();
    void showStudents(List<Student> studentList);
    void hideStudents();
    void showNoStudentsView();
    void hideNoStudentsView();
    void showSetGradeDialog(Integer index);
    void updateGrade(Integer index, String grade);
    void showSubmitGradesButton();
    void hideSubmitGradesButton();
    void finishActivity();
}
