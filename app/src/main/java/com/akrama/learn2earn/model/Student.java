package com.akrama.learn2earn.model;

import com.akrama.learn2earn.Constants;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by akrama on 31/01/18.
 */

public class Student {

    private String mStudentName;
    private String mStudentUid;

    public Student(String studentName, String studentUid) {
        mStudentName = studentName;
        mStudentUid = studentUid;
    }

    @PropertyName(Constants.FIELD_STUDENT_NAME)
    public String getStudentName() {
        return mStudentName;
    }

    @PropertyName(Constants.FIELD_STUDENT_UID)
    public String getStudentUid() {
        return mStudentUid;
    }

    public static Student fromMap(Map map) {
        String name = (String) map.get(Constants.FIELD_STUDENT_NAME);
        String uid = (String) map.get(Constants.FIELD_STUDENT_UID);
        return new Student(name, uid);
    }

    public static List<Student> fromMapListToStudentList(List<Map> mapList) {
        List<Student> studentList = new ArrayList<>();
        for (Map map : mapList) {
            studentList.add(Student.fromMap(map));
        }
        return studentList;
    }
}
