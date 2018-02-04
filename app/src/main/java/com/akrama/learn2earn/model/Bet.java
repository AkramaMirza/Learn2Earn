package com.akrama.learn2earn.model;

import com.akrama.learn2earn.Constants;
import com.google.firebase.firestore.PropertyName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akrama on 25/01/18.
 */

public class Bet {

    private String studentAddress;
    private String parentAddress;
    private String teacherAddress;
    private String assignmentName;
    private String assignmentUid;
    private String betValue;
    private Long betGrade;

    public Bet(String studentAddress, String parentAddress, String teacherAddress, String assignmentName,
               String assignmentUid, String betValue, Long betGrade) {
        this.studentAddress = studentAddress;
        this.parentAddress = parentAddress;
        this.teacherAddress = teacherAddress;
        this.assignmentName = assignmentName;
        this.assignmentUid = assignmentUid;
        this.betValue = betValue;
        this.betGrade = betGrade;
    }

    @PropertyName(Constants.FIELD_STUDENT_ADDRESS)
    public String getStudentAddress() {
        return studentAddress;
    }

    @PropertyName(Constants.FIELD_PARENT_ADDRESS)
    public String getParentAddress() {
        return parentAddress;
    }

    @PropertyName(Constants.FIELD_TEACHER_ADDRESS)
    public String getTeacherAddress() {
        return teacherAddress;
    }

    @PropertyName(Constants.FIELD_ASSIGNMENT_NAME)
    public String getAssignmentName() {
        return assignmentName;
    }

    @PropertyName(Constants.FIELD_ASSIGNMENT_UID)
    public String getAssignmentUid() {
        return assignmentUid;
    }

    @PropertyName(Constants.FIELD_BET_VALUE)
    public String getBetValue() {
        return betValue;
    }

    @PropertyName(Constants.FIELD_BET_GRADE)
    public Long getBetGrade() {
        return betGrade;
    }
}
