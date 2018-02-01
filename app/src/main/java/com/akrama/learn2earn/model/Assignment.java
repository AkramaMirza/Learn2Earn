package com.akrama.learn2earn.model;

import com.akrama.learn2earn.Constants;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by akrama on 26/01/18.
 */

public class Assignment {

    private String name;
    private String uid;

    public Assignment(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    @PropertyName(Constants.FIELD_ASSIGNMENT_NAME)
    public String getName() {
        return name;
    }

    @PropertyName(Constants.FIELD_ASSIGNMENT_UID)
    public String getUid() {
        return uid;
    }

    public static Assignment fromMap(Map map) {
        String name = (String) map.get(Constants.FIELD_ASSIGNMENT_NAME);
        String uid = (String) map.get(Constants.FIELD_ASSIGNMENT_UID);
        return new Assignment(name, uid);
    }

    public static List<Assignment> fromMapListToAssignmentList(List<Map> mapList) {
        List<Assignment> assignments = new ArrayList<>();
        for (Map map : mapList) {
            assignments.add(Assignment.fromMap(map));
        }
        return assignments;
    }
}
