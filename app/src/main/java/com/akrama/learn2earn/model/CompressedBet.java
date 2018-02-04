package com.akrama.learn2earn.model;

import com.akrama.learn2earn.Constants;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by akrama on 27/01/18.
 */

public class CompressedBet {

    private String assignmentName;
    private String betUid;
    private String betValue;
    private Long betGrade;
    private boolean confirmed;


    public CompressedBet(String assignmentName, String betUid, String betValue, Long betGrade, boolean confirmed) {
        this.assignmentName = assignmentName;
        this.betUid = betUid;
        this.betValue = betValue;
        this.betGrade = betGrade;
        this.confirmed = confirmed;
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
    public Long getBetGrade() {
        return betGrade;
    }

    @PropertyName(Constants.FIELD_BET_CONFIRMED)
    public boolean isConfirmed() {
        return confirmed;
    }

    @Exclude
    public float getBetValueAsFloat() {
        return Float.valueOf(betValue);
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public static CompressedBet fromMap(Map map) {
        String assignmentName = (String) map.get(Constants.FIELD_ASSIGNMENT_NAME);
        String betUid = (String) map.get(Constants.FIELD_BET_UID);
        String betValue = (String) map.get(Constants.FIELD_BET_VALUE);
        Long betGrade = (Long) map.get(Constants.FIELD_BET_GRADE);
        boolean confirmed = (boolean) map.get(Constants.FIELD_BET_CONFIRMED);
        return new CompressedBet(assignmentName, betUid, betValue, betGrade, confirmed);
    }

    public static List<CompressedBet> fromMapListToCompressedBetList(List<Map> mapList) {
        List<CompressedBet> compressedBets = new ArrayList<>();
        for (Map map : mapList) {
            compressedBets.add(CompressedBet.fromMap(map));
        }
        return compressedBets;
    }
}
