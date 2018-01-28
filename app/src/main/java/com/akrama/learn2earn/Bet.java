package com.akrama.learn2earn;

import com.google.firebase.firestore.PropertyName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akrama on 25/01/18.
 */

public class Bet {

    private String studentUid;
    private String parentUid;
    private String teacherUid;
    private String assignmentUid;
    private String betValue;
    private String betGrade;

    public Bet(String studentUid, String parentUid, String teacherUid, String assignmentUid,
               String betValue, String betGrade) {
        this.studentUid = studentUid;
        this.parentUid = parentUid;
        this.teacherUid = teacherUid;
        this.assignmentUid = assignmentUid;
        this.betValue = betValue;
        this.betGrade = betGrade;
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

    @PropertyName(Constants.FIELD_ASSIGNMENT_UID)
    public String getAssignmentUid() {
        return assignmentUid;
    }

    @PropertyName(Constants.FIELD_BET_VALUE)
    public String getBetValue() {
        return betValue;
    }

    @PropertyName(Constants.FIELD_BET_GRADE)
    public String getBetGrade() {
        return betGrade;
    }
}
