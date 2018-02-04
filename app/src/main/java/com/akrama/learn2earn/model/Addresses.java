package com.akrama.learn2earn.model;

/**
 * Created by akrama on 04/02/18.
 */

public class Addresses {
    private String student;
    private String parent;
    private String teacher;

    public Addresses(String student, String parent, String teacher) {
        this.student = student;
        this.parent = parent;
        this.teacher = teacher;
    }

    public String getStudent() {
        return student;
    }

    public String getParent() {
        return parent;
    }

    public String getTeacher() {
        return teacher;
    }
}
