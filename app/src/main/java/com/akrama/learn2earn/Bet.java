package com.akrama.learn2earn;

/**
 * Created by akrama on 25/01/18.
 */

public class Bet {

    private String parentUid;
    private String assignmentUid;
    private float value;

    public Bet() {}

    public Bet(String parentUid, String assignmentUid, float value) {
        this.parentUid = parentUid;
        this.assignmentUid = assignmentUid;
        this.value = value;
    }

    public String getParentUid() {
        return parentUid;
    }

    public String getAssignmentUid() {
        return assignmentUid;
    }

    public float getValue() {
        return value;
    }
}
