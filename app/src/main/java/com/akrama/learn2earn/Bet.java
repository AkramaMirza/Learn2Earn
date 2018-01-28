package com.akrama.learn2earn;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akrama on 25/01/18.
 */

public class Bet {

    private String student_uid;
    private String parent_uid;
    private String teacher_uid;
    private String assignment_uid;
    private float bet_value;

    public Bet(String student_uid, String parent_uid, String teacher_uid, String assignment_uid, float bet_value) {
        this.student_uid = student_uid;
        this.parent_uid = parent_uid;
        this.teacher_uid = teacher_uid;
        this.assignment_uid = assignment_uid;
        this.bet_value = bet_value;
    }

    public String getStudentUid() {
        return student_uid;
    }

    public String getParentUid() {
        return parent_uid;
    }

    public String getTeacherUid() {
        return teacher_uid;
    }

    public String getAssignmentUid() {
        return assignment_uid;
    }

    public float getBetValue() {
        return bet_value;
    }

    public static Map toMap(String assignmentName, String betUid, float value) {
        Map map = new HashMap();
        map.put(Constants.FIELD_ASSIGNMENT_NAME, assignmentName);
        map.put(Constants.FIELD_BET_UID, betUid);
        map.put(Constants.FIELD_BET_VALUE, value);
        return map;
    }
}
