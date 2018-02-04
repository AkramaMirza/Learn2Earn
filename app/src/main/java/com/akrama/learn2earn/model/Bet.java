package com.akrama.learn2earn.model;

import com.akrama.learn2earn.Constants;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by akrama on 25/01/18.
 */

public class Bet {

    private String studentAddress;
    private String parentAddress;
    private String teacherAddress;
    private String studentUid;
    private String parentUid;
    private String teacherUid;
    private String assignmentName;
    private String assignmentUid;
    private String betValue;
    private Long betGrade;
    private boolean confirmed;

    public Bet(String studentAddress, String parentAddress, String teacherAddress, String studentUid,
               String parentUid, String teacherUid, String assignmentName ,String assignmentUid,
               String betValue, Long betGrade, boolean confirmed) {
        this.studentAddress = studentAddress;
        this.parentAddress = parentAddress;
        this.teacherAddress = teacherAddress;
        this.studentUid = studentUid;
        this.parentUid = parentUid;
        this.teacherUid = teacherUid;
        this.assignmentName = assignmentName;
        this.assignmentUid = assignmentUid;
        this.betValue = betValue;
        this.betGrade = betGrade;
        this.confirmed = confirmed;
    }

    @PropertyName(Constants.FIELD_STUDENT_UID)
    public String getStudentUid() {
        return studentUid;
    }

    @PropertyName(Constants.FIELD_PARENT_UID)
    public String getParentUid() {
        return parentUid;
    }

    @PropertyName(Constants.FIELD_TEACHER_UID)
    public String getTeacherUid() {
        return teacherUid;
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

    @PropertyName(Constants.FIELD_BET_CONFIRMED)
    public boolean isConfirmed() {
        return confirmed;
    }

    public static Bet fromMap(Map map) {
        String studentAddress = (String) map.get(Constants.FIELD_STUDENT_ADDRESS);
        String parentAddress = (String) map.get(Constants.FIELD_PARENT_ADDRESS);
        String teacherAddress = (String) map.get(Constants.FIELD_TEACHER_ADDRESS);
        String studentUid = (String) map.get(Constants.FIELD_STUDENT_UID);
        String parentUid = (String) map.get(Constants.FIELD_PARENT_UID);
        String teacherUid = (String) map.get(Constants.FIELD_TEACHER_UID);
        String assignmentName = (String) map.get(Constants.FIELD_ASSIGNMENT_NAME);
        String assignmentUid = (String) map.get(Constants.FIELD_ASSIGNMENT_UID);
        String betValue = (String) map.get(Constants.FIELD_BET_VALUE);
        Long betGrade = (Long) map.get(Constants.FIELD_BET_GRADE);
        Boolean confirmed = (Boolean) map.get(Constants.FIELD_BET_CONFIRMED);
        return new Bet(studentAddress, parentAddress, teacherAddress, studentUid, parentUid,
                teacherUid,assignmentName, assignmentUid, betValue, betGrade, confirmed);
    }
}
