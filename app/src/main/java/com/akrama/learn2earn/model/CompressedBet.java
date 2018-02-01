package com.akrama.learn2earn.model;

import com.akrama.learn2earn.Constants;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

import java.util.Map;

/**
 * Created by akrama on 27/01/18.
 */

public class CompressedBet {

    private String assignmentName;
    private String betUid;
    private String betValue;
    private String betGrade;


    public CompressedBet(String assignmentName, String betUid, String betValue, String betGrade) {
        this.assignmentName = assignmentName;
        this.betUid = betUid;
        this.betValue = betValue;
        this.betGrade = betGrade;
    }

    @PropertyName(Constants.FIELD_ASSIGNMENT_NAME)
    public String getAssignmentName() {
        return assignmentName;
    }

    @PropertyName(Constants.FIELD_BET_UID)
    public String getBetUid() {
        return betUid;
    }

    @PropertyName(Constants.FIELD_BET_VALUE)
    public String getBetValue() {
        return betValue;
    }

    @PropertyName(Constants.FIELD_BET_GRADE)
    public String getBetGrade() {
        return betGrade;
    }

    @Exclude
    public float getBetValueAsFloat() {
        return Float.valueOf(betValue);
    }

    @Exclude
    public float getBetGradeAsFloat() {
        return Float.valueOf(betGrade);
    }

    public static CompressedBet fromMap(Map map) {
        String assignmentName = (String) map.get(Constants.FIELD_ASSIGNMENT_NAME);
        String betUid = (String) map.get(Constants.FIELD_BET_UID);
        String betValue = (String) map.get(Constants.FIELD_BET_VALUE);
        String betGrade = (String) map.get(Constants.FIELD_BET_GRADE);
        return new CompressedBet(assignmentName, betUid, betValue, betGrade);
    }
}
