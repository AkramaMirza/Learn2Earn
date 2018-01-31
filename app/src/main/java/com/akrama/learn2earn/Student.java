package com.akrama.learn2earn;

import com.google.firebase.firestore.PropertyName;

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


}
